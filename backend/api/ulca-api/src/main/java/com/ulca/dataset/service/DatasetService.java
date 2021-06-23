package com.ulca.dataset.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ulca.dataset.dao.DatasetDao;
import com.ulca.dataset.dao.FileIdentifierDao;
import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.kakfa.FileDownload;
import com.ulca.dataset.model.Dataset;
import com.ulca.dataset.model.Fileidentifier;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.ProcessTracker.ServiceRequestActionEnum;
import com.ulca.dataset.model.ProcessTracker.ServiceRequestTypeEnum;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.model.TaskTracker;
import com.ulca.dataset.request.DatasetCorpusSearchRequest;
import com.ulca.dataset.request.DatasetSubmitRequest;
import com.ulca.dataset.response.DatasetCorpusSearchResponse;
import com.ulca.dataset.response.DatasetListByUserIdResponse;
import com.ulca.dataset.response.DatasetSearchStatusResponse;
import com.ulca.dataset.response.DatasetSubmitResponse;
import com.ulca.dataset.util.DateUtil;
import com.ulca.dataset.util.Utility;

import lombok.extern.slf4j.Slf4j;

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
	TaskTrackerDao taskTrackerDao;

	@Autowired
	SearchKafkaPublish searchKafkaPublish;

	@Autowired
	private KafkaTemplate<String, Object> template;

	@Value(value = "${KAFKA_ULCA_DS_INGEST_IP_TOPIC}")
	private String fileDownloadTopic;

	@Transactional
	public DatasetSubmitResponse datasetSubmit(DatasetSubmitRequest request, String userId) {

		Dataset dataset = new Dataset();
		dataset.setDatasetName(request.getDatasetName());
		dataset.setDatasetType(request.getType().name());
		dataset.setCreatedOn(new Date().toString());

		Fileidentifier fileIndetifier = new Fileidentifier();
		fileIndetifier.setFileLocationURL(request.getUrl());

		fileIndetifier.setCreatedOn(new Date().toString());
		fileIdentifierDao.insert(fileIndetifier);

		dataset.setDatasetFileIdentifier(fileIndetifier);

		datasetDao.insert(dataset);

		ProcessTracker processTracker = new ProcessTracker();
		processTracker.setUserId(userId);
		processTracker.setDatasetId(dataset.getDatasetId());
		processTracker.setServiceRequestNumber(Utility.getDatasetSubmitReferenceNumber());
		processTracker.setServiceRequestAction(ServiceRequestActionEnum.submit);
		processTracker.setServiceRequestType(ServiceRequestTypeEnum.dataset);
		processTracker.setStatus(StatusEnum.notstarted);
		processTracker.setStartTime(new Date().toString());

		processTrackerDao.insert(processTracker);

		FileDownload fileDownload = new FileDownload();
		fileDownload.setUserId(userId);
		fileDownload.setDatasetId(dataset.getDatasetId());
		fileDownload.setDatasetName(dataset.getDatasetName());
		fileDownload.setDatasetType(request.getType());
		fileDownload.setFileUrl(request.getUrl());
		fileDownload.setServiceRequestNumber(processTracker.getServiceRequestNumber());

		template.send(fileDownloadTopic, fileDownload);

		return new DatasetSubmitResponse(processTracker.getServiceRequestNumber(), dataset.getDatasetId(),
				dataset.getCreatedOn());
	}

	public List<DatasetListByUserIdResponse> datasetListByUserId(String userId) {

		log.info("******** Entry DatasetService:: dataSetListByUserId *******");

		List<DatasetListByUserIdResponse> list = new ArrayList<DatasetListByUserIdResponse>();

		List<ProcessTracker> processList = processTrackerDao.findByUserId(userId);

		for (ProcessTracker p : processList) {
			if (p.getDatasetId() != null && !p.getDatasetId().isEmpty()) {

				String status = p.getStatus().toString();
				Optional<Dataset> dataset = datasetDao.findById(p.getDatasetId());

				List<TaskTracker> taskTrackerList = taskTrackerDao
						.findAllByServiceRequestNumber(p.getServiceRequestNumber());

				HashMap<String, String> map = new HashMap<String, String>();
				for (TaskTracker tTracker : taskTrackerList) {
					map.put(tTracker.getTool().toString(), tTracker.getStatus().toString());
				}
				if (map.containsKey("publish")) {
					status = map.get("publish");
				} else if (map.containsKey("validate")) {
					status = map.get("validate");
				}

				list.add(new DatasetListByUserIdResponse(p.getDatasetId(), p.getServiceRequestNumber(),
						dataset.get().getDatasetName(), dataset.get().getCreatedOn(), status));
			}

		}

		log.info("******** Exit DatasetService:: dataSetListByUserId *******");
		return list;

	}

	public DatasetCorpusSearchResponse corpusSearch(DatasetCorpusSearchRequest request, String userId)
			throws JsonProcessingException {

		log.info("******** Entry DatasetService:: corpusSearch *******");

		String serviceRequestNumber = searchKafkaPublish.searchPublish(request, userId);

		DatasetCorpusSearchResponse response = new DatasetCorpusSearchResponse(serviceRequestNumber, new Date());
		return response;
	}

	public Map<String, ArrayList<TaskTracker>> datasetById(String datasetId) {

		Map<String, ArrayList<TaskTracker>> map = new HashMap<String, ArrayList<TaskTracker>>();

		List<ProcessTracker> processTrackerList = processTrackerDao.findByDatasetId(datasetId);

		if (processTrackerList != null && processTrackerList.size() > 0) {

			for (ProcessTracker pt : processTrackerList) {

				String serviceRequestNumber = pt.getServiceRequestNumber();

				List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumber(serviceRequestNumber);

				map.put(serviceRequestNumber, (ArrayList<TaskTracker>) taskTrackerList);
			}

		}

		return map;
	}

	public List<TaskTracker> datasetByServiceRequestNumber(String serviceRequestNumber) {

		return taskTrackerDao.findAllByServiceRequestNumber(serviceRequestNumber);
	}

	public DatasetSearchStatusResponse searchStatus(String serviceRequestNumber) {

		ProcessTracker processTracker = processTrackerDao.findByServiceRequestNumber(serviceRequestNumber);

		List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumber(serviceRequestNumber);

		return new DatasetSearchStatusResponse(processTracker.getServiceRequestNumber(), processTracker.getStartTime(),
				processTracker.getSearchCriterion(), taskTrackerList);
	}

	public List<DatasetSearchStatusResponse> searchListByUserId(String userId) {

		List<DatasetSearchStatusResponse> searchList = new ArrayList<DatasetSearchStatusResponse>();

		List<ProcessTracker> processTrackerList = processTrackerDao.findByUserId(userId);

		if (processTrackerList != null && processTrackerList.size() > 0) {

			for (ProcessTracker processTracker : processTrackerList) {
				String serviceRequestNumber = processTracker.getServiceRequestNumber();
				if (processTracker.getSearchCriterion() != null) {
					List<TaskTracker> taskTrackerList = taskTrackerDao
							.findAllByServiceRequestNumber(serviceRequestNumber);

					searchList.add(new DatasetSearchStatusResponse(processTracker.getServiceRequestNumber(),
							processTracker.getStartTime(), processTracker.getSearchCriterion(), taskTrackerList));
				}

			}

		}

		return searchList;

	}

}
