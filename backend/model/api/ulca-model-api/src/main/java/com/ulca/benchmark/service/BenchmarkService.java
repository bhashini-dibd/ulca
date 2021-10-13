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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ulca.benchmark.dao.BenchmarkDao;
import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.exception.BenchmarkNotAllowedException;
import com.ulca.benchmark.exception.BenchmarkNotFoundException;
import com.ulca.benchmark.kafka.model.BenchmarkIngest;
import com.ulca.benchmark.kafka.model.BmDatasetDownload;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.benchmark.model.BenchmarkSubmissionType;
import com.ulca.benchmark.request.BenchmarkMetricRequest;
import com.ulca.benchmark.request.BenchmarkSearchRequest;
import com.ulca.benchmark.request.BenchmarkSubmitRequest;
import com.ulca.benchmark.request.BenchmarkListByModelRequest;
import com.ulca.benchmark.request.ExecuteBenchmarkRequest;
import com.ulca.benchmark.response.BenchmarkDto;
import com.ulca.benchmark.response.BenchmarkListByModelResponse;
import com.ulca.benchmark.response.BenchmarkSearchResponse;
import com.ulca.benchmark.response.BenchmarkSubmitResponse;
import com.ulca.benchmark.response.ExecuteBenchmarkResponse;
import com.ulca.benchmark.response.GetBenchmarkByIdResponse;
import com.ulca.benchmark.util.Utility;
import com.ulca.model.dao.ModelDao;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.exception.ModelNotFoundException;
import com.ulca.model.request.ModelSearchRequest;
import com.ulca.model.response.BmProcessListByProcessIdResponse;
import com.ulca.model.response.ModelListResponseDto;
import com.ulca.model.response.ModelSearchResponse;

import io.swagger.model.ASRConfig.ModelEnum;
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
	BenchmarkProcessDao benchmarkprocessDao;

	public BenchmarkSubmitResponse submitBenchmark(BenchmarkSubmitRequest request) {

		Benchmark benchmark = new Benchmark();
		benchmark.setName(request.getName());
		benchmark.setUserId(request.getUserId());
		benchmark.setDataset(request.getDataset());
		benchmark.setStatus(BenchmarkSubmissionType.SUBMITTED.toString());		
		benchmark.setTask(request.getTask());
		benchmarkDao.save(benchmark);
		
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
				String message = "Benchmark has already been executed for benchmarkId : " + bm.getBenchmarkId() + " and metric : " + bm.getMetric();
				throw new BenchmarkNotAllowedException(message);
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
				List<String> metricList = getMetric(bm.getTask().getType().toString());
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
			//LanguagePairs lprs = new LanguagePairs();
			LanguagePair lp = new LanguagePair();
			lp.setSourceLanguage(SourceLanguageEnum.fromValue(request.getSourceLanguage()));

			if (request.getTargetLanguage() != null && !request.getTargetLanguage().isBlank()) {
				lp.setTargetLanguage(TargetLanguageEnum.fromValue(request.getTargetLanguage()));
			}
			//lprs.add(lp);
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

		//list = benchmarkDao.findAll(example);

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
			List<String> metricList = getMetric(result.getTask().getType().toString());
			bmDto.setMetric(metricList);
			List<BenchmarkProcess> benchmarkProcess = benchmarkprocessDao.findByBenchmarkDatasetId(benchmarkId);
			List<BenchmarkProcess> bmProcessPublished = new ArrayList<BenchmarkProcess>();
			for(BenchmarkProcess bm : benchmarkProcess) {
				ModelExtended model = modelDao.findByModelId(bm.getModelId());
				if(model.getStatus().equalsIgnoreCase("published")) {
					bmProcessPublished.add(bm);
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
			String[] metric = { "bleu","meteor","rouge","ribes","gleu" };
			list = new ArrayList<>(Arrays.asList(metric));
			return list;
		}

		if (task.equalsIgnoreCase("asr")) {
			String[] metric = { "wer","cer" };
			list = new ArrayList<>(Arrays.asList(metric));
			return list;
		}
		if (task.equalsIgnoreCase("ocr")) {

			String[] metric = { "wer"};
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

}
