package com.ulca.dataset.service;

import com.ulca.dataset.dao.DatasetDao;
import com.ulca.dataset.dao.FileIdentifierDao;
import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.model.Dataset;
import com.ulca.dataset.model.Fileidentifier;
import com.ulca.dataset.model.ProcessTracker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class DatasetService {


	@Autowired
	DatasetDao datasetDao;
	
	@Autowired
	FileIdentifierDao fileIdentifierDao;

	@Autowired
	ProcessTrackerDao processTrackerDao;

	public void updateDatasetFileLocation(String serviceRequestNumber, String localUrl) {
		
		ProcessTracker processTracker = processTrackerDao.findByServiceRequestNumber(serviceRequestNumber);
		Dataset dataset = datasetDao.findByDatasetId(processTracker.getDatasetId());
		//dataset.getDatasetFileIdentifier().setFileUlcaUrl(localUrl);
		
		Fileidentifier fileidentifier = dataset.getDatasetFileIdentifier();
		fileidentifier.setFileUlcaUrl(localUrl);
		fileIdentifierDao.save(fileidentifier);
	}

}
