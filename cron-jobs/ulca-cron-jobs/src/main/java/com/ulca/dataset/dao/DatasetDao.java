package com.ulca.dataset.dao;

import com.ulca.dataset.model.Dataset;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DatasetDao extends MongoRepository<Dataset, String>{
	Dataset findByDatasetId(String datasetId);

}
