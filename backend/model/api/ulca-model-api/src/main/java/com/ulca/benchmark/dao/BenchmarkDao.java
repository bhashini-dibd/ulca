package com.ulca.benchmark.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.benchmark.model.BenchmarkExtended;
import com.ulca.model.dao.ModelExtended;

@Repository
public interface BenchmarkDao extends MongoRepository<BenchmarkExtended, String> {

}
