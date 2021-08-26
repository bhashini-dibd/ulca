package com.ulca.benchmark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ulca.benchmark.dao.BenchmarkDao;
import com.ulca.benchmark.model.BenchmarkExtended;
import com.ulca.benchmark.request.ExecuteBenchmarkRequest;
import com.ulca.benchmark.request.ExecuteBenchmarkResponse;

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

}
