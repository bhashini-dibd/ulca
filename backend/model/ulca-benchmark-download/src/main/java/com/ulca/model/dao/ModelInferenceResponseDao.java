package com.ulca.model.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.benchmark.model.ModelInferenceResponse;

@Repository
public interface ModelInferenceResponseDao extends MongoRepository<ModelInferenceResponse, String> {

}
