package com.ulca.model.service;

import java.util.Iterator;	
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulca.benchmark.dao.BenchmarkDao;
import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.benchmark.util.ModelConstants;
import com.ulca.model.dao.InferenceAPIEndPointDto;
import com.ulca.model.dao.ModelDao;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.dao.ModelExtendedDto;
import com.ulca.model.dao.ModelFeedback;
import com.ulca.model.dao.ModelFeedbackDao;
import com.ulca.model.dao.ModelHealthStatus;
import com.ulca.model.dao.ModelHealthStatusDao;
import com.ulca.model.dao.PipelineModel;
import com.ulca.model.dao.PipelineModelDao;
import com.ulca.model.exception.FileExtensionNotSupportedException;
import com.ulca.model.exception.ModelNotFoundException;
import com.ulca.model.exception.ModelStatusChangeException;
import com.ulca.model.exception.ModelValidationException;
import com.ulca.model.exception.PipelineValidationException;
import com.ulca.model.exception.RequestParamValidationException;
import com.ulca.model.request.ModelComputeRequest;
import com.ulca.model.request.ModelFeedbackSubmitRequest;
import com.ulca.model.request.ModelSearchRequest;
import com.ulca.model.request.ModelStatusChangeRequest;
import com.ulca.model.response.GetModelFeedbackListResponse;
import com.ulca.model.response.GetTransliterationModelIdResponse;
import com.ulca.model.response.ModelComputeResponse;
import com.ulca.model.response.ModelFeedbackSubmitResponse;
import com.ulca.model.response.ModelHealthStatusResponse;
import com.ulca.model.response.ModelListByUserIdResponse;
import com.ulca.model.response.ModelListResponseDto;
import com.ulca.model.response.ModelPipelineResponse;
import com.ulca.model.response.ModelSearchResponse;
import com.ulca.model.response.ModelStatusChangeResponse;
import com.ulca.model.response.UploadModelResponse;

import io.swagger.model.ASRInference;
import io.swagger.model.AsyncApiDetails;
import io.swagger.model.ImageFormat;
import io.swagger.model.InferenceAPIEndPoint;
import io.swagger.model.InferenceAPIEndPointInferenceApiKey;
import io.swagger.model.LanguagePair;
import io.swagger.model.LanguagePairs;
import io.swagger.model.License;
import io.swagger.model.ModelProcessingType;
import io.swagger.model.ModelTask;
import io.swagger.model.NerInference;
import io.swagger.model.OCRInference;
import io.swagger.model.OneOfInferenceAPIEndPointSchema;

import io.swagger.model.SupportedTasks;
import io.swagger.model.Submitter;
import io.swagger.model.SupportedLanguages;
import io.swagger.model.TTSInference;
import io.swagger.model.TranslationInference;
import io.swagger.model.TranslationResponse;
import io.swagger.model.TransliterationInference;
import io.swagger.model.TransliterationRequest;
import io.swagger.model.TxtLangDetectionInference;
import io.swagger.model.TxtLangDetectionRequest;
import io.swagger.pipelinemodel.InferenceAPIEndPointMasterApiKey;
import io.swagger.pipelinemodel.ListOfPipelines;
import io.swagger.pipelinemodel.PipelineTaskSequence;
import io.swagger.pipelinerequest.ASRRequestConfig;
import io.swagger.pipelinerequest.ASRResponseConfig;
import io.swagger.pipelinerequest.ASRTask;
import io.swagger.pipelinerequest.ASRTaskInference;
import io.swagger.pipelinerequest.PipelineConfig;
import io.swagger.pipelinerequest.PipelineInferenceAPIEndPoint;
import io.swagger.pipelinerequest.PipelineRequest;
import io.swagger.pipelinerequest.PipelineTask;
import io.swagger.pipelinerequest.PipelineTasks;
import io.swagger.pipelinerequest.TTSRequestConfig;
import io.swagger.pipelinerequest.TTSResponseConfig;
import io.swagger.pipelinerequest.TTSTask;
import io.swagger.pipelinerequest.TTSTaskInference;
import io.swagger.pipelinerequest.TaskSchema;
import io.swagger.pipelinerequest.TaskSchemaList;
import io.swagger.pipelinerequest.TranslationRequestConfig;
import io.swagger.pipelinerequest.TranslationResponseConfig;
import io.swagger.pipelinerequest.TranslationTask;
import io.swagger.pipelinerequest.TranslationTaskInference;
import io.swagger.pipelinerequest.PipelineResponse;
import io.swagger.pipelinerequest.LanguagesList;

import lombok.extern.slf4j.Slf4j;
import com.github.mervick.aes_everywhere.Aes256;
import com.google.gson.Gson;
import com.mongodb.client.model.geojson.LineString;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Slf4j
@Service
public class ModelService {

	private int PAGE_SIZE = 10;

	@Value(value = "${aes.model.apikey.secretkey}")
	private String SECRET_KEY;

	@Autowired
	ModelDao modelDao;

	@Autowired
	PipelineModelDao pipelineModelDao;

	@Autowired
	BenchmarkProcessDao benchmarkProcessDao;

	@Autowired
	BenchmarkDao benchmarkDao;

	@Autowired
	ModelFeedbackDao modelFeedbackDao;

	@Autowired
	ModelHealthStatusDao modelHealthStatusDao;

	@Value("${ulca.model.upload.folder}")
	private String modelUploadFolder;

	@Autowired
	ModelInferenceEndPointService modelInferenceEndPointService;

	@Autowired
	WebClient.Builder builder;

	@Autowired
	ModelConstants modelConstants;

	@Autowired
	MongoTemplate mongoTemplate;

	public ModelExtended modelSubmit(ModelExtended model) {

		modelDao.save(model);
		return model;
	}

	public ModelListByUserIdResponse modelListByUserId(String userId, Integer startPage, Integer endPage,
			Integer pgSize, String name) {
		log.info("******** Entry ModelService:: modelListByUserId *******");
		Integer count = modelDao.countByUserId(userId);
		List<ModelExtended> list = new ArrayList<ModelExtended>();

		if (startPage != null) {
			int startPg = startPage - 1;
			for (int i = startPg; i < endPage; i++) {
				Pageable paging = null;
				if (pgSize != null) {
					paging = PageRequest.of(i, pgSize, Sort.by("submittedOn").descending());
				} else {
					paging = PageRequest.of(i, PAGE_SIZE, Sort.by("submittedOn").descending());

				}

				Page<ModelExtended> modelList = null;
				if (name != null) {
					ModelExtended modelExtended = new ModelExtended();
					modelExtended.setUserId(userId);
					modelExtended.setName(name);
					Example<ModelExtended> example = Example.of(modelExtended);

					modelList = modelDao.findAll(example, paging);
					count = modelDao.countByUserIdAndName(userId, name);
				} else {
					modelList = modelDao.findByUserId(userId, paging);
				}
				list.addAll(modelList.toList());
			}
		} else {
			if (name != null) {
				ModelExtended modelExtended = new ModelExtended();
				modelExtended.setUserId(userId);
				modelExtended.setName(name);
				Example<ModelExtended> example = Example.of(modelExtended);

				list = modelDao.findAll(example);
				count = list.size();

			} else {
				list = modelDao.findByUserId(userId);

			}
		}

		List<ModelListResponseDto> modelDtoList = new ArrayList<ModelListResponseDto>();
		for (ModelExtended model : list) {
			// changes
			ModelExtendedDto modelExtendedDto = new ModelExtendedDto();
			BeanUtils.copyProperties(model, modelExtendedDto);
			InferenceAPIEndPoint inferenceAPIEndPoint = model.getInferenceEndPoint();
			InferenceAPIEndPointDto inferenceAPIEndPointDto = new InferenceAPIEndPointDto();

			BeanUtils.copyProperties(inferenceAPIEndPoint, inferenceAPIEndPointDto);

			modelExtendedDto.setInferenceEndPoint(inferenceAPIEndPointDto);

			ModelListResponseDto modelDto = new ModelListResponseDto();
			// BeanUtils.copyProperties(model, modelDto);
			BeanUtils.copyProperties(modelExtendedDto, modelDto);
			List<BenchmarkProcess> benchmarkProcess = benchmarkProcessDao.findByModelId(model.getModelId());
			modelDto.setBenchmarkPerformance(benchmarkProcess);
			modelDtoList.add(modelDto);
		}
		modelDtoList.sort(Comparator.comparing(ModelListResponseDto::getSubmittedOn).reversed());
		return new ModelListByUserIdResponse("Model list by UserId", modelDtoList, modelDtoList.size(), count);
	}

	public ModelListResponseDto getModelByModelId(String modelId) {
		log.info("******** Entry ModelService:: getModelDescription *******");
		Optional<ModelExtended> result = modelDao.findById(modelId);

		if (!result.isEmpty()) {

			ModelExtended model = result.get();

			// new changes
			ModelExtendedDto modelExtendedDto = new ModelExtendedDto();

			BeanUtils.copyProperties(model, modelExtendedDto);

			InferenceAPIEndPoint inferenceAPIEndPoint = model.getInferenceEndPoint();
			InferenceAPIEndPointDto dto = new InferenceAPIEndPointDto();
			BeanUtils.copyProperties(inferenceAPIEndPoint, dto);
			modelExtendedDto.setInferenceEndPoint(dto);
			ModelListResponseDto modelDto = new ModelListResponseDto();
			BeanUtils.copyProperties(modelExtendedDto, modelDto);
			List<String> metricList = modelConstants.getMetricListByModelTask(model.getTask().getType().toString());
			modelDto.setMetric(metricList);

			List<BenchmarkProcess> benchmarkProcess = benchmarkProcessDao.findByModelIdAndStatus(model.getModelId(),
					"Completed");
			modelDto.setBenchmarkPerformance(benchmarkProcess);

			return modelDto;
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

	public String storePipelineModelFile(MultipartFile file) throws Exception {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String uploadFolder = modelUploadFolder + "/pipelineModel";
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

	public String storeModelTryMeFile(MultipartFile file) throws Exception {

		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		/*
		 * check file extension
		 */
		String fileExtension = FilenameUtils.getExtension(fileName);
		try {
			ImageFormat imageformat = ImageFormat.fromValue(fileExtension);
			if (imageformat == null && !fileExtension.equalsIgnoreCase("jpg")) {
				log.info("Extension " + fileExtension + " not supported. It should be jpg/jpeg/bmp/png/tiff format");
				throw new FileExtensionNotSupportedException(
						"Extension " + fileExtension + " not supported. It should be jpeg/bmp/png/tiff format");
			}
		} catch (FileExtensionNotSupportedException ex) {

			throw new FileExtensionNotSupportedException(
					"Extension " + fileExtension + " not supported. It should be jpg/jpeg/bmp/png/tiff format");

		}

		String uploadFolder = modelUploadFolder + "/model/tryme";
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

		String modelName = checkModel(file);
		if (modelName.equals("pipelineModel")) {

			String pipelineModelFilepath = storePipelineModelFile(file);
			PipelineModel pipelineModelObj = getUploadedPipelineModel(pipelineModelFilepath);
			if (pipelineModelObj != null) {
				validatePipelineModel(pipelineModelObj);
			} else {
				throw new ModelValidationException("PipelineModel validation failed. Check uploaded file syntax");

			}
			pipelineModelObj.setUserId(userId);
			pipelineModelObj.setSubmittedOn(Instant.now().toEpochMilli());

			InferenceAPIEndPointMasterApiKey pipelineInferenceMasterApiKey = pipelineModelObj.getInferenceEndPoint()
					.getMasterApiKey();
			if (pipelineInferenceMasterApiKey.getValue() != null) {
				log.info("SecretKey :: " + SECRET_KEY);
				String originalApiKeyName = pipelineInferenceMasterApiKey.getName();
				log.info("originalApiKeyName :: " + originalApiKeyName);
				String originalApiKeyValue = pipelineInferenceMasterApiKey.getValue();
				log.info("originalApiKeyValue :: " + originalApiKeyValue);
				String encryptedApiKeyName = Aes256.encrypt(originalApiKeyName, SECRET_KEY);
				log.info("encryptedApiKeyName :: " + encryptedApiKeyName);
				String encryptedApiKeyValue = Aes256.encrypt(originalApiKeyValue, SECRET_KEY);
				log.info("encryptedApiKeyValue :: " + encryptedApiKeyValue);
				pipelineInferenceMasterApiKey.setName(encryptedApiKeyName);
				pipelineInferenceMasterApiKey.setValue(encryptedApiKeyValue);
				pipelineModelObj.getInferenceEndPoint().setMasterApiKey(pipelineInferenceMasterApiKey);

			}

			log.info("pipelineModelObj :: " + pipelineModelObj);

			if (pipelineModelObj != null) {
				try {
					pipelineModelDao.save(pipelineModelObj);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			return new UploadModelResponse("Pipeline Model Saved Successfully", pipelineModelObj);

		}

		else if (modelName.equals("model")) {

			String modelFilePath = storeModelFile(file);
			ModelExtended modelObj = getUploadedModel(modelFilePath);

			if (modelObj != null) {
				validateModel(modelObj);
			} else {
				throw new ModelValidationException("Model validation failed. Check uploaded file syntax");
			}

			ModelTask taskType = modelObj.getTask();
			if (taskType.getType().equals(SupportedTasks.TXT_LANG_DETECTION)) {
				LanguagePair lp = new LanguagePair();
				lp.setSourceLanguage(SupportedLanguages.MIXED);
				LanguagePairs lps = new LanguagePairs();
				lps.add(lp);
				modelObj.setLanguages(lps);
			}

			modelObj.setUserId(userId);
			modelObj.setSubmittedOn(Instant.now().toEpochMilli());
			modelObj.setPublishedOn(Instant.now().toEpochMilli());
			modelObj.setStatus("unpublished");
			modelObj.setUnpublishReason("Newly submitted model");

			InferenceAPIEndPoint inferenceAPIEndPoint = modelObj.getInferenceEndPoint();
			OneOfInferenceAPIEndPointSchema schema = modelObj.getInferenceEndPoint().getSchema();

			if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.ASRInference")
					|| schema.getClass().getName().equalsIgnoreCase("io.swagger.model.TTSInference")) {
				if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.ASRInference")) {
					ASRInference asrInference = (ASRInference) schema;
					if (!asrInference.getModelProcessingType().getType()
							.equals(ModelProcessingType.TypeEnum.STREAMING)) {
						inferenceAPIEndPoint = modelInferenceEndPointService.validateCallBackUrl(inferenceAPIEndPoint);
					}
				} else {
					TTSInference ttsInference = (TTSInference) schema;

					if (!ttsInference.getModelProcessingType().getType()
							.equals(ModelProcessingType.TypeEnum.STREAMING)) {
						inferenceAPIEndPoint = modelInferenceEndPointService.validateCallBackUrl(inferenceAPIEndPoint);
					}
				}
			} else {
				inferenceAPIEndPoint = modelInferenceEndPointService.validateCallBackUrl(inferenceAPIEndPoint);
			}

			if (inferenceAPIEndPoint.getInferenceApiKey() != null) {

				InferenceAPIEndPointInferenceApiKey inferenceAPIEndPointInferenceApiKey = inferenceAPIEndPoint
						.getInferenceApiKey();
				if (inferenceAPIEndPointInferenceApiKey.getValue() != null) {
					String originalName = inferenceAPIEndPointInferenceApiKey.getName();
					String originalValue = inferenceAPIEndPointInferenceApiKey.getValue();
					log.info("SecretKey :: " + SECRET_KEY);
					String encryptedName = Aes256.encrypt(originalName, SECRET_KEY);
					log.info("encryptedName ::" + encryptedName);
					String encryptedValue = Aes256.encrypt(originalValue, SECRET_KEY);
					log.info("encryptedValue ::" + encryptedValue);

					inferenceAPIEndPointInferenceApiKey.setName(encryptedName);
					inferenceAPIEndPointInferenceApiKey.setValue(encryptedValue);
					inferenceAPIEndPoint.setInferenceApiKey(inferenceAPIEndPointInferenceApiKey);
				}
			}
			modelObj.setInferenceEndPoint(inferenceAPIEndPoint);

			if (modelObj != null) {
				try {
					modelDao.save(modelObj);
				} catch (DuplicateKeyException ex) {
					ex.printStackTrace();
					throw new DuplicateKeyException("Model with same name and version exist in system");
				}
			}

			return new UploadModelResponse("Model Saved Successfully", modelObj);

		} else {

			return new UploadModelResponse("Invalid Model!", modelName);

		}
	}

	public ModelExtended getUploadedModel(String modelFilePath) {

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

	public PipelineModel getUploadedPipelineModel(String modelFilePath) {

		PipelineModel modelObj = null;
		ObjectMapper objectMapper = new ObjectMapper();
		File file = new File(modelFilePath);
		try {
			modelObj = objectMapper.readValue(file, PipelineModel.class);

			return modelObj;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return modelObj;
	}

	private Boolean validateModel(ModelExtended model) throws ModelValidationException {

		if (model.getName() == null || model.getName().isBlank())
			throw new ModelValidationException("name is required field");

		if (model.getVersion() == null || model.getVersion().isBlank())
			throw new ModelValidationException("version is required field");

		if (model.getDescription() == null || model.getDescription().isBlank())
			throw new ModelValidationException("description is required field");

		if (model.getTask() == null)
			throw new ModelValidationException("task is required field");

		if (model.getLanguages() == null) {
			ModelTask taskType = model.getTask();
			if (!taskType.getType().equals(SupportedTasks.TXT_LANG_DETECTION)) {
				throw new ModelValidationException("languages is required field");
			}
		}

		if (model.getLicense() == null)
			throw new ModelValidationException("license is required field");

		if (model.getLicense() == License.CUSTOM_LICENSE) {
			if (model.getLicenseUrl().isBlank())
				throw new ModelValidationException("custom licenseUrl is required field");
		}

		if (model.getDomain() == null)
			throw new ModelValidationException("domain is required field");

		if (model.getSubmitter() == null)
			throw new ModelValidationException("submitter is required field");

		if (model.getInferenceEndPoint() == null)
			throw new ModelValidationException("inferenceEndPoint is required field");

		InferenceAPIEndPoint inferenceAPIEndPoint = model.getInferenceEndPoint();

		if (inferenceAPIEndPoint.isIsSyncApi() != null && !inferenceAPIEndPoint.isIsSyncApi()) {
			AsyncApiDetails asyncApiDetails = inferenceAPIEndPoint.getAsyncApiDetails();
			if (asyncApiDetails.getPollingUrl().isBlank()) {
				throw new ModelValidationException("PollingUrl is required field for async model");
			}
		} else {
			if (inferenceAPIEndPoint.getCallbackUrl().isBlank()) {
				throw new ModelValidationException("callbackUrl is required field for sync model");
			}
		}

		if (model.getTrainingDataset() == null)
			throw new ModelValidationException("trainingDataset is required field");

		return true;
	}

	private Boolean validatePipelineModel(PipelineModel model) throws ModelValidationException {

		if (model.getName() == null || model.getName().isBlank())
			throw new ModelValidationException("name is required field");

		if (model.getVersion() == null || model.getVersion().isBlank())
			throw new ModelValidationException("version is required field");

		if (model.getDescription() == null || model.getDescription().isBlank())
			throw new ModelValidationException("description is required field");

		if (model.getDomain() == null)
			throw new ModelValidationException("domain is required field");

		if (model.getSubmitter() == null)
			throw new ModelValidationException("submitter is required field");

		if (model.getInferenceEndPoint() == null)
			throw new ModelValidationException("inferenceEndPoint is required field");

		io.swagger.pipelinemodel.InferenceAPIEndPoint inferenceAPIEndPoint = model.getInferenceEndPoint();

		if (inferenceAPIEndPoint.isIsSyncApi() != null && !inferenceAPIEndPoint.isIsSyncApi()) {
			AsyncApiDetails asyncApiDetails = inferenceAPIEndPoint.getAsyncApiDetails();
			if (asyncApiDetails.getPollingUrl().isBlank()) {
				throw new ModelValidationException("PollingUrl is required field for async model");
			}
		} else {
			if (inferenceAPIEndPoint.getCallbackUrl().isBlank()) {
				throw new ModelValidationException("callbackUrl is required field for sync model");
			}
		}

		if (model.getSupportedPipelines() == null || model.getSupportedPipelines().isEmpty())
			throw new ModelValidationException("supported pipelines  is required field");

		if (model.getTaskSpecifications() == null)
			throw new ModelValidationException("Task Specification is required field");

		return true;
	}

//	public ModelSearchResponse searchModel(ModelSearchRequest request) {
//
//		ModelExtended model = new ModelExtended();
//
//		if (request.getTask() != null && !request.getTask().isBlank()) {
//			ModelTask modelTask = new ModelTask();
//			SupportedTasks modelTaskType = SupportedTasks.fromValue(request.getTask());
//			if(modelTaskType == null) {
//				throw new RequestParamValidationException("task type is not valid");
//			}
//			modelTask.setType(modelTaskType);
//			model.setTask(modelTask);
//		}else {
//			throw new RequestParamValidationException("task is required field");
//		}
//		
//		LanguagePairs lprs = new LanguagePairs();
//		LanguagePair lp = null;
//		if (request.getSourceLanguage() != null && !request.getSourceLanguage().isBlank()) {
//			 lp = new LanguagePair();
//			lp.setSourceLanguage(SupportedLanguages.fromValue(request.getSourceLanguage()));
//			
//		}
//		
//		if (request.getTargetLanguage() != null && !request.getTargetLanguage().isBlank()) {
//			if(lp==null) {
//				lp = new LanguagePair();
//				
//			}
//			 
//			lp.setTargetLanguage(SupportedLanguages.fromValue(request.getTargetLanguage()));
//		}
//		
//		if(lp!=null) {
//			
//			lprs.add(lp);
//			model.setLanguages(lprs);
//		}
//		
//	
//		/*
//		 * seach only published model
//		 */
//		model.setStatus("published");
//
//		Example<ModelExtended> example = Example.of(model);
//		List<ModelExtended> list = modelDao.findAll(example);
//		
//		if(list != null) {
//			Collections.shuffle(list); // randomize the search
//			return new ModelSearchResponse("Model Search Result", list, list.size());
//		}
//		return new ModelSearchResponse("Model Search Result", list, 0);
//		
//		
//
//	}

	public ModelSearchResponse searchModel(ModelSearchRequest request) {

		Query dynamicQuery = new Query();

		if (request.getTask() != null && !request.getTask().isBlank()) {
			SupportedTasks modelTaskType = SupportedTasks.fromValue(request.getTask());
			if (modelTaskType == null) {
				throw new RequestParamValidationException("task type is not valid");
			}
			Criteria nameCriteria = Criteria.where("task.type").is(modelTaskType.name());
			dynamicQuery.addCriteria(nameCriteria);
		} else {
			throw new RequestParamValidationException("task is required field");
		}

		if (request.getSourceLanguage() != null && !request.getSourceLanguage().isBlank()
				&& !request.getSourceLanguage().equalsIgnoreCase("All")) {
			Criteria nameCriteria = Criteria.where("languages.0.sourceLanguage")
					.is(SupportedLanguages.fromValue(request.getSourceLanguage()).name());
			dynamicQuery.addCriteria(nameCriteria);
		}

		if (request.getTargetLanguage() != null && !request.getTargetLanguage().isBlank()
				&& !request.getTargetLanguage().equalsIgnoreCase("All")) {
			Criteria nameCriteria = Criteria.where("languages.0.targetLanguage")
					.is(SupportedLanguages.fromValue(request.getTargetLanguage()).name());
			dynamicQuery.addCriteria(nameCriteria);
		}

		// domain

		if (request.getDomain() != null && !request.getDomain().isBlank()
				&& !request.getDomain().equalsIgnoreCase("All")) {
			Criteria nameCriteria = Criteria.where("domain.0").is(request.getDomain());
			dynamicQuery.addCriteria(nameCriteria);
		}

		// submitter
		if (request.getSubmitter() != null && !request.getSubmitter().isBlank()
				&& !request.getSubmitter().equalsIgnoreCase("All")) {
			Criteria nameCriteria = Criteria.where("submitter.name").is(request.getSubmitter());
			dynamicQuery.addCriteria(nameCriteria);
		}

		// userId

		/*
		 * if (request.getUserId()!= null && !request.getUserId().isBlank() &&
		 * !request.getUserId().equalsIgnoreCase("All")) { Criteria nameCriteria =
		 * Criteria.where("userId").is(request.getUserId());
		 * dynamicQuery.addCriteria(nameCriteria); }
		 */

		Criteria nameCriteria = Criteria.where("status").is("published");
		dynamicQuery.addCriteria(nameCriteria);

		log.info("dynamicQuery : " + dynamicQuery.toString());

		List<ModelExtended> list = mongoTemplate.find(dynamicQuery, ModelExtended.class);

		ArrayList<ModelExtendedDto> modelDtoList = new ArrayList<ModelExtendedDto>();

		for (ModelExtended model : list) {

			ModelExtendedDto modelExtendedDto = new ModelExtendedDto();

			BeanUtils.copyProperties(model, modelExtendedDto);

			InferenceAPIEndPoint inferenceAPIEndPoint = model.getInferenceEndPoint();
			InferenceAPIEndPointDto dto = new InferenceAPIEndPointDto();
			BeanUtils.copyProperties(inferenceAPIEndPoint, dto);
			modelExtendedDto.setInferenceEndPoint(dto);
			modelDtoList.add(modelExtendedDto);
		}

		/*
		 * if(list != null) { Collections.shuffle(list); // randomize the search return
		 * new ModelSearchResponse("Model Search Result", list, list.size()); }
		 */

		if (modelDtoList != null) {
			Collections.shuffle(modelDtoList); // randomize the search
			return new ModelSearchResponse("Model Search Result", modelDtoList, modelDtoList.size());
		}
		return new ModelSearchResponse("Model Search Result", modelDtoList, 0);

	}

	public ModelComputeResponse computeModel(ModelComputeRequest compute) throws Exception {

		String modelId = compute.getModelId();
		ModelExtended modelObj = modelDao.findById(modelId).get();
		InferenceAPIEndPoint inferenceAPIEndPoint = modelObj.getInferenceEndPoint();

		return modelInferenceEndPointService.compute(inferenceAPIEndPoint, compute);
	}

	public ModelComputeResponse tryMeOcrImageContent(MultipartFile file, String modelId) throws Exception {

		String imageFilePath = storeModelTryMeFile(file);

		ModelExtended modelObj = modelDao.findById(modelId).get();
		InferenceAPIEndPoint inferenceAPIEndPoint = modelObj.getInferenceEndPoint();
		// String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();
		OneOfInferenceAPIEndPointSchema schema = inferenceAPIEndPoint.getSchema();

		ModelComputeResponse response = modelInferenceEndPointService.compute(inferenceAPIEndPoint, schema,
				imageFilePath);

		return response;
	}

	public ModelStatusChangeResponse changeStatus(@Valid ModelStatusChangeRequest request) {

		String userId = request.getUserId();
		String modelId = request.getModelId();
		String status = request.getStatus().toString();
		ModelExtended model = modelDao.findByModelId(modelId);
		if (model == null) {
			throw new ModelNotFoundException("model with modelId : " + modelId + " not found");
		}
		if (!model.getUserId().equalsIgnoreCase(userId)) {
			throw new ModelStatusChangeException("Not the submitter of model. So, can not " + status + " it.", status);
		}
		model.setStatus(status);
		if (status.equalsIgnoreCase("unpublished")) {
			if (request.getUnpublishReason() == null
					|| (request.getUnpublishReason() != null && request.getUnpublishReason().isBlank())) {
				throw new ModelStatusChangeException("unpublishReason field should not be empty", status);

			}

			model.setUnpublishReason(request.getUnpublishReason());
		}
		modelDao.save(model);

		return new ModelStatusChangeResponse("Model " + status + " successfull.");
	}

	public ModelFeedbackSubmitResponse modelFeedbackSubmit(ModelFeedbackSubmitRequest request) throws IOException {

		String taskType = request.getTaskType();
		if (taskType == null || (!taskType.equalsIgnoreCase("translation") && !taskType.equalsIgnoreCase("asr")
				&& !taskType.equalsIgnoreCase("ocr") && !taskType.equalsIgnoreCase("tts")
				&& !taskType.equalsIgnoreCase("sts") && !taskType.equalsIgnoreCase("ner"))) {

			throw new RequestParamValidationException(
					"Model taskType should be one of { translation, asr, ocr, tts , sts or ner }");
		}

		ModelFeedback feedback = new ModelFeedback();
		BeanUtils.copyProperties(request, feedback);
		feedback.setInput("");
		feedback.setOutput("");

		// feedback =setInputOutput(request ,feedback);

		feedback.setCreatedAt(new Date().toString());
		feedback.setUpdatedAt(new Date().toString());

		modelFeedbackDao.save(feedback);

		String feedbackId = feedback.getFeedbackId();
		String userId = feedback.getUserId();

		if (request.getTaskType() != null && !request.getTaskType().isBlank()
				&& request.getTaskType().equalsIgnoreCase("sts")) {

			List<ModelFeedbackSubmitRequest> detailedFeedback = request.getDetailedFeedback();
			for (ModelFeedbackSubmitRequest modelFeedback : detailedFeedback) {

				ModelFeedback mfeedback = new ModelFeedback();
				BeanUtils.copyProperties(modelFeedback, mfeedback);
				// feedback =setInputOutput(modelFeedback ,mfeedback);
				mfeedback.setInput("");
				mfeedback.setOutput("");
				mfeedback.setStsFeedbackId(feedbackId);
				mfeedback.setUserId(userId);
				mfeedback.setCreatedAt(new Date().toString());
				mfeedback.setUpdatedAt(new Date().toString());

				modelFeedbackDao.save(mfeedback);

			}
		}

		ModelFeedbackSubmitResponse response = new ModelFeedbackSubmitResponse("model feedback submitted successful",
				feedbackId);
		return response;
	}

	public List<ModelFeedback> getModelFeedbackByModelId(String modelId) {

		return modelFeedbackDao.findByModelId(modelId);

	}

	public List<GetModelFeedbackListResponse> getModelFeedbackByTaskType(String taskType) {

		List<GetModelFeedbackListResponse> response = new ArrayList<GetModelFeedbackListResponse>();
		List<ModelFeedback> feedbackList = modelFeedbackDao.findByTaskType(taskType);

		for (ModelFeedback feedback : feedbackList) {

			GetModelFeedbackListResponse res = new GetModelFeedbackListResponse();
			BeanUtils.copyProperties(feedback, res);

			if (taskType.equalsIgnoreCase("sts")) {
				List<ModelFeedback> stsDetailedFd = modelFeedbackDao.findByStsFeedbackId(feedback.getFeedbackId());
				res.setDetailedFeedback(stsDetailedFd);
			}
			response.add(res);
		}
		return response;
	}

	public ModelHealthStatusResponse modelHealthStatus(String taskType, Integer startPage, Integer endPage) {
		log.info("******** Entry ModelService:: modelHealthStatus *******");

		List<ModelHealthStatus> list = new ArrayList<ModelHealthStatus>();
		if (taskType == null || taskType.isBlank()) {
			list = modelHealthStatusDao.findAll();
		} else {

			if (startPage != null) {
				int startPg = startPage - 1;
				for (int i = startPg; i < endPage; i++) {
					Pageable paging = PageRequest.of(i, PAGE_SIZE);
					Page<ModelHealthStatus> modelHealthStatusesList = modelHealthStatusDao.findByTaskType(taskType,
							paging);
					list.addAll(modelHealthStatusesList.toList());
				}
			} else {
				list = modelHealthStatusDao.findByTaskType(taskType);
			}
		}
		log.info("******** Exit ModelService:: modelHealthStatus *******");

		return new ModelHealthStatusResponse("ModelHealthStatus", list, list.size());
	}

	public GetTransliterationModelIdResponse getTransliterationModelId(String sourceLanguage, String targetLanguage) {

		ModelExtended model = new ModelExtended();

		ModelTask modelTask = new ModelTask();
		modelTask.setType(SupportedTasks.TRANSLITERATION);
		model.setTask(modelTask);

		LanguagePairs lprs = new LanguagePairs();
		LanguagePair lp = new LanguagePair();
		lp.setSourceLanguage(SupportedLanguages.fromValue(sourceLanguage));
		if (targetLanguage != null && !targetLanguage.isBlank()) {
			lp.setTargetLanguage(SupportedLanguages.fromValue(targetLanguage));
		}
		lprs.add(lp);
		model.setLanguages(lprs);

		Submitter submitter = new Submitter();
		submitter.setName("AI4Bharat");
		model.setSubmitter(submitter);

		/*
		 * seach only published model
		 */
		model.setStatus("published");

		Example<ModelExtended> example = Example.of(model);
		List<ModelExtended> list = modelDao.findAll(example);

		if (list != null && list.size() > 0) {
			GetTransliterationModelIdResponse response = new GetTransliterationModelIdResponse();
			ModelExtended obj = list.get(0);
			response.setModelId(obj.getModelId());
			return response;
		}

		return null;
	}

	public ModelFeedback setInputOutput(ModelFeedbackSubmitRequest request, ModelFeedback feedback) throws IOException {

		MultipartFile inputFile = request.getMultipartInput();
		MultipartFile outputFile = request.getMultipartInput();

		if (request.getTaskType().equals("asr")) {
			// perform audio save to azure

			String audioInputFileName = inputFile.getName();
			log.info("audioInputFileName : " + audioInputFileName);

			// set audio link to feedback collection in mongo

			String audioInputUrl = "dummyurl";
			feedback.setInput(audioInputUrl);

			String audioOutputContent = new String(outputFile.getBytes());
			feedback.setOutput(audioOutputContent);

		} else if (request.getTaskType().equals("ocr")) {
			// perform image save to azure

			String imageFileName = inputFile.getName();
			log.info("imageFileName : " + imageFileName);

			// set image link to feedback collection in mongo
			String imageInputUrl = "dummyUrl";
			feedback.setInput(imageInputUrl);

			String imageOutputContent = new String(outputFile.getBytes());
			feedback.setOutput(imageOutputContent);

		} else if (request.getTaskType().equals("tts")) {

			// perform input text to save mongo
			String inputContent = new String(inputFile.getBytes());
			feedback.setInput(inputContent);

			// perform save output audio to save in azure

			// save audio output link to mongo
			String audioOutputUrl = "dummyUrl";
			feedback.setOutput(audioOutputUrl);

		}

		else {
			// perform txt save to mongo
			String txtFileName = inputFile.getName();
			log.info("txtFileName : " + txtFileName);

			// String inputText = inputFile.

			String inputContent = new String(inputFile.getBytes());
			feedback.setInput(inputContent);

			String outputContent = new String(outputFile.getBytes());
			feedback.setOutput(outputContent);

		}

		return feedback;

	}

	public String getModelsPipeline(MultipartFile file, String userId) throws Exception {
		log.info("File :: " + file.toString());
		PipelineRequest pipelineRequest = getPipelineRequest(file);
		log.info("pipelineRequest :: " + pipelineRequest);
		if (pipelineRequest != null) {
			validatePipelineRequest(pipelineRequest);
		} else {
			throw new PipelineValidationException("Pipeline validation failed. Check uploaded file syntax");
		}
        
		
		//Make query and find pipeline model with the submitter name
				Query dynamicQuery1 = new Query();
				Criteria modelTypeCriteria1 = Criteria.where("_class").is("com.ulca.model.dao.PipelineModel");
				dynamicQuery1.addCriteria(modelTypeCriteria1);
				Criteria submitterCriteria1 = Criteria.where("submitter.name").is(pipelineRequest.getPipelineRequestConfig().getSubmitter());
				dynamicQuery1.addCriteria(submitterCriteria1);
				log.info("dynamicQuery : " + dynamicQuery1.toString());
				PipelineModel pipelineModel = mongoTemplate.findOne(dynamicQuery1, PipelineModel.class);
		
		ArrayList<PipelineTask> pipelineTasks = pipelineRequest.getPipelineTasks();

	//	ArrayList<String> pipelineTaskSequence = new ArrayList<String>();
		
		PipelineResponse pipelineResponse = new PipelineResponse();
		
	   PipelineInferenceAPIEndPoint pipelineInferenceAPIEndPoint = new PipelineInferenceAPIEndPoint();
	   pipelineInferenceAPIEndPoint.setCallbackUrl(pipelineModel.getInferenceEndPoint().getCallbackUrl());
	   pipelineInferenceAPIEndPoint.setIsSyncApi(pipelineModel.getInferenceEndPoint().isIsSyncApi());
	   pipelineInferenceAPIEndPoint.setIsMultilingualEnabled(pipelineModel.getInferenceEndPoint().isIsMultilingualEnabled());
	   pipelineInferenceAPIEndPoint.setAsyncApiDetails(pipelineModel.getInferenceEndPoint().getAsyncApiDetails());
	   pipelineResponse.setPipelineInferenceAPIEndPoint(pipelineInferenceAPIEndPoint);
	   TaskSchemaList taskSchemaList = new TaskSchemaList();
	   
		Set<String> sourceLanguages = new HashSet<String>();
		Set<String> targetLanguages = new HashSet<String>();

		//For each task in input pipeline request
		for(PipelineTask pipelineTask : pipelineTasks)
		{
			
	     String task=pipelineTask.getTaskType();
			if(task=="translation") {
			
				TranslationTaskInference translationTaskInference = new TranslationTaskInference();
				//translationTaskInference.setTaskType(SupportedTasks.fromValue(task));
				
				TranslationTask translationTask	=(TranslationTask)pipelineTask;
				targetLanguages.clear();
				if(translationTask.getConfig()!=null && translationTask.getConfig().getLanguage()!=null)
				{
					TranslationRequestConfig translationRequestConfig =	translationTask.getConfig();
					LanguagePair languagePair =translationRequestConfig.getLanguage();
					{
						sourceLanguages.clear();
						sourceLanguages.add(languagePair.getSourceLanguage().toString().toUpperCase());
					}
					if(languagePair.getTargetLanguage()!=null)
					{
						//targetLanguages.clear();
						targetLanguages.add(languagePair.getTargetLanguage().toString().toUpperCase());
					}
					log.info("Source Languages :: "+sourceLanguages);
					log.info("Target Languages :: "+targetLanguages);				
				}

				log.info("SourceLanguages :: "+sourceLanguages);
				log.info("TargetLanguages :: "+targetLanguages);

				Query dynamicQuery = new Query();
				
				Criteria modelTypeCriteria = Criteria.where("_class").is("com.ulca.model.dao.ModelExtended");
				dynamicQuery.addCriteria(modelTypeCriteria);

				Criteria submitterCriteria = Criteria.where("submitter.name").is(pipelineRequest.getPipelineRequestConfig().getSubmitter());
				dynamicQuery.addCriteria(submitterCriteria);
			    
				Criteria taskCriteria = Criteria.where("task.type").is("TRANSLATION");
				dynamicQuery.addCriteria(taskCriteria);

				if(sourceLanguages.size() != 0 && targetLanguages.size() != 0)
				{
					Criteria languagesCriteria = new Criteria();
					languagesCriteria.andOperator(Criteria.where("languages.sourceLanguage").in(sourceLanguages),
													   Criteria.where("languages.targetLanguage").in(targetLanguages));
					dynamicQuery.addCriteria(languagesCriteria);
				}
				else if(sourceLanguages.size() !=0 )
				{
				 	Criteria languagesCriteria = new Criteria();
				 	languagesCriteria.orOperator(Criteria.where("languages.sourceLanguage").in(sourceLanguages));
				 	dynamicQuery.addCriteria(languagesCriteria);
				}
				else if(targetLanguages.size() !=0 )
				{
				 	Criteria languagesCriteria = new Criteria();
				 	languagesCriteria.orOperator(Criteria.where("languages.targetLanguage").in(targetLanguages));
				 	dynamicQuery.addCriteria(languagesCriteria);
				}

				//TODO: CHANGE LOGIC TO WORK FOR OR CRITERIA FOR LIST OF SOURCE AND TARGET LANGUAGES
				// if(targetLanguages.size() != 0)
				// {
				// 	Criteria targetLanguagesCriteria = new Criteria();
				// 	targetLanguagesCriteria.orOperator(Criteria.where("languages.targetLanguage").in(targetLanguages));
				// 	dynamicQuery.addCriteria(targetLanguagesCriteria);
				// }

				log.info("dynamicQuery in translation task search ::" + dynamicQuery.toString());
				
				List<ModelExtended> translationModels = mongoTemplate.find(dynamicQuery, ModelExtended.class);

				sourceLanguages.clear();
				targetLanguages.clear();
				
				List<TranslationResponseConfig> config = new ArrayList<TranslationResponseConfig>();
				for(ModelExtended each_model : translationModels) 
				{
					log.info("Model Name :: " + each_model.getName());
					LanguagePairs langPair = each_model.getLanguages();
					TranslationResponseConfig translationResponseConfig = new TranslationResponseConfig();

					for(LanguagePair lp : langPair)	
					{
						sourceLanguages.add(lp.getSourceLanguage().toString().toUpperCase());
						targetLanguages.add(lp.getTargetLanguage().toString().toUpperCase());	
						translationResponseConfig.setModelId("");
						translationResponseConfig.setLanguage(lp);
					}
					//TODO: Read each model and store the results in PipelineResponseConfig
					config.add(translationResponseConfig);
				}
				
				log.info(" SourceLanguages at end of Translation :: "+sourceLanguages);
				log.info(" TargetLanguages at end of Translation :: "+targetLanguages);
				sourceLanguages = targetLanguages;
				translationTaskInference.setConfig(config);
				taskSchemaList.add(translationTaskInference);
			}
			
			else if(task=="asr") 
			{
				
				ASRTaskInference asrTaskInference = new ASRTaskInference();
				//aSRTaskInference.setTaskType(SupportedTasks.fromValue(task));
				
				List<ASRResponseConfig> config = new ArrayList<ASRResponseConfig>();

				
				ASRTask asrTask=(ASRTask)pipelineTask;
				if(asrTask.getConfig()!=null && asrTask.getConfig().getLanguage()!=null)
				{
					ASRRequestConfig asrRequestConfig =	asrTask.getConfig();
					LanguagePair languagePair =asrRequestConfig.getLanguage();
					if(languagePair.getSourceLanguage()!=null)
					{
						sourceLanguages.clear();
						sourceLanguages.add(languagePair.getSourceLanguage().toString().toUpperCase());
					}
					// if(languagePair.getTargetLanguage()!=null)
					// 	targetLanguages.add(languagePair.getTargetLanguage().toString().toUpperCase());
					log.info("Source Languages :: "+sourceLanguages);
					// log.info("Target Languages :: "+targetLanguages);				
				}

				Query dynamicQuery = new Query();
				
				Criteria modelTypeCriteria = Criteria.where("_class").is("com.ulca.model.dao.ModelExtended");
				dynamicQuery.addCriteria(modelTypeCriteria);

				Criteria submitterCriteria = Criteria.where("submitter.name").is(pipelineRequest.getPipelineRequestConfig().getSubmitter());
				dynamicQuery.addCriteria(submitterCriteria);
			    
				Criteria taskCriteria = Criteria.where("task.type").is("ASR");
				dynamicQuery.addCriteria(taskCriteria);

				// if(sourceLanguages.size() != 0 && targetLanguages.size() != 0)
				// {
				// 	Criteria languagesCriteria = new Criteria();
				// 	languagesCriteria.andOperator(Criteria.where("languages.sourceLanguage").in(sourceLanguages),
				// 									   Criteria.where("languages.targetLanguage").in(targetLanguages));
				// 	dynamicQuery.addCriteria(languagesCriteria);
				// }
				if(sourceLanguages.size() !=0)
				{
				 	Criteria languagesCriteria = new Criteria();
				 	languagesCriteria.orOperator(Criteria.where("languages.sourceLanguage").in(sourceLanguages));
				 	dynamicQuery.addCriteria(languagesCriteria);
				}
				// else if(targetLanguages.size() !=0 )
				// {
				//  	Criteria languagesCriteria = new Criteria();
				//  	languagesCriteria.orOperator(Criteria.where("languages.targetLanguage").in(targetLanguages));
				//  	dynamicQuery.addCriteria(languagesCriteria);
				// }

				// if(targetLanguages.size() != 0)
				// {
				// 	Criteria targetLanguagesCriteria = new Criteria();
				// 	targetLanguagesCriteria.orOperator(Criteria.where("languages.targetLanguage").in(targetLanguages));
				// 	dynamicQuery.addCriteria(targetLanguagesCriteria);
				// }

				log.info("dynamicQuery in ASR task search ::" + dynamicQuery.toString());

				
				List<ModelExtended> asrModels = mongoTemplate.find(dynamicQuery, ModelExtended.class);

				sourceLanguages.clear();
				targetLanguages.clear();

				for(ModelExtended each_model : asrModels) 
				{
					
					ASRResponseConfig asrResponseConfig = new ASRResponseConfig();
					log.info("Model Name :: " + each_model.getName());
					LanguagePairs langPair = each_model.getLanguages();
					for(LanguagePair lp : langPair)	
					{
						sourceLanguages.add(lp.getSourceLanguage().toString().toUpperCase());
						asrResponseConfig.setModelId("");
						asrResponseConfig.setLanguage(lp);
					}
					//TODO: Read each model and store the results in PipelineResponseConfig
					asrResponseConfig.setDomain(each_model.getDomain());
				
					
					
					config.add(asrResponseConfig);
				}
				
				asrTaskInference.setConfig(config);
				taskSchemaList.add(asrTaskInference);

			}
			else if(task=="tts") 
			{
				
				TTSTaskInference ttsTaskInference = new TTSTaskInference();
				//tTSTaskInference.setTaskType(SupportedTasks.fromValue(task));
				
				List<TTSResponseConfig> config = new ArrayList<TTSResponseConfig>();

				
				
				
				TTSTask ttsTask=(TTSTask)pipelineTask;
				if(ttsTask.getConfig()!=null && ttsTask.getConfig().getLanguage()!=null)
				{
					TTSRequestConfig  ttsRequestConfig = ttsTask.getConfig();
					LanguagePair languagePair = ttsRequestConfig.getLanguage();
					if(languagePair.getSourceLanguage()!=null)
					{
						sourceLanguages.clear();
						sourceLanguages.add(languagePair.getSourceLanguage().toString().toUpperCase());
					}					// if(languagePair.getTargetLanguage()!=null)
					// 	targetLanguages.add(languagePair.getTargetLanguage().toString().toUpperCase());
					log.info("Source Languages :: "+sourceLanguages);
					// log.info("Target Languages :: "+targetLanguages);				
				}

				Query dynamicQuery = new Query();
				
				Criteria modelTypeCriteria = Criteria.where("_class").is("com.ulca.model.dao.ModelExtended");
				dynamicQuery.addCriteria(modelTypeCriteria);

				Criteria submitterCriteria = Criteria.where("submitter.name").is(pipelineRequest.getPipelineRequestConfig().getSubmitter());
				dynamicQuery.addCriteria(submitterCriteria);
			    
				Criteria taskCriteria = Criteria.where("task.type").is("TTS");
				dynamicQuery.addCriteria(taskCriteria);

				// if(sourceLanguages.size() != 0 && targetLanguages.size() != 0)
				// {
				// 	Criteria languagesCriteria = new Criteria();
				// 	languagesCriteria.andOperator(Criteria.where("languages.sourceLanguage").in(sourceLanguages),
				// 									   Criteria.where("languages.targetLanguage").in(targetLanguages));
				// 	dynamicQuery.addCriteria(languagesCriteria);
				// }
				if(sourceLanguages.size() !=0 )
				{
				 	Criteria languagesCriteria = new Criteria();
				 	languagesCriteria.orOperator(Criteria.where("languages.sourceLanguage").in(sourceLanguages));
				 	dynamicQuery.addCriteria(languagesCriteria);
				}
				// else if(targetLanguages.size() !=0 )
				// {
				//  	Criteria languagesCriteria = new Criteria();
				//  	languagesCriteria.orOperator(Criteria.where("languages.targetLanguage").in(targetLanguages));
				//  	dynamicQuery.addCriteria(languagesCriteria);
				// }

				//TODO: CHANGE LOGIC TO WORK FOR OR CRITERIA FOR LIST OF SOURCE AND TARGET LANGUAGES
				// if(targetLanguages.size() != 0)
				// {
				// 	Criteria targetLanguagesCriteria = new Criteria();
				// 	targetLanguagesCriteria.orOperator(Criteria.where("languages.targetLanguage").in(targetLanguages));
				// 	dynamicQuery.addCriteria(targetLanguagesCriteria);
				// }

				log.info("dynamicQuery in TTS Models task search ::" + dynamicQuery.toString());

				List<ModelExtended> ttsModels = mongoTemplate.find(dynamicQuery, ModelExtended.class);

				sourceLanguages.clear();
				targetLanguages.clear();
				
				for(ModelExtended each_model : ttsModels) 
				{
					
					TTSResponseConfig ttsResponseConfig = new TTSResponseConfig();

					log.info("Model Name :: " + each_model.getName());
					LanguagePairs langPair = each_model.getLanguages();
					for(LanguagePair lp : langPair)	
					{
						sourceLanguages.add(lp.getSourceLanguage().toString().toUpperCase());
						ttsResponseConfig.setModelId("");
						ttsResponseConfig.setLanguage(lp);
					}
					//TODO: Read each model and store the results in PipelineResponseConfig
			
					config.add(ttsResponseConfig);

				}
				
				log.info("config :: "+config);
				ttsTaskInference.setConfig(config);
				taskSchemaList.add(ttsTaskInference);
			}
	     

	      
		}
		pipelineResponse.setPipelineResponseConfig(taskSchemaList);
		
		//TODO: Add PipelineInferenceEndPoint without api keys (Except for it, everything else copied from pipelinemodel)
		
		//TODO: Add language pairs to response

		TaskSchemaList responseConfig = pipelineResponse.getPipelineResponseConfig(); 
		
		LanguagesList languageList = pipelineResponse.getLanguages();

		for(TaskSchema each_task : responseConfig) 
		{
			log.info("Each Task :: "+each_task.getClass());
		}
		
		pipelineResponse.setLanguages(languageList);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		String json = mapper.writeValueAsString(pipelineResponse);
		log.info("String JSON :: "+json);
		return json;
	}

	public static String checkModel(MultipartFile file) {

		try {
			Object rowObj = new Gson().fromJson(new InputStreamReader(file.getInputStream()), Object.class);

			ObjectMapper mapper = new ObjectMapper();

			String dataRow = mapper.writeValueAsString(rowObj);
			JSONObject params = new JSONObject(dataRow);

			if (!params.has("supportedPipelines")) {
				return "model";
			} else {
				return "pipelineModel";
			}
		} catch (Exception e) {

			e.printStackTrace();

			return "Invalid Model!";

		}

	}

	public Boolean validatePipelineRequest(PipelineRequest pipelineRequest) {
		log.info("Enter to validate pipelineRequest");
		if (pipelineRequest.getPipelineTasks() == null || pipelineRequest.getPipelineTasks().isEmpty())
			throw new PipelineValidationException("PipelineTasks is required field");

		log.info("pipelineRequest.getPipelineTasks() validated");

		if (pipelineRequest.getPipelineRequestConfig() == null
				&& pipelineRequest.getPipelineRequestConfig().getSubmitter() == null)
			throw new PipelineValidationException("PipelineRequestConfig and submitter are required field");

		log.info("pipelineRequest.getPipelineRequestConfig() is validated");

		ArrayList<PipelineTask> pipelineTasks = pipelineRequest.getPipelineTasks();

		ArrayList<String> pipelineTaskSequence = new ArrayList<String>();

		for(PipelineTask pipelineTask : pipelineTasks)
			pipelineTaskSequence.add(pipelineTask.getTaskType());
		
		log.info("Pipeline Sequence :: "+pipelineTaskSequence);

		//Make query and find pipeline model with the submitter name
		Query dynamicQuery = new Query();
		Criteria modelTypeCriteria = Criteria.where("_class").is("com.ulca.model.dao.PipelineModel");
		dynamicQuery.addCriteria(modelTypeCriteria);
		Criteria submitterCriteria = Criteria.where("submitter.name").is(pipelineRequest.getPipelineRequestConfig().getSubmitter());
		dynamicQuery.addCriteria(submitterCriteria);
		log.info("dynamicQuery : " + dynamicQuery.toString());
		PipelineModel pipelineModel = mongoTemplate.findOne(dynamicQuery, PipelineModel.class);

		if (pipelineModel == null)
			throw new PipelineValidationException("Pipeline model with the request submitter does not exist");

		ArrayList<PipelineTaskSequence> supportedPipelines = pipelineModel.getSupportedPipelines();
          boolean flag = false;
		for(PipelineTaskSequence sequence : supportedPipelines) {

			log.info("Sequence :: " + sequence);
			ArrayList<String> pipelineTaskSequenceInModel = new ArrayList<String>();
             for(SupportedTasks task:sequence) {
            	 pipelineTaskSequenceInModel.add(task.name().toLowerCase());
            	 
             }
			log.info("pipelineTaskSequenceInModel :: "+pipelineTaskSequenceInModel);
			
			if(pipelineTaskSequenceInModel.equals(pipelineTaskSequence)) {
				log.info("available");
				flag =true;
				break;
			}
			
			
			
		}
		
		if (!flag)
			throw new PipelineValidationException("Requested pipeline in not exist with this submitter!");
		
		
		
		log.info("pipelineRequest.getPipelineTasks() validated");
         

		return true;
	}

	public PipelineRequest getPipelineRequest(MultipartFile file) {
		log.info("Enter to get PipelineRequest ");
		PipelineRequest pipelineRequest = null;
		ObjectMapper objectMapper = new ObjectMapper();

		Version version = objectMapper.version();
		log.info("jackson version :: " + version);

		try {

			pipelineRequest = objectMapper.readValue(file.getInputStream(), PipelineRequest.class);

			return pipelineRequest;

		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("Exit to get PipelineRequest ");

		return pipelineRequest;

	}

	/*
	 * public Boolean checkTaskSequence(PipelineRequest pipelineRequest) {
	 * 
	 * PipelineModel pipelineModel = pipelineModelDao
	 * .findBySubmitterName(pipelineRequest.getPipelineRequestConfig().getSubmitter(
	 * ));
	 * 
	 * if (pipelineModel == null) throw new
	 * PipelineValidationException("This submitter don't have any pipeline model!");
	 * 
	 * PipelineTaskSequence requestPipelineTaskSequence = new
	 * PipelineTaskSequence(); List<PipelineTask> requestList =
	 * pipelineRequest.getPipelineTasks();
	 * 
	 * for (PipelineTask pipelineTask : requestList) {
	 * 
	 * String task = pipelineTask.getType().name(); log.info("task :: " + task);
	 * requestPipelineTaskSequence.add(SupportedTasks.valueOf(task)); }
	 * 
	 * log.info("requestPipelineTaskSequence :: " + requestPipelineTaskSequence);
	 * 
	 * boolean flag = false; ListOfPipelines listOfPipelines =
	 * pipelineModel.getSupportedPipelines();
	 * 
	 * for (PipelineTaskSequence pipelineTaskSequence : listOfPipelines) {
	 * log.info("pipelineTaskSequence :: " + pipelineTaskSequence);
	 * 
	 * if (pipelineTaskSequence.hashCode() ==
	 * requestPipelineTaskSequence.hashCode()) {
	 * 
	 * log.info("matched"); flag = true; break; }
	 * 
	 * }
	 * 
	 * if (!flag) {
	 * 
	 * throw new PipelineValidationException("This pipeline doesn't exist!");
	 * 
	 * }
	 * 
	 * return true;
	 * 
	 * }
	 */
}
