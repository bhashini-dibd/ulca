package com.ulca.benchmark.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.benchmark.model.BenchmarkProcess;

@Repository
public interface BenchmarkProcessDao extends MongoRepository<BenchmarkProcess, String> {

	BenchmarkProcess findByBenchmarkProcessId(String benchmarkProcessId);

}
