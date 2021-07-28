package com.ulca.model.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.swagger.model.Model;

@Repository
public interface ModelDao extends MongoRepository<Model, String> {

	Page<Model> findBySubmitterId(String submitterId, Pageable paging);

	List<Model> findBySubmitterId(String userId);

}
