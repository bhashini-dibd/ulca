package com.ulca.benchmark.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.model.dao.ModelExtended;

import io.swagger.model.Benchmark;
import io.swagger.model.ModelTask;

@Repository
public interface BenchmarkDao extends MongoRepository<Benchmark, String> {

	Benchmark findByBenchmarkId(String benchmarkId);

	List<Benchmark> findByTask(ModelTask task);

	Page<Benchmark> findByUserId(String userId, Pageable paging);

	List<Benchmark> findByUserId(String userId);
	
}
