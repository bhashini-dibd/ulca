package com.ulca.benchmark.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.benchmark.request.BenchmarkSearchResponse;

import io.swagger.model.Benchmark;
import io.swagger.model.Domain;
import io.swagger.model.ModelTask;

@Repository
public interface BenchmarkDao extends MongoRepository<Benchmark, String> {
	
	List<Benchmark> findByDomain(Domain domainId,String taskId);

	List<BenchmarkSearchResponse> findAllByDomain(Benchmark domain);

	List<BenchmarkSearchResponse> findAllByTask(ModelTask task);
	

	List<BenchmarkSearchResponse> findByDomain(Benchmark domain, ModelTask task);


}
