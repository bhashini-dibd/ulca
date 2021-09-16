package com.ulca.benchmark.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.benchmark.model.BenchmarkProcess;

import io.swagger.model.Benchmark;

@Repository
public interface BenchmarkProcessDao extends MongoRepository<BenchmarkProcess, String> {

	List<BenchmarkProcess> findByModelId(String modelId);
	List<BenchmarkProcess> findByBenchmarkProcessId(String benchmarkProcessId);
	List<BenchmarkProcess> findByModelIdAndBenchmarkDatasetIdAndMetric(String modelId, String benchmarkId, String metric);
	List<BenchmarkProcess> findByModelIdAndBenchmarkDatasetId(String modelId, String benchmarkId);
	
}
