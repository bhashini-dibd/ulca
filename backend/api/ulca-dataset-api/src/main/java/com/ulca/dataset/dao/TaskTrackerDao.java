package com.ulca.dataset.dao;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.dataset.model.TaskTracker;
import com.ulca.dataset.model.TaskTracker.ToolEnum;

@Repository
public interface TaskTrackerDao extends MongoRepository<TaskTracker, String> {

	List<TaskTracker> findAllByServiceRequestNumber(String serviceRequestNumber);
	
	List<TaskTracker> findAllByServiceRequestNumberAndTool(String serviceRequestNumber,String tool );



}
