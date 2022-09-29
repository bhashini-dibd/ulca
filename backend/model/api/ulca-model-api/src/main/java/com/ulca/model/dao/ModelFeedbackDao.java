package com.ulca.model.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelFeedbackDao extends MongoRepository<ModelFeedback, String>{

	List<ModelFeedback> findByModelId(String modelId);

	List<ModelFeedback>  findByTaskType(String taskType);

	List<ModelFeedback> findByStsFeedbackId(String feedbackId);

}
