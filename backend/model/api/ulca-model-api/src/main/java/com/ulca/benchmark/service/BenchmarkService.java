package com.ulca.benchmark.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ulca.benchmark.dao.BenchmarkDao;
import com.ulca.benchmark.request.BenchmarkSearchRequest;
import com.ulca.benchmark.request.BenchmarkSearchResponse;
import com.ulca.benchmark.request.ExecuteBenchmarkRequest;
import com.ulca.benchmark.request.ExecuteBenchmarkResponse;

import io.swagger.model.Benchmark;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BenchmarkService {
	
	
	@Autowired
	private KafkaTemplate<String, ExecuteBenchmarkRequest> benchmarkDownloadKafkaTemplate;
	
	@Value("${kafka.ulca.bm.filedownload.ip.topic}")
	private String benchmarkDownloadTopic;
	
	
	@Autowired
	BenchmarkDao benchmarkDao;
	
	public Benchmark submitBenchmark(Benchmark benchmark) {
		
		benchmarkDao.save(benchmark);
		return benchmark;
	}
	
	public ExecuteBenchmarkResponse executeBenchmark(ExecuteBenchmarkRequest request) {
		
		ExecuteBenchmarkResponse response = new ExecuteBenchmarkResponse();
		
		benchmarkDownloadKafkaTemplate.send(benchmarkDownloadTopic, request);
		
		log.info(request.toString());
		log.info(request.getModelId());
		log.info(request.getBenchmarks().toString());
		
		log.info(request.getBenchmarks().get(0).getBenchmarkId());
		
		return response;
		
	}

	public List<BenchmarkSearchResponse> listByTaskID(BenchmarkSearchRequest request) {
		
		List<BenchmarkSearchResponse> response = null;
		
		if (request.getTask() != null && request.getDomain() == null ) {
			 response=benchmarkDao.findAllByTask(request.getTask());
		}
		else if (request.getDomain() == null &&  request.getTask() != null ) {
			 response=benchmarkDao.findAllByDomain(request.getDomain());
		}

		else if (request.getDomain() != null &&  request.getTask() != null ) {
			 response=benchmarkDao.findByDomain(request.getDomain(), request.getTask());
		}
		else if (request.getMetricName().name()=="BLEU" ||request.getMetricName().name()=="BLEU"||request.getMetricName().name()=="SACREBLEU"||request.getMetricName().name()=="METEOR") {
			 response=benchmarkDao.findByEnum(request.getMetricName().name());
		}
		
		
		return 	response;
	}

}
