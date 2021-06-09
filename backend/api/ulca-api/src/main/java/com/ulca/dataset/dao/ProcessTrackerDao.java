package com.ulca.dataset.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.dataset.model.Dataset;
import com.ulca.dataset.model.ProcessTracker;

@Repository
public interface ProcessTrackerDao extends MongoRepository<ProcessTracker, String>{

}
