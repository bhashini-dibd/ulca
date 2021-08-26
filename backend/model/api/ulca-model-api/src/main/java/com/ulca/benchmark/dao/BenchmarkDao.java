package com.ulca.benchmark.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.benchmark.model.BenchmarkExtended;
import com.ulca.benchmark.request.BenchmarkSearchResponse;

import io.swagger.model.Domain;
import io.swagger.model.ModelTask;

@Repository
public interface BenchmarkDao extends MongoRepository<BenchmarkExtended, String> {
	
	List<BenchmarkExtended> findByDomain(Domain domainId,String taskId);

	List<BenchmarkSearchResponse> findAllByDomain(BenchmarkExtended domain);

	List<BenchmarkSearchResponse> findAllByTask(ModelTask task);

	List<BenchmarkSearchResponse> findByDomain(BenchmarkExtended domain, ModelTask task);

}
