package com.ulca.benchmark.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.ulca.benchmark.model.BenchmarkProcessTracker;

@Repository
public interface BenchmarkProcessTrackerDao extends MongoRepository<BenchmarkProcessTracker, String> {

}
