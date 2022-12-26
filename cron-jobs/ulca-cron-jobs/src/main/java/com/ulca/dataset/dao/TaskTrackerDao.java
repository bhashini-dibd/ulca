package com.ulca.dataset.dao;


import com.ulca.dataset.model.TaskTracker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskTrackerDao extends MongoRepository<TaskTracker, String> {

	List<TaskTracker> findAllByServiceRequestNumberAndTool(String serviceRequestNumber,String tool );
}
