package com.ulca.dataset.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.dataset.model.Dataset;

@Repository
public interface DatasetDao extends MongoRepository<Dataset, String>{

	Page<Dataset> findBySubmitterId(String userId, Pageable paging);

	List<Dataset> findBySubmitterId(String userId);
	Dataset findByDatasetId(String datasetId);

	Integer countBySubmitterId(String userId);
	Integer countBySubmitterIdAndDatasetName(String userId,String datasetName);


}
