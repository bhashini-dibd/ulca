package com.ulca.dataset.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.ulca.dataset.constants.DatasetConstants;
import com.ulca.dataset.dao.DatasetDao;
import com.ulca.dataset.dao.FileIdentifierDao;
import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.kakfa.model.FileDownload;
import com.ulca.dataset.model.Dataset;
import com.ulca.dataset.model.Fileidentifier;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.ProcessTracker.ServiceRequestActionEnum;
import com.ulca.dataset.model.ProcessTracker.ServiceRequestTypeEnum;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.request.DatasetSubmitRequest;
import com.ulca.dataset.response.DatasetSubmitResponse;
import com.ulca.dataset.util.Utility;

public class BenchmarkServices {
	
	@Autowired
	DatasetDao datasetDao;

	@Autowired
	FileIdentifierDao fileIdentifierDao;

	@Autowired
	ProcessTrackerDao processTrackerDao;

	@Autowired
	TaskTrackerDao taskTrackerDao;

	@Autowired
	SearchKafkaPublishService searchKafkaPublish;

	@Autowired
	private KafkaTemplate<String, FileDownload> benchmarkFiledownloadKafkaTemplate;

	@Value("${kafka.ulca.ds.filedownload.ip.topic}")
	private String fileDownloadTopic;

	@Transactional
	public DatasetSubmitResponse Submitdataset(BenchmarkSubmitRequest request) {

		String userId = request.getUserId();
		Dataset dataset = new Dataset();
		dataset.setDatasetName(request.getDatasetName());
		//dataset.setDatasetType(request.getType().toString());
		dataset.setCreatedOn(new Date().toString());

		Fileidentifier fileIndetifier = new Fileidentifier();
		fileIndetifier.setFileLocationURL(request.getUrl());

		fileIndetifier.setCreatedOn(new Date().toString());
		fileIdentifierDao.insert(fileIndetifier);

		dataset.setDatasetFileIdentifier(fileIndetifier);

		try {
			datasetDao.insert(dataset);
		} catch(DuplicateKeyException ex) {
			
			throw new DuplicateKeyException(DatasetConstants.datasetNameUniqueErrorMsg);
			
		}

		ProcessTracker processTracker = new ProcessTracker();
		processTracker.setUserId(userId);
		processTracker.setDatasetId(dataset.getDatasetId());
		processTracker.setServiceRequestNumber(Utility.getDatasetSubmitReferenceNumber());
		processTracker.setServiceRequestAction(ServiceRequestActionEnum.submit);
		processTracker.setServiceRequestType(ServiceRequestTypeEnum.dataset);
		processTracker.setStatus(StatusEnum.pending.toString());
		processTracker.setStartTime(new Date().toString());

		processTrackerDao.insert(processTracker);

		FileDownload fileDownload = new FileDownload();
		fileDownload.setUserId(userId);
		fileDownload.setDatasetId(dataset.getDatasetId());
		fileDownload.setDatasetName(dataset.getDatasetName());
		//fileDownload.setDatasetType(request.getType());
		fileDownload.setFileUrl(request.getUrl());
		fileDownload.setServiceRequestNumber(processTracker.getServiceRequestNumber());
		
		benchmarkFiledownloadKafkaTemplate.send(fileDownloadTopic, fileDownload);
		
		String message = "Dataset Submit success";
		return new DatasetSubmitResponse(message,processTracker.getServiceRequestNumber(), dataset.getDatasetId(),
				dataset.getCreatedOn());
	}

}
