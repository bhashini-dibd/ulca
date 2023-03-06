package com.ulca.dataset.dao;


import com.ulca.dataset.model.TaskTracker;
import com.ulca.dataset.model.TaskTrackerDto;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskTrackerDao extends MongoRepository<TaskTracker, String> {

	List<TaskTrackerDto> findAllByServiceRequestNumberAndTool(String serviceRequestNumber,String tool );
	TaskTrackerDto findByServiceRequestNumberAndTool(String serviceRequestNumber,String tool );

}
