package com.ulca.dataset.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ulca.dataset.constants.DatasetConstants;
import com.ulca.dataset.dao.DatasetDao;
import com.ulca.dataset.dao.FileIdentifierDao;
import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.exception.ServiceRequestNumberNotFoundException;
import com.ulca.dataset.kakfa.model.FileDownload;
import com.ulca.dataset.model.Dataset;
import com.ulca.dataset.model.Fileidentifier;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.ProcessTracker.ServiceRequestActionEnum;
import com.ulca.dataset.model.ProcessTracker.ServiceRequestTypeEnum;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.model.TaskTracker;
import com.ulca.dataset.model.TaskTracker.ToolEnum;
import com.ulca.dataset.request.DatasetCorpusSearchRequest;
import com.ulca.dataset.request.DatasetSubmitRequest;
import com.ulca.dataset.response.DatasetByIdResponse;
import com.ulca.dataset.response.DatasetByServiceReqNrResponse;
import com.ulca.dataset.response.DatasetCorpusSearchResponse;
import com.ulca.dataset.response.DatasetListByUserIdResponse;
import com.ulca.dataset.response.DatasetListByUserIdResponseDto;
import com.ulca.dataset.response.DatasetSearchStatusResponse;
import com.ulca.dataset.response.DatasetSubmitResponse;
import com.ulca.dataset.response.SearchListByUserIdResponse;
import com.ulca.dataset.response.SearchListByUserIdResponseDto;
import com.ulca.dataset.util.Utility;

import io.swagger.model.DatasetType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatasetService {

	private int  PAGE_SIZE = 10;
	
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
	private KafkaTemplate<String, FileDownload> datasetFiledownloadKafkaTemplate;

	@Value("${kafka.ulca.ds.filedownload.ip.topic}")
	private String fileDownloadTopic;

	@Transactional
	public DatasetSubmitResponse datasetSubmit(DatasetSubmitRequest request) {

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
		
		datasetFiledownloadKafkaTemplate.send(fileDownloadTopic, fileDownload);
		
		String message = "Dataset Submit success";
		return new DatasetSubmitResponse(message,processTracker.getServiceRequestNumber(), dataset.getDatasetId(),
				dataset.getCreatedOn());
	}
	
	public DatasetListByUserIdResponse datasetListByUserId(String userId, Integer startPage, Integer endPage) {
		
		DatasetListByUserIdResponse response = null;
		
		if(startPage == null) {
			response = datasetListByUserIdFetchAll(userId);
		} else {
			
			response = datasetListByUserIdPagination(userId, startPage, endPage);
		}
		return response;
	}

	public DatasetListByUserIdResponse datasetListByUserIdPagination(String userId, Integer startPage, Integer endPage) {

		log.info("******** Entry DatasetService:: datasetListByUserIdPagination *******");
		
		List<DatasetListByUserIdResponseDto> list = new ArrayList<DatasetListByUserIdResponseDto>();
		int startPg = startPage - 1;
		for(int i= startPg; i< endPage; i++) {
			
			Pageable paging = PageRequest.of(i, PAGE_SIZE);
			Page<ProcessTracker> processTrackerPage = processTrackerDao.findByUserId(userId,paging);
			
			for (ProcessTracker p : processTrackerPage) {
				if (p.getDatasetId() != null && !p.getDatasetId().isEmpty()) {
					
					String status = p.getStatus().toString();
					Optional<Dataset> dataset = datasetDao.findById(p.getDatasetId());
					
					if(status.equalsIgnoreCase(TaskTracker.StatusEnum.failed.toString()) || status.equalsIgnoreCase(TaskTracker.StatusEnum.completed.toString())) {
						list.add(new DatasetListByUserIdResponseDto(p.getDatasetId(), p.getServiceRequestNumber(),
								dataset.get().getDatasetName(),dataset.get().getDatasetType(), dataset.get().getCreatedOn(), status));
					}else {
						
						List<TaskTracker> taskTrackerList = taskTrackerDao
								.findAllByServiceRequestNumber(p.getServiceRequestNumber());

						HashMap<String, String> map = new HashMap<String, String>();
						for (TaskTracker tTracker : taskTrackerList) {
							map.put(tTracker.getTool().toString(), tTracker.getStatus().toString());
						}
						if(map.containsValue(TaskTracker.StatusEnum.failed.toString())) {
							status = ProcessTracker.StatusEnum.failed.toString();
						}else if(map.containsValue(ProcessTracker.StatusEnum.inprogress.toString())) {
							status = ProcessTracker.StatusEnum.inprogress.toString();
						}else if (map.containsKey(TaskTracker.ToolEnum.publish.toString())) {
							status = map.get(TaskTracker.ToolEnum.publish.toString());
						} 

						list.add(new DatasetListByUserIdResponseDto(p.getDatasetId(), p.getServiceRequestNumber(),
								dataset.get().getDatasetName(),dataset.get().getDatasetType(), dataset.get().getCreatedOn(), status));
					}
				}
			}
		}

		String msg = "Dataset List By userId";
		DatasetListByUserIdResponse response = new DatasetListByUserIdResponse(msg,list, startPage, endPage);
		log.info("******** Exit DatasetService:: datasetListByUserIdPagination *******");
		return response;

	}
	
	public DatasetListByUserIdResponse datasetListByUserIdFetchAll(String userId) {

		log.info("******** Entry DatasetService:: datasetListByUserIdFetchAll *******");

		List<DatasetListByUserIdResponseDto> list = new ArrayList<DatasetListByUserIdResponseDto>();

		List<ProcessTracker> processList = processTrackerDao.findByUserId(userId);

		for (ProcessTracker p : processList) {
			if (p.getDatasetId() != null && !p.getDatasetId().isEmpty()) {
				
				String status = p.getStatus().toString();
				Optional<Dataset> dataset = datasetDao.findById(p.getDatasetId());
				
				if(status.equalsIgnoreCase(TaskTracker.StatusEnum.failed.toString()) || status.equalsIgnoreCase(TaskTracker.StatusEnum.completed.toString())) {
					list.add(new DatasetListByUserIdResponseDto(p.getDatasetId(), p.getServiceRequestNumber(),
							dataset.get().getDatasetName(),dataset.get().getDatasetType(), dataset.get().getCreatedOn(), status));
				}else {
					
					List<TaskTracker> taskTrackerList = taskTrackerDao
							.findAllByServiceRequestNumber(p.getServiceRequestNumber());

					HashMap<String, String> map = new HashMap<String, String>();
					for (TaskTracker tTracker : taskTrackerList) {
						map.put(tTracker.getTool().toString(), tTracker.getStatus().toString());
					}
					if(map.containsValue(TaskTracker.StatusEnum.failed.toString())) {
						status = ProcessTracker.StatusEnum.failed.toString();
					}else if(map.containsValue(ProcessTracker.StatusEnum.inprogress.toString())) {
						status = ProcessTracker.StatusEnum.inprogress.toString();
					}else if (map.containsKey(TaskTracker.ToolEnum.publish.toString())) {
						status = map.get(TaskTracker.ToolEnum.publish.toString());
					} 

					list.add(new DatasetListByUserIdResponseDto(p.getDatasetId(), p.getServiceRequestNumber(),
							dataset.get().getDatasetName(),dataset.get().getDatasetType(), dataset.get().getCreatedOn(), status));
				}
			}
		}
		String msg = "Dataset List By userId";
		DatasetListByUserIdResponse response = new DatasetListByUserIdResponse(msg,list);
		log.info("******** Exit DatasetService:: datasetListByUserIdFetchAll *******");
		return response;

	}
	
	public DatasetByIdResponse datasetById(String datasetId) {

		Map<String, ArrayList<TaskTracker>> map = new HashMap<String, ArrayList<TaskTracker>>();

		List<ProcessTracker> processTrackerList = processTrackerDao.findByDatasetId(datasetId);

		if (processTrackerList != null && processTrackerList.size() > 0) {

			for (ProcessTracker pt : processTrackerList) {

				String serviceRequestNumber = pt.getServiceRequestNumber();

				List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumber(serviceRequestNumber);

				
				List<TaskTracker> taskTrackerListUpdated = getStatusUpdatedTaskTrackerList(taskTrackerList, serviceRequestNumber);
				map.put(serviceRequestNumber, (ArrayList<TaskTracker>) taskTrackerListUpdated);
			}

		}
		DatasetByIdResponse response = new DatasetByIdResponse("Dataset Details", map);
		return response;
	}

	public DatasetByServiceReqNrResponse datasetByServiceRequestNumber(String serviceRequestNumber) {
		
		ProcessTracker processTrackerList = processTrackerDao.findByServiceRequestNumber(serviceRequestNumber);
		if(processTrackerList == null) {
			throw new ServiceRequestNumberNotFoundException("serviceRequestNumber :: " + serviceRequestNumber + " not found");
		}

		List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumber(serviceRequestNumber);
		
		List<TaskTracker> taskTrackerListUpdated = getStatusUpdatedTaskTrackerList(taskTrackerList, serviceRequestNumber);
		
		DatasetByServiceReqNrResponse response = new DatasetByServiceReqNrResponse("Dataset details", taskTrackerListUpdated);
		return response;
	}
	
	public DatasetCorpusSearchResponse corpusSearch(DatasetCorpusSearchRequest request)
			throws JsonProcessingException {

		log.info("******** Entry DatasetService:: corpusSearch *******");
		
		String userId = request.getUserId();
		String serviceRequestNumber = searchKafkaPublish.searchPublish(request, userId);

		String message = "Search has been initiated";
		DatasetCorpusSearchResponse response = new DatasetCorpusSearchResponse(message, serviceRequestNumber);
				
		return response;
	}
	

	public DatasetSearchStatusResponse searchStatus(String serviceRequestNumber) {

		ProcessTracker processTracker = processTrackerDao.findByServiceRequestNumber(serviceRequestNumber);
		if(processTracker == null) {
			throw new ServiceRequestNumberNotFoundException("serviceRequestNumber :: " + serviceRequestNumber + " not found");
		}

		List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumber(serviceRequestNumber);

		String msg = "Search status";
		return new DatasetSearchStatusResponse(msg,processTracker.getServiceRequestNumber(), processTracker.getStartTime(),
				processTracker.getSearchCriterion(), taskTrackerList);
	}
	
	public SearchListByUserIdResponse searchListByUserId(String userId, Integer startPage, Integer endPage) {
		
		log.info("******** Entry DatasetService:: searchListByUserId *******" );
		
		SearchListByUserIdResponse response = null;
		if(startPage == null) {
			response = searchListByUserIdFetchAll(userId);
			
		}else {
			response = searchListByUserIdPagination(userId, startPage, endPage);
		}
		
		log.info("******** Exit DatasetService:: searchListByUserId *******" );
		return response;
	}
	

	public SearchListByUserIdResponse searchListByUserIdPagination(String userId, Integer startPage, Integer endPage) {
		
		log.info("******** Entry DatasetService:: searchListByUserIdPagination *******" );

		List<SearchListByUserIdResponseDto> searchList = new ArrayList<SearchListByUserIdResponseDto>();
		
		int startPg = startPage - 1;
		for(int i= startPg; i< endPage; i++) {
			
			Pageable paging = PageRequest.of(i, PAGE_SIZE);
			Page<ProcessTracker> processTrackerPage = processTrackerDao.findByUserIdAndServiceRequestTypeAndServiceRequestAction(userId,ServiceRequestTypeEnum.dataset,ServiceRequestActionEnum.search,paging);
			List<ProcessTracker> processTrackerList = processTrackerPage.getContent();
			
				for (ProcessTracker processTracker : processTrackerList) {
					String serviceRequestNumber = processTracker.getServiceRequestNumber();
					
					if (processTracker.getSearchCriterion() != null) {
						
						List<TaskTracker> taskTrackerList = taskTrackerDao
								.findAllByServiceRequestNumber(serviceRequestNumber);

						searchList.add(new SearchListByUserIdResponseDto(processTracker.getServiceRequestNumber(),
								processTracker.getStartTime(), processTracker.getSearchCriterion(), taskTrackerList));
					}
				}
		}
		
		String msg = "Search List";
		SearchListByUserIdResponse response = new SearchListByUserIdResponse( msg, searchList) ;

		log.info("******** Exit DatasetService:: searchListByUserIdPagination *******" );
		return response;

	}

	public SearchListByUserIdResponse searchListByUserIdFetchAll(String userId) {

		log.info("******** Entry DatasetService:: searchListByUserIdFetchAll *******" );
		
		List<SearchListByUserIdResponseDto> searchList = new ArrayList<SearchListByUserIdResponseDto>();
		List<ProcessTracker> processTrackerList = processTrackerDao.findByUserIdAndServiceRequestTypeAndServiceRequestAction(userId,ServiceRequestTypeEnum.dataset,ServiceRequestActionEnum.search);
		
			for (ProcessTracker processTracker : processTrackerList) {
				String serviceRequestNumber = processTracker.getServiceRequestNumber();
				if (processTracker.getSearchCriterion() != null) {
					
					List<TaskTracker> taskTrackerList = taskTrackerDao
							.findAllByServiceRequestNumber(serviceRequestNumber);
					
					searchList.add(new SearchListByUserIdResponseDto(processTracker.getServiceRequestNumber(),
							processTracker.getStartTime(), processTracker.getSearchCriterion(), taskTrackerList));
				}
			}
		
		String msg = "Search List";
		SearchListByUserIdResponse response = new SearchListByUserIdResponse( msg, searchList) ;

		log.info("******** Exit DatasetService:: searchListByUserIdFetchAll *******" );
		
		return response;

	}
	
	
	public void updateDataset(String datasetId, String userId, JSONObject schema, String md5hash) {
		
		
				
		Optional<Dataset> datasetOps = datasetDao.findById(datasetId);
		if(!datasetOps.isEmpty()) {
			Dataset dataset = datasetOps.get();
			dataset.setDatasetType(schema.get("datasetType").toString());
			dataset.setCollectionSource(schema.get("collectionSource").toString());
			if(!schema.get("datasetType").toString().equalsIgnoreCase(DatasetType.DOCUMENT_LAYOUT_CORPUS.toString())) {
				dataset.setLanguages(schema.get("languages").toString());
			}
			
			dataset.setDomain(schema.get("domain").toString());
			dataset.setContributors(schema.get("submitter").toString());	
			dataset.setSubmitterId(userId);
			dataset.setLicense(schema.get("license").toString());
			
			/*Fileidentifier fileidentifier = dataset.getDatasetFileIdentifier();
			fileidentifier.setMd5hash(md5hash);
			*/
			
			if(schema.has("collectionMethod")) {
				dataset.setCollectionMethod(schema.get("collectionMethod").toString());
			}
			//fileIdentifierDao.save(fileidentifier);
			datasetDao.save(dataset);	
			
			
		}
		
	}
	
	
	public List<TaskTracker> getStatusUpdatedTaskTrackerList(List<TaskTracker> taskTrackerList, String serviceRequestNumber) {
		
		HashMap<String, String> mapTemp = new HashMap<String, String>();
		for (TaskTracker tTracker : taskTrackerList) {
			mapTemp.put(tTracker.getTool().toString(), tTracker.getStatus().toString());
		}
		
		if(!mapTemp.containsKey(TaskTracker.ToolEnum.download.toString())) {
			TaskTracker download = new TaskTracker();
			download.serviceRequestNumber(serviceRequestNumber);
			download.setTool(ToolEnum.download);
			download.setStatus(TaskTracker.StatusEnum.pending.toString());
			taskTrackerList.add(download);
			
			TaskTracker ingest = new TaskTracker();
			ingest.serviceRequestNumber(serviceRequestNumber);
			ingest.setTool(ToolEnum.ingest);
			ingest.setStatus(TaskTracker.StatusEnum.pending.toString());
			taskTrackerList.add(ingest);
			
			TaskTracker validate = new TaskTracker();
			validate.serviceRequestNumber(serviceRequestNumber);
			validate.setTool(ToolEnum.validate);
			validate.setStatus(TaskTracker.StatusEnum.pending.toString());
			taskTrackerList.add(validate);
			
			TaskTracker publish = new TaskTracker();
			publish.serviceRequestNumber(serviceRequestNumber);
			publish.setTool(ToolEnum.publish);
			publish.setStatus(TaskTracker.StatusEnum.pending.toString());
			taskTrackerList.add(publish);
			
			return taskTrackerList;
			
		}else if(mapTemp.get(TaskTracker.ToolEnum.download.toString()).equals(TaskTracker.StatusEnum.inprogress.toString())) {
			TaskTracker ingest = new TaskTracker();
			ingest.serviceRequestNumber(serviceRequestNumber);
			ingest.setTool(ToolEnum.ingest);
			ingest.setStatus(TaskTracker.StatusEnum.pending.toString());
			taskTrackerList.add(ingest);
			
			TaskTracker validate = new TaskTracker();
			validate.serviceRequestNumber(serviceRequestNumber);
			validate.setTool(ToolEnum.validate);
			validate.setStatus(TaskTracker.StatusEnum.pending.toString());
			taskTrackerList.add(validate);
			
			TaskTracker publish = new TaskTracker();
			publish.serviceRequestNumber(serviceRequestNumber);
			publish.setTool(ToolEnum.publish);
			publish.setStatus(TaskTracker.StatusEnum.pending.toString());
			taskTrackerList.add(publish);
			
			return taskTrackerList;
			
		
		} else if(mapTemp.get(TaskTracker.ToolEnum.download.toString()).equals(TaskTracker.StatusEnum.failed.toString())) {
			TaskTracker ingest = new TaskTracker();
			ingest.serviceRequestNumber(serviceRequestNumber);
			ingest.setTool(ToolEnum.ingest);
			ingest.setStatus(TaskTracker.StatusEnum.na.toString());
			taskTrackerList.add(ingest);
			
			TaskTracker validate = new TaskTracker();
			validate.serviceRequestNumber(serviceRequestNumber);
			validate.setTool(ToolEnum.validate);
			validate.setStatus(TaskTracker.StatusEnum.na.toString());
			taskTrackerList.add(validate);
			
			TaskTracker publish = new TaskTracker();
			publish.serviceRequestNumber(serviceRequestNumber);
			publish.setTool(ToolEnum.publish);
			publish.setStatus(TaskTracker.StatusEnum.na.toString());
			taskTrackerList.add(publish);
			
			return taskTrackerList;
			
		} else if(!mapTemp.containsKey(TaskTracker.ToolEnum.ingest.toString())) {
			TaskTracker ingest = new TaskTracker();
			ingest.serviceRequestNumber(serviceRequestNumber);
			ingest.setTool(ToolEnum.ingest);
			ingest.setStatus(TaskTracker.StatusEnum.pending.toString());
			taskTrackerList.add(ingest);
			
			TaskTracker validate = new TaskTracker();
			validate.serviceRequestNumber(serviceRequestNumber);
			validate.setTool(ToolEnum.validate);
			validate.setStatus(TaskTracker.StatusEnum.pending.toString());
			taskTrackerList.add(validate);
			
			TaskTracker publish = new TaskTracker();
			publish.serviceRequestNumber(serviceRequestNumber);
			publish.setTool(ToolEnum.publish);
			publish.setStatus(TaskTracker.StatusEnum.pending.toString());
			taskTrackerList.add(publish);
			
			return taskTrackerList;
			
		} else if(mapTemp.get(TaskTracker.ToolEnum.ingest.toString()).equals(TaskTracker.StatusEnum.failed.toString())) {
			TaskTracker validate = new TaskTracker();
			validate.serviceRequestNumber(serviceRequestNumber);
			validate.setTool(ToolEnum.validate);
			validate.setStatus(TaskTracker.StatusEnum.na.toString());
			taskTrackerList.add(validate);
			
			TaskTracker publish = new TaskTracker();
			publish.serviceRequestNumber(serviceRequestNumber);
			publish.setTool(ToolEnum.publish);
			publish.setStatus(TaskTracker.StatusEnum.na.toString());
			taskTrackerList.add(publish);
			return taskTrackerList;
			
		}else if(mapTemp.get(TaskTracker.ToolEnum.ingest.toString()).equals(TaskTracker.StatusEnum.pending.toString()) ) {
				
			TaskTracker validate = new TaskTracker();
			validate.serviceRequestNumber(serviceRequestNumber);
			validate.setTool(ToolEnum.validate);
			validate.setStatus(TaskTracker.StatusEnum.pending.toString());
			taskTrackerList.add(validate);
			
			TaskTracker publish = new TaskTracker();
			publish.serviceRequestNumber(serviceRequestNumber);
			publish.setTool(ToolEnum.publish);
			publish.setStatus(TaskTracker.StatusEnum.pending.toString());
			taskTrackerList.add(publish);
			return taskTrackerList;
			
		} else if(mapTemp.get(TaskTracker.ToolEnum.ingest.toString()).equals(TaskTracker.StatusEnum.inprogress.toString())
				|| mapTemp.get(TaskTracker.ToolEnum.ingest.toString()).equals(TaskTracker.StatusEnum.completed.toString())
				) {
			
			if(!mapTemp.containsKey(TaskTracker.ToolEnum.validate.toString())) {
				TaskTracker validate = new TaskTracker();
				validate.serviceRequestNumber(serviceRequestNumber);
				validate.setTool(ToolEnum.validate);
				validate.setStatus(TaskTracker.StatusEnum.pending.toString());
				taskTrackerList.add(validate);
			}
			if(!mapTemp.containsKey(TaskTracker.ToolEnum.publish.toString())) {
				TaskTracker validate = new TaskTracker();
				validate.serviceRequestNumber(serviceRequestNumber);
				validate.setTool(ToolEnum.publish);
				validate.setStatus(TaskTracker.StatusEnum.pending.toString());
				taskTrackerList.add(validate);
			}
			
			return taskTrackerList;
		}
		
		
		return taskTrackerList;
	}
	
	public void updateDatasetFileLocation(String serviceRequestNumber, String localUrl) {
		
		ProcessTracker processTracker = processTrackerDao.findByServiceRequestNumber(serviceRequestNumber);
		Dataset dataset = datasetDao.findByDatasetId(processTracker.getDatasetId());
		dataset.getDatasetFileIdentifier().setFileUlcaUrl(localUrl);
		
		Fileidentifier fileidentifier = dataset.getDatasetFileIdentifier();
		fileidentifier.setFileUlcaUrl(localUrl);
		fileIdentifierDao.save(fileidentifier);
	}

}
