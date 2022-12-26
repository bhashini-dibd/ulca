package com.ulca.model.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelDao extends MongoRepository<ModelExtended, String> {
	List<ModelExtended> findByStatus(String status);
}
