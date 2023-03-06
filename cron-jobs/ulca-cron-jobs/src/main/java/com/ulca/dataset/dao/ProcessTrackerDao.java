package com.ulca.dataset.dao;

import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.ProcessTrackerDto;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProcessTrackerDao extends MongoRepository<ProcessTracker, String>{

	ProcessTrackerDto findByServiceRequestNumber(String serviceRequestNumber);

}
