package com.ulca.dataset.dao;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.swagger.model.Benchmark;
import io.swagger.model.ModelTask;

@Repository
public interface BenchmarkDao extends MongoRepository<Benchmark, String> {

	

	List<Benchmark> findByUserId(String userId);
	
}
