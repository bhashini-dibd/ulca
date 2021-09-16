package com.ulca.model.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulca.benchmark.dao.BenchmarkDao;
import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.model.dao.ModelDao;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.request.ModelComputeRequest;
import com.ulca.model.request.ModelLeaderboardRequest;
import com.ulca.model.request.ModelSearchRequest;
import com.ulca.model.response.ModelComputeResponse;
import com.ulca.model.response.ModelLeaderboardFiltersMetricResponse;
import com.ulca.model.response.ModelLeaderboardFiltersResponse;
import com.ulca.model.response.ModelLeaderboardResponse;
import com.ulca.model.response.ModelLeaderboardResponseDto;
import com.ulca.model.response.ModelListByUserIdResponse;
import com.ulca.model.response.ModelListResponseDto;
import com.ulca.model.response.ModelSearchResponse;
import com.ulca.model.response.UploadModelResponse;

import io.swagger.model.InferenceAPIEndPoint;
import io.swagger.model.LanguagePair;
import io.swagger.model.LanguagePair.SourceLanguageEnum;
import io.swagger.model.LanguagePair.TargetLanguageEnum;
import io.swagger.model.LanguagePairs;
import io.swagger.model.ModelTask;
import io.swagger.model.ModelTask.TypeEnum;
import io.swagger.model.OneOfInferenceAPIEndPointSchema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ModelService {

	private int PAGE_SIZE = 10;

	@Autowired
	ModelDao modelDao;

	@Autowired
	BenchmarkProcessDao benchmarkProcessDao;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Value("${ulca.model.upload.folder}")
	private String modelUploadFolder;

	@Autowired
	ModelInferenceEndPointService modelInferenceEndPointService;

	public ModelExtended modelSubmit(ModelExtended model) {

		modelDao.save(model);
		return model;
	}

	public ModelListByUserIdResponse modelListByUserId(String userId, Integer startPage, Integer endPage) {
		log.info("******** Entry ModelService:: modelListByUserId *******");
		List<ModelExtended> list = new ArrayList<ModelExtended>();

		if (startPage != null) {
			int startPg = startPage - 1;
			for (int i = startPg; i < endPage; i++) {
				Pageable paging = PageRequest.of(i, PAGE_SIZE);
				Page<ModelExtended> modelList = modelDao.findByUserId(userId, paging);
				list.addAll(modelList.toList());
			}
		} else {
			list = modelDao.findByUserId(userId);
		}

		List<ModelListResponseDto> modelDtoList = new ArrayList<ModelListResponseDto>();
		for (ModelExtended model : list) {
			ModelListResponseDto modelDto = new ModelListResponseDto();
			BeanUtils.copyProperties(model, modelDto);
			List<BenchmarkProcess> benchmarkProcess = benchmarkProcessDao.findByModelId(model.getModelId());
			modelDto.setBenchmarkPerformance(benchmarkProcess);
			modelDtoList.add(modelDto);

		}

		return new ModelListByUserIdResponse("Model list by UserId", modelDtoList, modelDtoList.size());
	}

	public ModelExtended getMode(String modelId) {
		Optional<ModelExtended> result = modelDao.findById(modelId);

		if (!result.isEmpty()) {
			return result.get();
		}
		return null;
	}

	public String storeModelFile(MultipartFile file) throws Exception {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String uploadFolder = modelUploadFolder + "/model";
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new Exception("Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = Paths.get(uploadFolder).toAbsolutePath().normalize();

			try {
				Files.createDirectories(targetLocation);
			} catch (Exception ex) {
				throw new Exception("Could not create the directory where the uploaded files will be stored.", ex);
			}
			Path filePath = targetLocation.resolve(fileName);

			log.info("filePath :: " + filePath.toAbsolutePath());

			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			return filePath.toAbsolutePath().toString();
		} catch (IOException ex) {
			throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	@Transactional
	public UploadModelResponse uploadModel(MultipartFile file, String userId) throws Exception {

		String modelFilePath = storeModelFile(file);
		ModelExtended modelObj = getModel(modelFilePath);
		modelObj.setUserId(userId);
		modelObj.setSubmittedOn(new Date().toString());
		modelObj.setPublishedOn(new Date().toString());
		modelObj.setStatus("published");
		if (modelObj != null) {
			try {
				modelDao.save(modelObj);
			} catch (DuplicateKeyException ex) {
				throw new DuplicateKeyException("Model with same name exist in system");
			}
		}
		InferenceAPIEndPoint inferenceAPIEndPoint = modelObj.getInferenceEndPoint();
		String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();
		OneOfInferenceAPIEndPointSchema schema = inferenceAPIEndPoint.getSchema();
		schema = modelInferenceEndPointService.validateCallBackUrl(callBackUrl, schema);
		inferenceAPIEndPoint.setSchema(schema);
		modelObj.setInferenceEndPoint(inferenceAPIEndPoint);
		modelDao.save(modelObj);

		return new UploadModelResponse("Model Saved Successfully", modelObj);
	}

	public ModelExtended getModel(String modelFilePath) {

		ModelExtended modelObj = null;
		ObjectMapper objectMapper = new ObjectMapper();
		File file = new File(modelFilePath);
		try {
			modelObj = objectMapper.readValue(file, ModelExtended.class);
			return modelObj;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return modelObj;
	}

	public ModelSearchResponse searchModel(ModelSearchRequest request) {

		ModelExtended model = new ModelExtended();

		if (request.getTask() != null && !request.getTask().isBlank()) {
			ModelTask modelTask = new ModelTask();
			modelTask.setType(TypeEnum.fromValue(request.getTask()));
			model.setTask(modelTask);
		}

		if (request.getSourceLanguage() != null && !request.getSourceLanguage().isBlank()) {
			LanguagePairs lprs = new LanguagePairs();
			LanguagePair lp = new LanguagePair();
			lp.setSourceLanguage(SourceLanguageEnum.fromValue(request.getSourceLanguage()));

			if (request.getTargetLanguage() != null && !request.getTargetLanguage().isBlank()) {
				lp.setTargetLanguage(TargetLanguageEnum.fromValue(request.getTargetLanguage()));
			}
			lprs.add(lp);
			model.setLanguages(lprs);
		}
		/*
		 * seach only published model
		 */
		model.setStatus("published");

		Example<ModelExtended> example = Example.of(model);
		List<ModelExtended> list = modelDao.findAll(example);

		return new ModelSearchResponse("Model Search Result", list, list.size());

	}

	public ModelComputeResponse computeModel(ModelComputeRequest compute)
			throws MalformedURLException, URISyntaxException, JsonMappingException, JsonProcessingException {

		String modelId = compute.getModelId();
		ModelExtended modelObj = modelDao.findById(modelId).get();
		InferenceAPIEndPoint inferenceAPIEndPoint = modelObj.getInferenceEndPoint();
		String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();
		OneOfInferenceAPIEndPointSchema schema = inferenceAPIEndPoint.getSchema();

		return modelInferenceEndPointService.compute(callBackUrl, schema, compute);
	}

	public ModelLeaderboardResponse searchLeaderboard(ModelLeaderboardRequest request) {

		ModelLeaderboardResponse response = new ModelLeaderboardResponse();
		List<ModelLeaderboardResponseDto> dtoList = new ArrayList<ModelLeaderboardResponseDto>();
		ModelLeaderboardResponseDto dto = new ModelLeaderboardResponseDto();
		LookupOperation lookup = LookupOperation.newLookup().from("benchmarkprocess").localField("modelId")
				.foreignField("modelId").as("join_benchmarkprocess");
		Aggregation aggregation = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("modelId").is(dto.getModelId())), lookup,
				Aggregation.match(Criteria.where("join_benchmarkprocess.modelId").is(dto.getModelId())),
				Aggregation.skip(10), Aggregation.limit(10));

		dtoList = mongoTemplate.aggregate(aggregation, BenchmarkProcess.class, ModelLeaderboardResponseDto.class)
				.getMappedResults();

		// join the benchmarkprocess and model collection and fetch the result
		// iterate result and create object of ModelLeaderboardResponseDto with
		// respective values
		// add the dto objectto dtoList

		for (ModelLeaderboardResponseDto mdto : dtoList) {

			// mdto.setLanguages(mdto.getSourceLanguage());
			mdto.setMetric(mdto.getMetric());
			mdto.setModelName(mdto.getModelName());
			mdto.setBenchmarkDatase(mdto.getBenchmarkDatase());

		}

		response.setData(dtoList);
		response.setCount(dtoList.size());
		response.setMessage("Model Leader Board results");

		return (ModelLeaderboardResponse) response;
	}

	public ModelLeaderboardFiltersResponse leaderBoardFilters() {

		log.info("******** Entry ModelService:: leaderBoardFilters *******");

		ModelLeaderboardFiltersResponse response = new ModelLeaderboardFiltersResponse();

		ModelTask.TypeEnum[] list = ModelTask.TypeEnum.values();
		for (ModelTask.TypeEnum type : list) {
			log.info(type.toString());
		}

		ModelLeaderboardFiltersMetricResponse metric = new ModelLeaderboardFiltersMetricResponse();
		String[] translation = { "bleu", "sacrebleu", "meteor", "lepor" };
		metric.setTranslation(Arrays.asList(translation));
		String[] asr = { "wer", "cer" };
		metric.setAsr(Arrays.asList(asr));

		String[] documentLayoutBenchmarkMetric = { "precision", "recall", "h1-mean" };
		metric.setDocumentLayout(Arrays.asList(documentLayoutBenchmarkMetric));

		String[] ocr = { "wer", "cer" };
		metric.setOcr(Arrays.asList(ocr));

		String[] tts = { "wer", "cer" };
		metric.setTts(Arrays.asList(tts));

		response.setMetric(metric);
		response.setSourceLanguage(LanguagePair.SourceLanguageEnum.values());
		response.setTargetLanguage(LanguagePair.TargetLanguageEnum.values());

		response.setTask(list);

		return response;

	}

}
