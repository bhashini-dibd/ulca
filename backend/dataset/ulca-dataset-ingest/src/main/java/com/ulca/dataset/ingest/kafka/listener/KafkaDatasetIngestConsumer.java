package com.ulca.dataset.ingest.kafka.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ulca.dataset.dao.DatasetDao;
import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.kakfa.DatasetAsrUnlabeledValidateIngest;
import com.ulca.dataset.kakfa.DatasetAsrValidateIngest;
import com.ulca.dataset.kakfa.DatasetDocumentLayoutValidateIngest;
import com.ulca.dataset.kakfa.DatasetGlossaryCorpusValidateIngest;
import com.ulca.dataset.kakfa.DatasetMonolingualValidateIngest;
import com.ulca.dataset.kakfa.DatasetOcrValidateIngest;
import com.ulca.dataset.kakfa.DatasetParallelCorpusValidateIngest;
import com.ulca.dataset.kakfa.DatasetTransliterationValidateIngest;
import com.ulca.dataset.kakfa.DatasetTtsValidateIngest;
import com.ulca.dataset.kakfa.model.DatasetIngest;
import com.ulca.dataset.model.TaskTracker;

import io.swagger.model.DatasetType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaDatasetIngestConsumer {

	@Autowired
	DatasetAsrValidateIngest datasetAsrValidateIngest;
	
	@Autowired
	DatasetTtsValidateIngest datasetTtsValidateIngest;

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
	DatasetTransliterationValidateIngest datasetTransliterationValidateIngest;
	
	@Autowired
	DatasetGlossaryCorpusValidateIngest datasetGlossaryCorpusValidateIngest;

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
			TaskTracker.ToolEnum tool = (mode.equalsIgnoreCase("real"))? TaskTracker.ToolEnum.ingest : TaskTracker.ToolEnum.precheck;
			List<TaskTracker> list = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString());
			if(list.size() > 0) {
				log.info("Duplicate ingest processing of serviceRequestNumber :: " + serviceRequestNumber + " and mode :: " + mode);
				return;
			}
			
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

			case TTS_CORPUS:
				log.info("calling the tts validate service");
				datasetTtsValidateIngest.validateIngest(datasetIngest);
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
				
			case TRANSLITERATION_CORPUS:
				log.info("calling the transliteration-corpus validate service");
				datasetTransliterationValidateIngest.validateIngest(datasetIngest);
				break;

			case GLOSSARY_CORPUS:
				log.info("calling the glossary-corpus validate service");
				datasetGlossaryCorpusValidateIngest.validateIngest(datasetIngest);
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
