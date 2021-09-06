package com.ulca.benchmark.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ulca.benchmark.dao.BenchmarkDao;
import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.kafka.model.BmDatasetDownload;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.benchmark.request.BenchmarkMetricRequest;

import com.ulca.benchmark.request.BenchmarkSearchRequest;
import com.ulca.benchmark.request.BenchmarkSearchResponse;
import com.ulca.benchmark.request.ExecuteBenchmarkRequest;
import com.ulca.benchmark.request.ExecuteBenchmarkResponse;
import com.ulca.model.response.BmProcessListByProcessIdResponse;

import io.swagger.model.Benchmark;
import io.swagger.model.ModelTask;
import io.swagger.model.ModelTask.TypeEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BenchmarkService {

	@Autowired
	private KafkaTemplate<String, BmDatasetDownload> benchmarkDownloadKafkaTemplate;

	@Value("${kafka.ulca.bm.filedownload.ip.topic}")
	private String benchmarkDownloadTopic;

	@Autowired
	BenchmarkDao benchmarkDao;

	@Autowired
	BenchmarkProcessDao benchmarkprocessDao;

	public Benchmark submitBenchmark(Benchmark benchmark) {

		benchmarkDao.save(benchmark);
		return benchmark;
	}

	public ExecuteBenchmarkResponse executeBenchmark(ExecuteBenchmarkRequest request) {

		log.info("******** Entry BenchmarkService:: executeBenchmark *******");

		UUID uuid = UUID.randomUUID();
		String modelId = request.getModelId();

		for (BenchmarkMetricRequest bm : request.getBenchmarks()) {
			Benchmark benchmark = benchmarkDao.findByBenchmarkId(bm.getBenchmarkId());
			BenchmarkProcess bmProcess = new BenchmarkProcess();
			bmProcess.setBenchmarkDatasetId(bm.getBenchmarkId());
			bmProcess.setBenchmarkProcessId(uuid.toString());
			bmProcess.setMetric(bm.getMetric());
			bmProcess.setBenchmarkDatasetName(benchmark.getName());
			bmProcess.setModelId(modelId);
			bmProcess.setStatus("In-Progress");
			bmProcess.setCreatedOn(new Date().toString());
			bmProcess.setLastModifiedOn(new Date().toString());
			benchmarkprocessDao.save(bmProcess);

		}

		BmDatasetDownload bmDsDownload = new BmDatasetDownload(uuid.toString());

		benchmarkDownloadKafkaTemplate.send(benchmarkDownloadTopic, bmDsDownload);

		ExecuteBenchmarkResponse response = new ExecuteBenchmarkResponse();
		response.setBenchmarkProcessId(uuid.toString());

		log.info("******** Exit BenchmarkService:: executeBenchmark *******");

		return response;

	}

	public BenchmarkSearchResponse listByTaskID(BenchmarkSearchRequest request) {

		log.info("******** Entry BenchmarkService:: listByTaskID *******");

		BenchmarkSearchResponse response = null;
		Benchmark benchmark = new Benchmark();
		if (request.getTask() != null && !request.getTask().isBlank()) {
			ModelTask modelTask = new ModelTask();
			modelTask.setType(TypeEnum.fromValue(request.getTask()));
			benchmark.setTask(modelTask);
		}
		if (request.getDomain() != null) {
			benchmark.setDomain(request.getDomain());
		}
		Example<Benchmark> example = Example.of(benchmark);
		List<Benchmark> list = benchmarkDao.findAll(example);

		List<String> metric = null;
		if (request.getTask() != null && !request.getTask().isBlank()) {
			metric = getMetric(request.getTask());
		}
		response = new BenchmarkSearchResponse("Benchmark Search Result", list, metric, list.size());

		log.info("******** Exit BenchmarkService:: listByTaskID *******");

		return response;
	}

	private List<String> getMetric(String task) {

		String[] metric = null;
		if (task.equalsIgnoreCase("translation")) {
			metric = new String[] { "bleu" };
			return Arrays.asList(metric);
		}
		if (task.equalsIgnoreCase("asr")) {
			metric = new String[] { "wer" };
			return Arrays.asList(metric);
		}
		if (task.equalsIgnoreCase("document-layout")) {
			metric = new String[] { "precision", "recall", "h1-mean" };
			return Arrays.asList(metric);
		}
		if (task.equalsIgnoreCase("ocr")) {
			metric = new String[] { "wer", "cer" };
			return Arrays.asList(metric);
		}

		return null;
	}
	
	public BmProcessListByProcessIdResponse getScorelistByProcess(String benchmarkProcessId ){
		
		List<BenchmarkProcess> list =  benchmarkprocessDao.findByBenchmarkProcessId(benchmarkProcessId);
		
		BmProcessListByProcessIdResponse response = new BmProcessListByProcessIdResponse("Benchmark Process list", list, list.size());
		
		return response;
		
		
	}

}
