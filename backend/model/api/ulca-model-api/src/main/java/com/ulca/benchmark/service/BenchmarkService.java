package com.ulca.benchmark.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulca.benchmark.constant.BenchmarkConstants;
import com.ulca.benchmark.dao.BenchmarkDao;
import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.dao.BenchmarkProcessTrackerDao;
import com.ulca.benchmark.exception.BenchmarkNotAllowedException;
import com.ulca.benchmark.exception.BenchmarkNotFoundException;
import com.ulca.benchmark.kafka.model.BenchmarkIngest;
import com.ulca.benchmark.kafka.model.BmDatasetDownload;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.benchmark.model.BenchmarkProcessTracker;
import com.ulca.benchmark.model.BenchmarkProcessTracker.ServiceRequestActionEnum;
import com.ulca.benchmark.model.BenchmarkProcessTracker.ServiceRequestTypeEnum;
import com.ulca.benchmark.model.BenchmarkProcessTracker.StatusEnum;
import com.ulca.benchmark.model.BenchmarkSubmissionType;
import com.ulca.benchmark.request.BenchmarkMetricRequest;
import com.ulca.benchmark.request.BenchmarkSearchRequest;
import com.ulca.benchmark.request.BenchmarkSubmitRequest;
import com.ulca.benchmark.request.BenchmarkListByModelRequest;
import com.ulca.benchmark.request.ExecuteBenchmarkRequest;
import com.ulca.benchmark.response.BenchmarkDto;
import com.ulca.benchmark.response.BenchmarkListByModelResponse;
import com.ulca.benchmark.response.BenchmarkListByUserIdResponse;
import com.ulca.benchmark.response.BenchmarkSearchResponse;
import com.ulca.benchmark.response.BenchmarkSubmitResponse;
import com.ulca.benchmark.response.ExecuteBenchmarkResponse;
import com.ulca.benchmark.response.GetBenchmarkByIdResponse;
import com.ulca.benchmark.util.ModelConstants;
import com.ulca.benchmark.util.Utility;
import com.ulca.model.dao.ModelDao;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.exception.ModelNotFoundException;
import com.ulca.model.exception.RequestParamValidationException;
import com.ulca.model.response.BmProcessListByProcessIdResponse;

import io.swagger.model.Benchmark;
import io.swagger.model.LanguagePair;
import io.swagger.model.LanguagePairs;
import io.swagger.model.ModelTask;
import io.swagger.model.LanguagePair.SourceLanguageEnum;
import io.swagger.model.LanguagePair.TargetLanguageEnum;
import io.swagger.model.ModelTask.TypeEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BenchmarkService {

	private int PAGE_SIZE = 10;
	@Autowired
	private KafkaTemplate<String, BmDatasetDownload> benchmarkDownloadKafkaTemplate;

	@Value("${kafka.ulca.bm.filedownload.ip.topic}")
	private String benchmarkDownloadTopic;
	
	@Autowired
	private KafkaTemplate<String, BenchmarkIngest> benchmarkIngestKafkaTemplate;

	@Value("${kafka.ulca.bm.ingest.ip.topic}")
	private String benchmarkIngestTopic;
	

	@Autowired
	BenchmarkDao benchmarkDao;
	
	@Autowired
	ModelDao modelDao;
	
	@Autowired
	BenchmarkProcessTrackerDao benchmarkProcessTrackerDao;
	

	@Autowired
	BenchmarkProcessDao benchmarkprocessDao;
	
	@Autowired
	ModelConstants modelConstants;

	public BenchmarkSubmitResponse submitBenchmark(BenchmarkSubmitRequest request) throws RequestParamValidationException {

		Benchmark benchmark = new Benchmark();
		benchmark.setName(request.getDatasetName());
		benchmark.setUserId(request.getUserId());
		benchmark.setDataset(request.getUrl());
		benchmark.setStatus(BenchmarkSubmissionType.SUBMITTED.toString());		
		benchmark.setSubmittedOn(new Date().toString());	
		benchmark.setCreatedOn(new Date().toString());
		
		if (benchmark != null) {
			try {
				benchmarkDao.save(benchmark);
			} catch (DuplicateKeyException ex) {
				ex.printStackTrace();
				throw new DuplicateKeyException(BenchmarkConstants.datasetNameUniqueErrorMsg);
			}
		}
		
		BenchmarkProcessTracker benchmarkprocessTracker = new BenchmarkProcessTracker();
		benchmarkprocessTracker.setId(benchmark.getBenchmarkId());
		benchmarkprocessTracker.setDatasetId(benchmark.getBenchmarkId());
		//	benchmarkprocessTracker.setServiceRequestNumber(Utility.getDatasetSubmitReferenceNumber());
		benchmarkprocessTracker.setServiceRequestAction(ServiceRequestActionEnum.submit);
		benchmarkprocessTracker.setServiceRequestType(ServiceRequestTypeEnum.dataset);
		benchmarkprocessTracker.setStatus(StatusEnum.pending.toString());
		benchmarkprocessTracker.setStartTime(new Date().toString());

		benchmarkProcessTrackerDao.insert(benchmarkprocessTracker);
//
//		FileDownload fileDownload = new FileDownload();
//		fileDownload.setUserId(userId);
//		fileDownload.setDatasetId(dataset.getDatasetId());
//		fileDownload.setDatasetName(dataset.getDatasetName());
//		//fileDownload.setDatasetType(request.getType());
//		fileDownload.setFileUrl(request.getUrl());
//		fileDownload.setServiceRequestNumber(processTracker.getServiceRequestNumber());
//		
//		//datasetFiledownloadKafkaTemplate.send(fileDownloadTopic, fileDownload);
//		
//		
//		try {
//			
//			 ListenableFuture<SendResult<String, FileDownload>> future = datasetFiledownloadKafkaTemplate.send(fileDownloadTopic, fileDownload);
//				
//				 future.addCallback(new ListenableFutureCallback<SendResult<String, FileDownload>>() {
//
//					    public void onSuccess(SendResult<String, FileDownload> result) {
//					    	log.info("message sent successfully to fileDownloadTopic, serviceRequestNumber :: "+ processTracker.getServiceRequestNumber());
//					    }
//
//					    @Override
//					    public void onFailure(Throwable ex) {
//					    	log.info("Error occured while sending message to fileDownloadTopic, serviceRequestNumber :: "+ processTracker.getServiceRequestNumber());
//					    	log.info("Error message :: " + ex.getMessage());
//					    	
//					    	DatasetKafkaTransactionErrorLog error = new DatasetKafkaTransactionErrorLog();
//					    	error.setServiceRequestNumber(processTracker.getServiceRequestNumber());
//					    	error.setAttempt(0);
//					    	error.setCreatedOn(new Date().toString());
//					    	error.setLastModifiedOn(new Date().toString());
//					    	error.setFailed(false);
//					    	error.setSuccess(false);
//					    	error.setStage("download");
//					    	List<String> er = new ArrayList<String>();
//					    	er.add(ex.getMessage());
//					    	error.setErrors(er);
//					    	ObjectMapper mapper = new ObjectMapper();
//						
//								String dataRow;
//								try {
//									dataRow = mapper.writeValueAsString(fileDownload);
//							    	error.setData(dataRow);
//							    	datasetKafkaTransactionErrorLogDao.save(error);
//							    	
//								} catch (JsonProcessingException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//								
//					    	
//					    }
//					});
//				 
//				 
//			
//		}catch ( KafkaException ex) {
//			log.info("Error occured while sending message to fileDownloadTopic, serviceRequestNumber :: "+ processTracker.getServiceRequestNumber());
//			log.info("Error message :: " + ex.getMessage());
//			DatasetKafkaTransactionErrorLog error = new DatasetKafkaTransactionErrorLog();
//	    	error.setServiceRequestNumber(processTracker.getServiceRequestNumber());
//	    	error.setAttempt(0);
//	    	error.setCreatedOn(new Date().toString());
//	    	error.setLastModifiedOn(new Date().toString());
//	    	error.setFailed(false);
//	    	error.setSuccess(false);
//	    	error.setStage("download");
//	    	List<String> er = new ArrayList<String>();
//	    	er.add(ex.getMessage());
//	    	error.setErrors(er);
//	    	ObjectMapper mapper = new ObjectMapper();
//		
//				String dataRow;
//				try {
//					dataRow = mapper.writeValueAsString(fileDownload);
//			    	error.setData(dataRow);
//			    	datasetKafkaTransactionErrorLogDao.save(error);
//			    	
//				} catch (JsonProcessingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				
//			throw ex;
//		}
		
		
		//send data to benchmark ingest topic to download benmark and validate and update
		
		BenchmarkIngest benchmarkIngest = new BenchmarkIngest();
		benchmarkIngest.setBenchmarkId(benchmark.getBenchmarkId());
		benchmarkIngestKafkaTemplate.send(benchmarkIngestTopic, benchmarkIngest);
		
		return new BenchmarkSubmitResponse("Benchmark has been Submitted", benchmark.getBenchmarkId(), benchmark.getStatus());
	}

	@Transactional
	public ExecuteBenchmarkResponse executeBenchmark(ExecuteBenchmarkRequest request) {

		log.info("******** Entry BenchmarkService:: executeBenchmark *******");

		
		String modelId = request.getModelId();
		Optional<ModelExtended> model = modelDao.findById(modelId);
		if(model.isEmpty()) {
			throw new ModelNotFoundException("Model with not modelId : " + modelId + " not available ");
		}
		
		ModelExtended modelExtended = model.get();
		
		List<String> benchmarkProcessIds = new ArrayList<String>();

		for (BenchmarkMetricRequest bm : request.getBenchmarks()) {
			Benchmark benchmark = benchmarkDao.findByBenchmarkId(bm.getBenchmarkId());
			if(benchmark == null ) {
				throw new BenchmarkNotFoundException("Benchmark : " + bm.getBenchmarkId() + " not found ");
			}
			List<BenchmarkProcess> isExistBmProcess = benchmarkprocessDao.findByModelIdAndBenchmarkDatasetIdAndMetric(modelId,bm.getBenchmarkId(),bm.getMetric());
			if(isExistBmProcess != null && isExistBmProcess.size()>0 ) {
				
				for(BenchmarkProcess existingBm : isExistBmProcess) {
					String status = existingBm.getStatus();
					if(status.equalsIgnoreCase("Completed") || status.equalsIgnoreCase("In-Progress") ) {
						String message = "Benchmark has already been executed for benchmarkId : " + bm.getBenchmarkId() + " and metric : " + bm.getMetric();
						throw new BenchmarkNotAllowedException(message);
					 }
					}
			}
			String serviceRequestNumber = Utility.getBenchmarkExecuteReferenceNumber();
			
			BenchmarkProcess bmProcess = new BenchmarkProcess();
			bmProcess.setBenchmarkDatasetId(bm.getBenchmarkId());
			bmProcess.setBenchmarkProcessId(serviceRequestNumber);
			bmProcess.setMetric(bm.getMetric());
			bmProcess.setBenchmarkDatasetName(benchmark.getName());
			bmProcess.setModelId(modelId);
			bmProcess.setModelName(modelExtended.getName());
			bmProcess.setStatus("In-Progress");
			bmProcess.setCreatedOn(new Date().toString());
			bmProcess.setLastModifiedOn(new Date().toString());
			benchmarkprocessDao.save(bmProcess);
			
			BmDatasetDownload bmDsDownload = new BmDatasetDownload(serviceRequestNumber);
			benchmarkDownloadKafkaTemplate.send(benchmarkDownloadTopic, bmDsDownload);
			benchmarkProcessIds.add(serviceRequestNumber);

		}

		ExecuteBenchmarkResponse response = new ExecuteBenchmarkResponse();
		response.setBenchmarkProcessIds(benchmarkProcessIds);

		log.info("******** Exit BenchmarkService:: executeBenchmark *******");

		return response;

	}

	public BenchmarkListByModelResponse listByTaskID(BenchmarkListByModelRequest request) {

		log.info("******** Entry BenchmarkService:: listByTaskID *******");

		BenchmarkListByModelResponse response = null;
		Benchmark benchmark = new Benchmark();
		
		ModelExtended model= modelDao.findByModelId(request.getModelId());
		List<BenchmarkDto> dtoList = new ArrayList<BenchmarkDto>();
		
		LanguagePairs lps = model.getLanguages();
		for(LanguagePair lp : lps) {
			benchmark.setLanguages(lp);
			benchmark.setTask(model.getTask());
			Example<Benchmark> example = Example.of(benchmark);
			List<Benchmark> list = benchmarkDao.findAll(example);
			
			for(Benchmark bm : list) {
				BenchmarkDto dto = new BenchmarkDto();
				BeanUtils.copyProperties(bm, dto);
				List<String> metricList = modelConstants.getMetricList(bm.getTask().getType().toString());
				dto.setMetric(new ArrayList<>(metricList));
				List<BenchmarkProcess> bmProcList = benchmarkprocessDao.findByModelIdAndBenchmarkDatasetId(request.getModelId(),bm.getBenchmarkId());
				for(BenchmarkProcess bmProc : bmProcList) {
					String status = bmProc.getStatus();
					if(status != null && !status.isBlank() && (status.equalsIgnoreCase("Completed") || status.equalsIgnoreCase("In-Progress"))) {
						metricList.remove(bmProc.getMetric());
					}
				}
				dto.setAvailableMetric(metricList);
				dtoList.add(dto);
				
			}
		}
		response = new BenchmarkListByModelResponse("Benchmark Search Result", dtoList,dtoList.size());
		log.info("******** Exit BenchmarkService:: listByTaskID *******");
		return response;
	}

	
	
	public BenchmarkSearchResponse searchBenchmark(BenchmarkSearchRequest request, Integer startPage, Integer endPage) {

		Benchmark benchmark = new Benchmark();
		Example<Benchmark> example = Example.of(benchmark);
		List<Benchmark> list = new ArrayList<>();
		
		if (request.getTask() != null && !request.getTask().isBlank()) {
			ModelTask modelTask = new ModelTask();
			modelTask.setType(TypeEnum.fromValue(request.getTask()));
			benchmark.setTask(modelTask);
		}

		if (request.getSourceLanguage() != null && !request.getSourceLanguage().isBlank()) {
			LanguagePair lp = new LanguagePair();
			lp.setSourceLanguage(SourceLanguageEnum.fromValue(request.getSourceLanguage()));

			if (request.getTargetLanguage() != null && !request.getTargetLanguage().isBlank()) {
				lp.setTargetLanguage(TargetLanguageEnum.fromValue(request.getTargetLanguage()));
			}
			benchmark.setLanguages(lp);
		}
		
		if (startPage != null) {
			int startPg = startPage - 1;
			for (int i = startPg; i < endPage; i++) {
				Pageable paging = PageRequest.of(i, PAGE_SIZE);
			  list = (List<Benchmark>) benchmarkDao.findAll(example,paging);
			}
		} else {
			 list =benchmarkDao.findAll(example);
		}

		return new BenchmarkSearchResponse("Benchmark Search Result", list, list.size());

	}
	
	
	public BmProcessListByProcessIdResponse processStatus(String benchmarkProcessId ){
		
		BenchmarkProcess benchmarkProcess =  benchmarkprocessDao.findByBenchmarkProcessId(benchmarkProcessId);
		
		List<BenchmarkProcess> list = new ArrayList<BenchmarkProcess>();
		list.add(benchmarkProcess);
		
		BmProcessListByProcessIdResponse response = new BmProcessListByProcessIdResponse("Benchmark Process list", list, list.size());
		
		return response;
		
		
	}

	public GetBenchmarkByIdResponse getBenchmarkById(String benchmarkId) {
		
		Benchmark result = benchmarkDao.findByBenchmarkId(benchmarkId);

		if (result != null) {
			GetBenchmarkByIdResponse bmDto = new GetBenchmarkByIdResponse();
			BeanUtils.copyProperties(result, bmDto);
			List<String> metricList = modelConstants.getMetricList(result.getTask().getType().toString());
			bmDto.setMetric(metricList);
			List<BenchmarkProcess> benchmarkProcess = benchmarkprocessDao.findByBenchmarkDatasetId(benchmarkId);
			List<BenchmarkProcess> bmProcessPublished = new ArrayList<BenchmarkProcess>();
			for(BenchmarkProcess bm : benchmarkProcess) {
				if(bm.getStatus().equalsIgnoreCase("Completed")) {
					ModelExtended model = modelDao.findByModelId(bm.getModelId());
					if(model.getStatus().equalsIgnoreCase("published")) {
						bmProcessPublished.add(bm);
					}
				}
			}
			/* 
			 * for traslation, higher the score better the model
			 */
			if(	bmDto.getTask().getType() == ModelTask.TypeEnum.TRANSLATION ) {
				Collections.sort(bmProcessPublished, Comparator.comparingDouble(BenchmarkProcess ::getScore).reversed());
				
				//bmProcessPublished.stream().sorted(Comparator.comparingDouble(BenchmarkProcess::getScore).reversed()).collect(Collectors.toList());
			}
			/* 
			 * for asr, lower the score better the model
			 */
			if(	bmDto.getTask().getType() == ModelTask.TypeEnum.ASR ) {
				Collections.sort(bmProcessPublished, Comparator.comparingDouble(BenchmarkProcess ::getScore));
				//bmProcessPublished.stream().sorted(Comparator.comparingDouble(BenchmarkProcess::getScore)).collect(Collectors.toList());
			}
			
			/* 
			 * for ocr, lower the score better the model
			 */
			if(	bmDto.getTask().getType() == ModelTask.TypeEnum.OCR ) {
				Collections.sort(bmProcessPublished, Comparator.comparingDouble(BenchmarkProcess ::getScore));
			}
			bmDto.setBenchmarkPerformance(bmProcessPublished);
			
			return bmDto;
		}
		return null;
		
	}
	

	List<String> getMetric(String task) {
		List<String> list = null;
		if (task.equalsIgnoreCase("translation")) {
			String[] metric = { "bleu","meteor","rouge","ribes","gleu","bert" };
			list = new ArrayList<>(Arrays.asList(metric));
			return list;
		}

		if (task.equalsIgnoreCase("asr")) {
			String[] metric = { "wer","cer" };
			list = new ArrayList<>(Arrays.asList(metric));
			return list;
		}
		if (task.equalsIgnoreCase("ocr")) {

			String[] metric = { "wer","cer"};
			list = new ArrayList<>(Arrays.asList(metric));
			return list;
		}
		if (task.equalsIgnoreCase("tts")) {

			String[] metric = { "wer" };
			list = new ArrayList<>(Arrays.asList(metric));
			return list;
		}

		if (task.equalsIgnoreCase("document-layout")) {
			String[] metric = { "precision", "recall", "h1-mean" };
			list = new ArrayList<>(Arrays.asList(metric));
			return list;
		}
		return list;
	}


	public BenchmarkListByUserIdResponse benchmarkListByUserId(String userId, Integer startPage, Integer endPage) {
		log.info("******** Entry BenchmarkService:: benchmarkListByUserId *******");
		
		List<Benchmark> list = new ArrayList<Benchmark>();

		if (startPage != null) {
			int startPg = startPage - 1;
			for (int i = startPg; i < endPage; i++) {
				Pageable paging = PageRequest.of(i, PAGE_SIZE);
				Page<Benchmark> benchmarkList = benchmarkDao.findByUserId(userId, paging);
				list.addAll(benchmarkList.toList());
			}
		} else {
			list = benchmarkDao.findByUserId(userId);
		}
		log.info("******** Exit BenchmarkService:: benchmarkListByUserId *******");
		
		return new BenchmarkListByUserIdResponse("Benchmark list by UserId", list, list.size());
	}

}
