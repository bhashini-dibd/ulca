package com.ulca.dataset.service;

import java.util.Date;
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
import com.ulca.dataset.request.DatasetSubmitRequest;
import com.ulca.dataset.response.DatasetSubmitResponse;

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
	public DatasetSubmitResponse datasetSubmit(DatasetSubmitRequest request) {
		
		
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
		processTracker.setUserId("6491af71d71b4f1d9cff293522260838");
		processTracker.setDatasetId(dataset.getDatasetId());
		UUID uuid = UUID.randomUUID();
		processTracker.setServiceRequestNumber(uuid.toString());
		processTrackerDao.insert(processTracker);
		
		
		FileDownload fileDownload = new FileDownload();
		fileDownload.setDatasetId(dataset.getDatasetId());	
		fileDownload.setFileUrl(request.getUrl());
		fileDownload.setServiceRequestNumber(processTracker.getServiceRequestNumber());
		
		
		template.send(fileDownloadTopic, fileDownload);
		
		return new DatasetSubmitResponse(processTracker.getServiceRequestNumber(), dataset.getDatasetId(), dataset.getCreatedOn());
	}

}
