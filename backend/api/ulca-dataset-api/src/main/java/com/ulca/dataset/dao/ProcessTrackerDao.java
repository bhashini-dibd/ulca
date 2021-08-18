package com.ulca.dataset.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.ProcessTracker.ServiceRequestActionEnum;
import com.ulca.dataset.model.ProcessTracker.ServiceRequestTypeEnum;

@Repository
public interface ProcessTrackerDao extends MongoRepository<ProcessTracker, String>{

	List<ProcessTracker> findByDatasetId(String datasetId);

	List<ProcessTracker> findByUserId(String userId);
	Page<ProcessTracker> findByUserId(String userId, Pageable pageable);
	
	ProcessTracker findByServiceRequestNumber(String serviceRequestNumber);

	Page<ProcessTracker> findByUserIdAndServiceRequestTypeAndServiceRequestAction(String userId,
			ServiceRequestTypeEnum dataset, ServiceRequestActionEnum search, Pageable paging);
	
	List<ProcessTracker> findByUserIdAndServiceRequestTypeAndServiceRequestAction(String userId,
			ServiceRequestTypeEnum dataset, ServiceRequestActionEnum search);

	

}
