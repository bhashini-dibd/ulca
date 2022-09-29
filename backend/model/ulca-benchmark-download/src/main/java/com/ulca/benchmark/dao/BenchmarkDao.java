package com.ulca.benchmark.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.swagger.model.Benchmark;

@Repository
public interface BenchmarkDao extends MongoRepository<Benchmark, String> {

}