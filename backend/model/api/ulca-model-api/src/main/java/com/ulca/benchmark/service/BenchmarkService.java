package com.ulca.benchmark.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ulca.benchmark.dao.BenchmarkDao;
import com.ulca.benchmark.model.BenchmarkExtended;
import com.ulca.benchmark.request.BenchmarkSearchRequest;
import com.ulca.benchmark.request.BenchmarkSearchResponse;
import com.ulca.benchmark.request.ExecuteBenchmarkRequest;
import com.ulca.benchmark.request.ExecuteBenchmarkResponse;
import com.ulca.model.response.ModelListByUserIdResponse;

import io.swagger.model.Domain;
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
	
	public BenchmarkExtended submitBenchmark(BenchmarkExtended benchmark) {
		
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

	public List<BenchmarkExtended> ListByTaskID(BenchmarkSearchRequest request) {
		
		if (request.getTask() != null && !request.getTask().isBlank()||request.getDomain() = null && request.getDomain().isBlank()) {
			List<BenchmarkSearchResponse> response=benchmarkDao.findAllByTask(request.getTask());
		}
		else if (request.getDomain() != null && !request.getDomain().isBlank()|| request.getTask() = null && request.getTask().isBlank()) {
			List<BenchmarkSearchResponse> response=benchmarkDao.findAllByDomain(request.getDomain());
		}

		else if (request.getDomain() != null && !request.getDomain().isBlank()|| request.getTask() != null && !request.getTask().isBlank()) {
			List<BenchmarkSearchResponse> response=benchmarkDao.findByDomain(request.getDomain(), request.getTask());
		}
		
		
		
		return 	response;
	}

}
