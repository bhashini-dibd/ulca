package com.ulca.benchmark.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.ulca.benchmark.model.BenchmarkDatasetSubmitStatus;

@Repository
public interface BenchmarkDatasetSubmitStatusDao extends MongoRepository<BenchmarkDatasetSubmitStatus, String> {

	BenchmarkDatasetSubmitStatus findByServiceRequestNumber(String serviceRequestNumber);

}
