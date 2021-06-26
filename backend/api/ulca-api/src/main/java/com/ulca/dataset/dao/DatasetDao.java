package com.ulca.dataset.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.dataset.model.Dataset;

@Repository
public interface DatasetDao extends MongoRepository<Dataset, String>{


	List<Dataset> findBySubmitterId(String userId);
	Dataset findByDatasetId(String datasetId);

}
