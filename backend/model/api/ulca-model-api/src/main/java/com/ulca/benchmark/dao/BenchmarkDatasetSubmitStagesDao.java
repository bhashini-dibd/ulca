package com.ulca.benchmark.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.ulca.benchmark.model.BenchmarkDatasetSubmitStages;
import com.ulca.benchmark.model.BenchmarkDatasetSubmitStages.ToolEnum;

@Repository
public interface BenchmarkDatasetSubmitStagesDao extends MongoRepository<BenchmarkDatasetSubmitStages, String> {

	List<BenchmarkDatasetSubmitStages> findAllByServiceRequestNumber(String serviceRequestNumber);

	List<BenchmarkDatasetSubmitStages> findAllByServiceRequestNumberAndTool(String serviceRequestNumber, ToolEnum tool);

}
