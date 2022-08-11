package com.ulca.benchmark.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.benchmark.model.BenchmarkTaskTracker;
import com.ulca.benchmark.model.BenchmarkTaskTracker.ToolEnum;

@Repository
public interface BenchmarkTaskTrackerDao extends MongoRepository<BenchmarkTaskTracker, String> {

	List<BenchmarkTaskTracker> findByBenchmarkProcessId(String benchmarkProcessId);

	List<BenchmarkTaskTracker> findAllByBenchmarkProcessIdAndTool(String benchmarkProcessId,
			ToolEnum tool);

	List<BenchmarkTaskTracker> findByBenchmarkProcessIdIn(List<String> benchmarkProcessIdList);

	
}
