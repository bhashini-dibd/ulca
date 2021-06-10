package com.ulca.dataset.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ulca.dataset.dao.DatasetDao;
import com.ulca.dataset.dao.FileIdentifierDao;
import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.kakfa.FileDownload;
import com.ulca.dataset.model.Dataset;
import com.ulca.dataset.model.Fileidentifier;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.ProcessTracker.ServiceRequestActionEnum;
import com.ulca.dataset.model.ProcessTracker.ServiceRequestTypeEnum;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.request.DatasetSubmitRequest;
import com.ulca.dataset.response.DatasetSubmitResponse;

import lombok.extern.slf4j.Slf4j;

import com.ulca.dataset.response.DatasetListByUserIdResponse;
@Slf4j
@Service
public class DatasetService {
	
	@Autowired
	DatasetDao datasetDao;
	
	@Autowired
	FileIdentifierDao fileIdentifierDao;
	
	@Autowired
	ProcessTrackerDao processTrackerDao;
	
	@Autowired
	private KafkaTemplate<String, Object> template;
	
	
	@Value(value = "${KAFKA_ULCA_DS_INGEST_IP_TOPIC}")
    private String fileDownloadTopic;
	
	@Transactional
	public DatasetSubmitResponse datasetSubmit(DatasetSubmitRequest request,String userId ) {
		
		Dataset dataset = new Dataset();
		dataset.setDatasetName(request.getDatasetName());
		dataset.setDatasetType(request.getType());
		dataset.setCreatedOn(new Date());
	
		
		Fileidentifier fileIndetifier = new Fileidentifier();
		fileIndetifier.setFileLocationURL(request.getUrl());
		
		fileIndetifier.setCreatedOn(new Date());
		fileIdentifierDao.insert(fileIndetifier);
		
		
		dataset.setDatasetFileIdentifier(fileIndetifier);
		
		datasetDao.insert(dataset);
		
		ProcessTracker processTracker = new ProcessTracker();
		processTracker.setUserId(userId);
		processTracker.setDatasetId(dataset.getDatasetId());
		UUID uuid = UUID.randomUUID();
		processTracker.setServiceRequestNumber(uuid.toString());
		processTracker.setServiceRequestAction(ServiceRequestActionEnum.SUBMIT);
		processTracker.setServiceRequestType(ServiceRequestTypeEnum.DATATSET);
		processTracker.setStatus(StatusEnum.NOTSTARTED);
		processTracker.setStartTime(new Date());
	
		processTrackerDao.insert(processTracker);
		
		
		FileDownload fileDownload = new FileDownload();
		fileDownload.setDatasetId(dataset.getDatasetId());	
		fileDownload.setFileUrl(request.getUrl());
		fileDownload.setServiceRequestNumber(processTracker.getServiceRequestNumber());
		
		
		template.send(fileDownloadTopic, fileDownload);
		
		return new DatasetSubmitResponse(processTracker.getServiceRequestNumber(), dataset.getDatasetId(), dataset.getCreatedOn());
	}
	
	public List<DatasetListByUserIdResponse> dataSetListByUserId(String userId) {
		
		log.info("******** Entry DatasetService:: dataSetListByUserId *******" );
		
		List<DatasetListByUserIdResponse> list = new ArrayList<DatasetListByUserIdResponse> ();
		
		
		List<ProcessTracker>  processList = processTrackerDao.findByUserId(userId);
		for(ProcessTracker p : processList) {
			
			Optional<Dataset> dataset = datasetDao.findById(p.getDatasetId());
			
			list.add(new DatasetListByUserIdResponse(p.getServiceRequestNumber(),dataset.get().getDatasetName(),dataset.get().getCreatedOn(),p.getStatus().toString()));	
		}
			
			
			
		log.info("******** Exit DatasetService:: dataSetListByUserId *******" );
		return list;
		
		
	}

}
