package com.ulca.benchmark.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.benchmark.model.BenchmarkTaskTracker;

@Repository
public interface BenchmarkTaskTrackerDao extends MongoRepository<BenchmarkTaskTracker, String> {

}
