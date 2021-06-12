package com.ulca.dataset.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.ProcessTracker.ServiceRequestActionEnum;
import com.ulca.dataset.model.ProcessTracker.ServiceRequestTypeEnum;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.request.DatasetCorpusSearchRequest;
import com.ulca.dataset.request.SearchCriteria;

import io.swagger.model.DatasetType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SearchKafkaPublish {
	
	
	@Autowired
	private KafkaTemplate<String, SearchCriteria> datasetSearchKafkaTemplate;
										  
	
	
	@Value(value = "${KAFKA_ULCA_DS_SEARCH_IP_TOPIC}")
	private String datasetSearchTopic;
	
	
	@Autowired
	ProcessTrackerDao processTrackerDao;
	
	
	@Transactional
	public String searchPublish(DatasetCorpusSearchRequest request, String userId) throws JsonProcessingException  {
		
		String serviceRequestNumber = null;
		

		ProcessTracker processTracker = new ProcessTracker();
		processTracker.setUserId(userId);
		UUID uuid = UUID.randomUUID();
		processTracker.setServiceRequestNumber(uuid.toString());
		processTracker.setServiceRequestAction(ServiceRequestActionEnum.search);
		processTracker.setServiceRequestType(ServiceRequestTypeEnum.dataset);
		processTracker.setStatus(StatusEnum.notstarted);
		processTracker.setStartTime(new Date());
		
		

		processTrackerDao.save(processTracker);
		
		serviceRequestNumber = processTracker.getServiceRequestNumber();
		
		

		SearchCriteria searchCriteria = request.getCriteria();
		
			
		searchCriteria.setServiceRequestNumber(processTracker.getServiceRequestNumber());
		searchCriteria.setGroupBy(request.getGroupby());
		searchCriteria.setDatasetType(request.getDatasetType().toString());
		
		log.info(searchCriteria.toString());
		
		datasetSearchKafkaTemplate.send(datasetSearchTopic, searchCriteria);
		
		
		processTracker.setSearchCriterion(searchCriteria);
		processTracker.setStatus(StatusEnum.inprogress);
		processTrackerDao.save(processTracker);
		
		
		return serviceRequestNumber;
		
	}
}
