package com.ulca.dataset.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.ProcessTracker.ServiceRequestActionEnum;
import com.ulca.dataset.model.ProcessTracker.ServiceRequestTypeEnum;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.request.DatasetCorpusSearchRequest;
import com.ulca.dataset.request.SearchCriteria;
import com.ulca.dataset.util.Utility;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Service
public class SearchKafkaPublishService {
	
	
	@Autowired
	private KafkaTemplate<String, SearchCriteria> datasetSearchKafkaTemplate;
										  
	
	
	@Value("${kafka.ulca.ds.search.ip.topic}")
	private String datasetSearchTopic;
	
	
	@Autowired
	ProcessTrackerDao processTrackerDao;
	
	
	@Transactional
	public String searchPublish(DatasetCorpusSearchRequest request, String userId) throws JsonProcessingException  {
		
		String serviceRequestNumber = null;
		

		ProcessTracker processTracker = new ProcessTracker();
		processTracker.setUserId(userId);
		processTracker.setServiceRequestNumber(Utility.getDatasetSearchReferenceNumber());
		processTracker.setServiceRequestAction(ServiceRequestActionEnum.search);
		processTracker.setServiceRequestType(ServiceRequestTypeEnum.dataset);
		processTracker.setStatus(StatusEnum.pending.toString());
		processTracker.setStartTime(Instant.now().toEpochMilli());
		
		processTrackerDao.save(processTracker);
		
		serviceRequestNumber = processTracker.getServiceRequestNumber();
		SearchCriteria searchCriteria = request.getCriteria();
		searchCriteria.setServiceRequestNumber(processTracker.getServiceRequestNumber());
		//searchCriteria.setGroupBy(request.getGroupby());
		searchCriteria.setDatasetType(request.getDatasetType().toString());
		searchCriteria.setUserId(userId);		
		log.info(searchCriteria.toString());
		
		datasetSearchKafkaTemplate.send(datasetSearchTopic, searchCriteria);
		
		
		processTracker.setSearchCriterion(searchCriteria);
		processTracker.setStatus(StatusEnum.inprogress.toString());
		processTrackerDao.save(processTracker);
		
		
		return serviceRequestNumber;
		
	}
}
