package com.ulca.model.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PipelineModelDao extends MongoRepository<PipelineModel,String> {

	PipelineModel findBySubmitterName(String submitterName);

	
	
}
