package com.ulca.benchmark.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ulca.benchmark.constant.BenchmarkConstants;
import com.ulca.benchmark.dao.BenchmarkDao;
import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.exception.BenchmarkNotAllowedException;
import com.ulca.benchmark.exception.BenchmarkNotFoundException;
import com.ulca.benchmark.kafka.model.BenchmarkIngest;
import com.ulca.benchmark.kafka.model.BmDatasetDownload;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.benchmark.model.BenchmarkSubmissionType;
import com.ulca.benchmark.request.BenchmarkListByModelRequest;
import com.ulca.benchmark.request.BenchmarkMetricRequest;
import com.ulca.benchmark.request.BenchmarkSearchRequest;
import com.ulca.benchmark.request.BenchmarkSubmitRequest;
import com.ulca.benchmark.request.ExecuteBenchmarkAllMetricRequest;
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
import io.swagger.model.SupportedLanguages;
import io.swagger.model.SupportedTasks;
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
	BenchmarkSubmtStatusService bmSubmtStatusService;

	@Autowired
	BenchmarkProcessDao benchmarkprocessDao;

	@Autowired
	ModelConstants modelConstants;

	public BenchmarkSubmitResponse submitBenchmark(BenchmarkSubmitRequest request)
			throws RequestParamValidationException {

		Benchmark benchmark = new Benchmark();
		benchmark.setName(request.getDatasetName());
		benchmark.setUserId(request.getUserId());
		benchmark.setDataset(request.getUrl());
		benchmark.setStatus(BenchmarkSubmissionType.SUBMITTED.toString());
		benchmark.setSubmittedOn(Instant.now().toEpochMilli());
		benchmark.setCreatedOn(Instant.now().toEpochMilli());

		Benchmark existingBenchmark = benchmarkDao.findByName(request.getDatasetName());
		if (existingBenchmark == null) {
			try {
				benchmarkDao.save(benchmark);
			} catch (DuplicateKeyException ex) {
				log.info("benchmark with same name exists.: " + benchmark.getName());
				throw new DuplicateKeyException(BenchmarkConstants.datasetNameUniqueErrorMsg);
			}
		} else {
			log.info(BenchmarkConstants.datasetNameUniqueErrorMsg + "benchmark name :: " + request.getDatasetName());
			throw new DuplicateKeyException(BenchmarkConstants.datasetNameUniqueErrorMsg);
		}

		String serviceRequestNumber = Utility.getBenchmarkDatasetSubmitReferenceNumber();
		bmSubmtStatusService.createStatus(serviceRequestNumber, request.getUserId(), benchmark.getBenchmarkId());

		BenchmarkIngest benchmarkIngest = new BenchmarkIngest();
		benchmarkIngest.setBenchmarkId(benchmark.getBenchmarkId());
		benchmarkIngest.setServiceRequestNumber(serviceRequestNumber);
		benchmarkIngestKafkaTemplate.send(benchmarkIngestTopic, benchmarkIngest);

		String message = "Benchmark Dataset has been Submitted";
		return new BenchmarkSubmitResponse(message, serviceRequestNumber, benchmark.getBenchmarkId(),
				benchmark.getCreatedOn());
	}

	@Transactional
	public ExecuteBenchmarkResponse executeBenchmark(ExecuteBenchmarkRequest request) {

		log.info("******** Entry BenchmarkService:: executeBenchmark *******");

		String modelId = request.getModelId();
		Optional<ModelExtended> model = modelDao.findById(modelId);
		if (model.isEmpty()) {
			throw new ModelNotFoundException("Model with not modelId : " + modelId + " not available ");
		}

		ModelExtended modelExtended = model.get();

		List<String> benchmarkProcessIds = new ArrayList<String>();

		for (BenchmarkMetricRequest bm : request.getBenchmarks()) {
			Benchmark benchmark = benchmarkDao.findByBenchmarkId(bm.getBenchmarkId());
			if (benchmark == null) {
				throw new BenchmarkNotFoundException("Benchmark : " + bm.getBenchmarkId() + " not found ");
			}

			List<BenchmarkProcess> isExistBmProcess = benchmarkprocessDao
					.findByModelIdAndBenchmarkDatasetIdAndMetric(modelId, bm.getBenchmarkId(), bm.getMetric());
			if (isExistBmProcess != null && isExistBmProcess.size() > 0) {

				for (BenchmarkProcess existingBm : isExistBmProcess) {
					String status = existingBm.getStatus();
					if (status.equalsIgnoreCase("Completed") || status.equalsIgnoreCase("In-Progress")) {
						String message = "Benchmark has already been executed for benchmarkId : " + bm.getBenchmarkId()
								+ " and metric : " + bm.getMetric();
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
			bmProcess.setCreatedOn(Instant.now().toEpochMilli());
			bmProcess.setLastModifiedOn(Instant.now().toEpochMilli());
			bmProcess.setStartTime(Instant.now().toEpochMilli());
			benchmarkprocessDao.save(bmProcess);

			Map<String,String> map = new HashMap<String, String>();
			map.put(serviceRequestNumber, bm.getMetric());
			
			BmDatasetDownload bmDsDownload = new BmDatasetDownload();
			bmDsDownload.setBenchmarkDatasetId(bm.getBenchmarkId());
			bmDsDownload.setModelId(modelId);	
			bmDsDownload.setBenchmarkProcessIdsMap(map);
			
			benchmarkDownloadKafkaTemplate.send(benchmarkDownloadTopic, bmDsDownload);
			benchmarkProcessIds.add(serviceRequestNumber);

		}

		ExecuteBenchmarkResponse response = new ExecuteBenchmarkResponse();
		response.setBenchmarkProcessIds(benchmarkProcessIds);

		log.info("******** Exit BenchmarkService:: executeBenchmark *******");

		return response;

	}

	@Transactional
	public ExecuteBenchmarkResponse executeBenchmarkAllMetric(ExecuteBenchmarkAllMetricRequest request) {

		log.info("******** Entry BenchmarkService:: executeBenchmark *******");

		String modelId = request.getModelId();
		Optional<ModelExtended> model = modelDao.findById(modelId);
		if (model.isEmpty()) {
			throw new ModelNotFoundException("Model with not modelId : " + modelId + " not available ");
		}

		ModelExtended modelExtended = model.get();

		Benchmark benchmark = benchmarkDao.findByBenchmarkId(request.getBenchmarkId());

		if (benchmark == null) {
			throw new BenchmarkNotFoundException("Benchmark : " + request.getBenchmarkId() + " not found ");
		}

		List<String> benchmarkProcessIds = new ArrayList<String>();

		List<String> metricList = modelConstants.getMetricListByModelTask(benchmark.getTask().getType().toString());

		String benchmarkId = benchmark.getBenchmarkId();
		
		Map<String, String> map = new HashMap<String, String>();
		

		for (String metric : metricList) {

			List<BenchmarkProcess> isExistBmProcess = benchmarkprocessDao
					.findByModelIdAndBenchmarkDatasetIdAndMetric(modelId, benchmarkId, metric);

			Boolean isExisting = false;

			if (isExistBmProcess != null && isExistBmProcess.size() > 0) {

				for (BenchmarkProcess existingBm : isExistBmProcess) {
					String status = existingBm.getStatus();
					if (status.equalsIgnoreCase("Completed") || status.equalsIgnoreCase("In-Progress")) {
						String message = "Benchmark has already been executed for benchmarkId : " + benchmarkId
								+ " and metric : " + metric;
						log.info(message);
						isExisting = true;
						break;

					}
				}
			}
			if (!isExisting) {
				String serviceRequestNumber = Utility.getBenchmarkExecuteReferenceNumber();

				BenchmarkProcess bmProcess = new BenchmarkProcess();
				bmProcess.setBenchmarkDatasetId(benchmarkId);
				bmProcess.setBenchmarkProcessId(serviceRequestNumber);
				bmProcess.setMetric(metric);
				bmProcess.setBenchmarkDatasetName(benchmark.getName());
				bmProcess.setModelId(modelId);
				bmProcess.setModelName(modelExtended.getName());
				bmProcess.setStatus("In-Progress");
				bmProcess.setCreatedOn(Instant.now().toEpochMilli());
				bmProcess.setLastModifiedOn(Instant.now().toEpochMilli());
				bmProcess.setStartTime(Instant.now().toEpochMilli());
				benchmarkprocessDao.save(bmProcess);
				map.put(serviceRequestNumber, metric);
				benchmarkProcessIds.add(serviceRequestNumber);
			}

		}

		if(benchmarkProcessIds.size()>0){
			
			BmDatasetDownload bmDsDownload = new BmDatasetDownload();
			bmDsDownload.setBenchmarkDatasetId(benchmarkId);
			bmDsDownload.setModelId(modelId);
			bmDsDownload.setBenchmarkProcessIdsMap(map);
			benchmarkDownloadKafkaTemplate.send(benchmarkDownloadTopic, bmDsDownload);
		}

		ExecuteBenchmarkResponse response = new ExecuteBenchmarkResponse();
		response.setBenchmarkProcessIds(benchmarkProcessIds);

		log.info("******** Exit BenchmarkService:: executeBenchmark *******");

		return response;

	}

	public BenchmarkListByModelResponse listByTaskID(BenchmarkListByModelRequest request) {

		log.info("******** Entry BenchmarkService:: listByTaskID *******");

		BenchmarkListByModelResponse response = null;

		List<BenchmarkDto> dtoList = new ArrayList<BenchmarkDto>();

		ModelExtended model = modelDao.findByModelId(request.getModelId());
		LanguagePairs lps = model.getLanguages();

		for (LanguagePair lp : lps) {

			List<Benchmark> list = benchmarkDao.findByTaskAndLanguages(model.getTask(), lp);
			for (Benchmark bm : list) {

				BenchmarkDto dto = new BenchmarkDto();
				BeanUtils.copyProperties(bm, dto);
				List<String> metricList = modelConstants.getMetricListByModelTask(bm.getTask().getType().toString());
				dto.setMetric(new ArrayList<>(metricList));
				List<BenchmarkProcess> bmProcList = benchmarkprocessDao
						.findByModelIdAndBenchmarkDatasetId(request.getModelId(), bm.getBenchmarkId());
				List<String> allMetricList = modelConstants.getMetricListByModelTask(bm.getTask().getType().toString());
				for (BenchmarkProcess bmProc : bmProcList) {
					if (allMetricList.contains(bmProc.getMetric())) {
						String status = bmProc.getStatus();
						if (status != null && !status.isBlank()
								&& (status.equalsIgnoreCase("Completed") || status.equalsIgnoreCase("In-Progress"))) {
							metricList.remove(bmProc.getMetric());
						}
					}

				}
				dto.setAvailableMetric(metricList);
				dtoList.add(dto);

			}
		}
		response = new BenchmarkListByModelResponse("Benchmark Search Result", dtoList, dtoList.size());
		log.info("******** Exit BenchmarkService:: listByTaskID *******");
		return response;
	}

	public BenchmarkSearchResponse searchBenchmark(BenchmarkSearchRequest request, Integer startPage, Integer endPage) {

		log.info("***********  BenchmarkService :: searchBenchmark *********");

		ModelTask modelTask = null;
		LanguagePair lp = null;

		List<Benchmark> list = null;

		if (request.getTask() != null && !request.getTask().isBlank()) {
			modelTask = new ModelTask();
			modelTask.setType(SupportedTasks.fromValue(request.getTask()));

		}

		if (request.getSourceLanguage() != null && !request.getSourceLanguage().isBlank()) {
			lp = new LanguagePair();
			lp.setSourceLanguage(SupportedLanguages.fromValue(request.getSourceLanguage()));

			if (request.getTargetLanguage() != null && !request.getTargetLanguage().isBlank()) {
				lp.setTargetLanguage(SupportedLanguages.fromValue(request.getTargetLanguage()));
			}

		}

		if (modelTask != null && lp != null) {
			if (startPage != null) {
				int startPg = startPage - 1;
				for (int i = startPg; i < endPage; i++) {
					Pageable paging = PageRequest.of(i, PAGE_SIZE);
					list = (List<Benchmark>) benchmarkDao.findByTaskAndLanguages(modelTask, lp, paging);
				}
			} else {
				list = benchmarkDao.findByTaskAndLanguages(modelTask, lp);
			}
			Collections.shuffle(list);
			return new BenchmarkSearchResponse("Benchmark Search Result", list, list.size());

		} else if (modelTask != null && lp == null) {
			if (startPage != null) {
				int startPg = startPage - 1;
				for (int i = startPg; i < endPage; i++) {
					Pageable paging = PageRequest.of(i, PAGE_SIZE);
					list = (List<Benchmark>) benchmarkDao.findByTask(modelTask, paging);
				}
			} else {
				list = benchmarkDao.findByTask(modelTask);
			}
			Collections.shuffle(list);
			return new BenchmarkSearchResponse("Benchmark Search Result", list, list.size());

		} else if (modelTask == null && lp != null) {
			if (startPage != null) {
				int startPg = startPage - 1;
				for (int i = startPg; i < endPage; i++) {
					Pageable paging = PageRequest.of(i, PAGE_SIZE);
					list = (List<Benchmark>) benchmarkDao.findByLanguages(lp, paging);
				}
			} else {
				list = benchmarkDao.findByLanguages(lp);
			}
			Collections.shuffle(list);
			return new BenchmarkSearchResponse("Benchmark Search Result", list, list.size());
		} else if (modelTask == null && lp == null) {
			if (startPage != null) {
				int startPg = startPage - 1;
				for (int i = startPg; i < endPage; i++) {
					Pageable paging = PageRequest.of(i, PAGE_SIZE);
					list = (List<Benchmark>) benchmarkDao.findAll(paging);
				}
			} else {
				list = benchmarkDao.findAll();
			}
			Collections.shuffle(list);
			return new BenchmarkSearchResponse("Benchmark Search Result", list, list.size());
		} else {
			log.info("search parameters not valid");
		}
		return new BenchmarkSearchResponse("search parameters not valid", list, 0);
	}

	public BmProcessListByProcessIdResponse processStatus(String benchmarkProcessId) {

		BenchmarkProcess benchmarkProcess = benchmarkprocessDao.findByBenchmarkProcessId(benchmarkProcessId);

		List<BenchmarkProcess> list = new ArrayList<BenchmarkProcess>();
		list.add(benchmarkProcess);

		BmProcessListByProcessIdResponse response = new BmProcessListByProcessIdResponse("Benchmark Process list", list,
				list.size());

		return response;

	}

	public GetBenchmarkByIdResponse getBenchmarkById(String benchmarkId) {

		Benchmark result = benchmarkDao.findByBenchmarkId(benchmarkId);

		if (result != null) {
			GetBenchmarkByIdResponse bmDto = new GetBenchmarkByIdResponse();
			BeanUtils.copyProperties(result, bmDto);
			List<String> metricList = modelConstants.getMetricListByModelTask(result.getTask().getType().toString());
			bmDto.setMetric(metricList);
			List<BenchmarkProcess> benchmarkProcess = benchmarkprocessDao.findByBenchmarkDatasetId(benchmarkId);
			List<BenchmarkProcess> bmProcessPublished = new ArrayList<BenchmarkProcess>();
			List<String> allMetricList = modelConstants.getMetricListByModelTask(result.getTask().getType().toString());
			for (BenchmarkProcess bm : benchmarkProcess) {
				if (bm.getStatus().equalsIgnoreCase("Completed") && allMetricList.contains(bm.getMetric())) {
					ModelExtended model = modelDao.findByModelId(bm.getModelId());
					if (model.getStatus().equalsIgnoreCase("published")) {
						bm.setModelName(model.getName());
						bm.setModelVersion(model.getVersion());
						bmProcessPublished.add(bm);
					}
				}
			}
			/*
			 * for traslation, higher the score better the model
			 */
			if (bmDto.getTask().getType() == SupportedTasks.TRANSLATION) {
				Collections.sort(bmProcessPublished, Comparator.comparingDouble(BenchmarkProcess::getScore).reversed());

				// bmProcessPublished.stream().sorted(Comparator.comparingDouble(BenchmarkProcess::getScore).reversed()).collect(Collectors.toList());
			}
			/*
			 * for asr, lower the score better the model
			 */
			if (bmDto.getTask().getType() == SupportedTasks.ASR) {
				Collections.sort(bmProcessPublished, Comparator.comparingDouble(BenchmarkProcess::getScore));
				// bmProcessPublished.stream().sorted(Comparator.comparingDouble(BenchmarkProcess::getScore)).collect(Collectors.toList());
			}

			/*
			 * for ocr, lower the score better the model
			 */
			if (bmDto.getTask().getType() == SupportedTasks.OCR) {
				Collections.sort(bmProcessPublished, Comparator.comparingDouble(BenchmarkProcess::getScore));
			}
			bmDto.setBenchmarkPerformance(bmProcessPublished);

			return bmDto;
		}
		return null;

	}

	public BenchmarkListByUserIdResponse benchmarkListByUserId(String userId, Integer startPage, Integer endPage,Integer pgSize,String name) {
		log.info("******** Entry BenchmarkService:: benchmarkListByUserId *******");

		Integer count = benchmarkDao.countByUserId(userId);

		List<Benchmark> list = new ArrayList<Benchmark>();

		if (startPage != null) {
			int startPg = startPage - 1;
			for (int i = startPg; i < endPage; i++) {
				Pageable paging = null;
				if (pgSize!=null) {
					paging =	PageRequest.of(i, pgSize, Sort.by("submittedOn").descending());
				} else {
					paging = PageRequest.of(i,PAGE_SIZE, Sort.by("submittedOn").descending());

				}				
				Page<Benchmark> benchmarkList = null;
				if (name!=null) {
					Benchmark benchmark = new Benchmark();
					benchmark.setUserId(userId);
					benchmark.setName(name);
					Example<Benchmark> example = Example.of(benchmark);

					benchmarkList = benchmarkDao.findAll(example, paging);
					count = modelDao.countByUserIdAndName(userId,name);

				} else {

				benchmarkList =	benchmarkDao.findByUserId(userId, paging);
				}
				list.addAll(benchmarkList.toList());
			}
		} else {
			if (name!=null) {
				Benchmark benchmark = new Benchmark();
				benchmark.setUserId(userId);
				benchmark.setName(name);
				Example<Benchmark> example = Example.of(benchmark);
				list = benchmarkDao.findAll(example);
				count = list.size();
			} else {
				list = benchmarkDao.findByUserId(userId);
			}
		}
		list.sort(Comparator.comparing(Benchmark::getSubmittedOn).reversed());

		log.info("******** Exit BenchmarkService:: benchmarkListByUserId *******");

		return new BenchmarkListByUserIdResponse("Benchmark list by UserId", list, list.size(),count);
	}

}
