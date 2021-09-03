package com.ulca.model.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BenchMarkDao extends MongoRepository<ModelExtended, String> {

	

}
