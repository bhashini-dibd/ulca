package com.ulca.dataset.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.dataset.model.Dataset;

@Repository
public interface DatasetDao extends MongoRepository<Dataset, String>{


	List<Dataset> findByUserId(String userId);
	Dataset findByDatasetId(String datasetId);

	Integer countByUserId(String userId);
	Integer countByUserIdAndDatasetName(String userId,String datasetName);
	Page<Dataset> findByUserId(String userId, Pageable paging);



}
