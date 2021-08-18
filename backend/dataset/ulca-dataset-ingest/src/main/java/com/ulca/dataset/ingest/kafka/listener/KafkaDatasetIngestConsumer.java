package com.ulca.dataset.ingest.kafka.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ulca.dataset.constants.DatasetConstants;
import com.ulca.dataset.dao.DatasetDao;
import com.ulca.dataset.dao.FileIdentifierDao;
import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.kakfa.DatasetAsrUnlabeledValidateIngest;
import com.ulca.dataset.kakfa.DatasetAsrValidateIngest;
import com.ulca.dataset.kakfa.DatasetDocumentLayoutValidateIngest;
import com.ulca.dataset.kakfa.DatasetErrorPublishService;
import com.ulca.dataset.kakfa.DatasetMonolingualValidateIngest;
import com.ulca.dataset.kakfa.DatasetOcrValidateIngest;
import com.ulca.dataset.kakfa.DatasetParallelCorpusValidateIngest;
import com.ulca.dataset.kakfa.model.DatasetIngest;
import com.ulca.dataset.model.Dataset;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.TaskTracker;
import com.ulca.dataset.service.ProcessTaskTrackerService;
//import com.ulca.dataset.util.UnzipUtility;

import io.swagger.model.DatasetType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaDatasetIngestConsumer {

	@Autowired
	DatasetAsrValidateIngest datasetAsrValidateIngest;

	@Autowired
	DatasetAsrUnlabeledValidateIngest datasetAsrUnlabeledValidateIngest;

	@Autowired
	DatasetParallelCorpusValidateIngest datasetParallelCorpusValidateIngest;

	@Autowired
	DatasetOcrValidateIngest datasetOcrValidateIngest;

	@Autowired
	DatasetMonolingualValidateIngest datasetMonolingualValidateIngest;

	@Autowired
	DatasetDocumentLayoutValidateIngest datasetDocumentLayoutValidateIngest;

	@Autowired
	TaskTrackerDao taskTrackerDao;

	@Autowired
	ProcessTrackerDao processTrackerDao;

	@Autowired
	DatasetDao datasetDao;

	@KafkaListener(groupId = "${kafka.ulca.ds.ingest.ip.topic.group.id}", topics = "${kafka.ulca.ds.ingest.ip.topic}", containerFactory = "datasetIngestKafkaListenerContainerFactory")
	public void datasetIngest(DatasetIngest datasetIngest) {
		 
		log.info("************ Entry KafkaDatasetIngestConsumer :: datasetIngest *********");
		try {
			String serviceRequestNumber = datasetIngest.getServiceRequestNumber();
			String mode = datasetIngest.getMode();
			log.info("serviceRequestNumber :: " + serviceRequestNumber);
			log.info("mode :: " + mode);
			TaskTracker.ToolEnum tool = (mode.equalsIgnoreCase("real"))? TaskTracker.ToolEnum.ingest : TaskTracker.ToolEnum.pseudo;
			List<TaskTracker> list = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool);
			if(list.size() > 0) {
				log.info("Duplicate ingest processing of serviceRequestNumber :: " + serviceRequestNumber + " and mode :: " + mode);
				return;
			}
			
			/*
			if (mode.equalsIgnoreCase("real")) {
				ProcessTracker processTracker = processTrackerDao.findByServiceRequestNumber(serviceRequestNumber);
				String userId = processTracker.getUserId();
				Dataset dataset = datasetDao.findByDatasetId(processTracker.getDatasetId());
				datasetIngest.setUserId(userId);
				datasetIngest.setDatasetId(processTracker.getDatasetId());
				datasetIngest.setDatasetName(dataset.getDatasetName());
				datasetIngest.setDatasetType(DatasetType.fromValue(dataset.getDatasetType()));

			}
			*/

			DatasetType datasetType = datasetIngest.getDatasetType();

			switch (datasetType) {

			case PARALLEL_CORPUS:
				log.info("calling the parallel-corpus validate service");
				datasetParallelCorpusValidateIngest.validateIngest(datasetIngest);
				break;

			case ASR_CORPUS:
				log.info("calling the asr validate service");
				datasetAsrValidateIngest.validateIngest(datasetIngest);
				break;

			case ASR_UNLABELED_CORPUS:
				log.info("calling the asr-unlabeled-corpus validate service");
				datasetAsrUnlabeledValidateIngest.validateIngest(datasetIngest);
				break;
			case OCR_CORPUS:
				log.info("calling the ocr-corpus validate service");
				datasetOcrValidateIngest.validateIngest(datasetIngest);
				break;

			case MONOLINGUAL_CORPUS:
				log.info("calling the monolingual-corpus validate service");
				datasetMonolingualValidateIngest.validateIngest(datasetIngest);
				break;

			case DOCUMENT_LAYOUT_CORPUS:
				log.info("calling the document-layout-corpus validate service");
				datasetDocumentLayoutValidateIngest.validateIngest(datasetIngest);
				break;

			default:
				log.info("datasetType for serviceRequestNumber not one of defined datasetType");
				break;
			}

			log.info("************ Exit KafkaDatasetIngestConsumer :: datasetIngest *********");

		} catch (

		Exception e) {
			log.info("Unhadled Exception :: " + e.getMessage());
			log.info("cause :: " + e.getClass());
			e.printStackTrace();

		}

	}
}
