package com.ulca.model.service;

import java.util.Iterator;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ulca.benchmark.dao.BenchmarkDao;
import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.benchmark.util.DomainEnum;
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
import com.ulca.model.exception.ModelComputeException;
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
import com.ulca.model.response.PipelineModelResponse;
import com.ulca.model.response.PipelinesResponse;
import com.ulca.model.response.UploadModelResponse;

import io.netty.handler.codec.http.HttpRequest;
import io.swagger.model.ASRInference;
import io.swagger.model.AsyncApiDetails;
import io.swagger.model.Domain;
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
import io.swagger.model.VoiceTypes;
import io.swagger.pipelinemodel.ConfigList;
import io.swagger.pipelinemodel.ConfigSchema;
import io.swagger.pipelinemodel.InferenceAPIEndPointMasterApiKey;
import io.swagger.pipelinemodel.ListOfPipelines;
import io.swagger.pipelinemodel.PipelineTaskSequence;
import io.swagger.pipelinemodel.TaskSpecification;
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
import io.swagger.pipelinerequest.TranslationTaskInferenceInferenceApiKey;
//import io.swagger.v3.core.util.Json;
import io.swagger.pipelinerequest.PipelineResponse;
import io.swagger.pipelinerequest.LanguagesList;
import io.swagger.pipelinerequest.LanguageSchema;
import io.swagger.pipelinemodel.TaskSpecifications;
import io.swagger.pipelinemodel.TaskSpecification;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.github.mervick.aes_everywhere.Aes256;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.client.model.geojson.LineString;

import com.fasterxml.jackson.databind.SerializationFeature;
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

	@Value("${aes.secret.key1}")
	private String aessecretkey1;

	@Value("${aes.secret.key2}")
	private String aessecretkey2;

	@Value("${ulca.model.upload.folder}")
	private String modelUploadFolder;

	@Value("${userId}")
	private String userId;

	@Value("${ulca.apikey1}")
	private String ulcaapikey1;

	@Value("${ulca.apikey2}")
	private String ulcaapikey2;

	@Value("${ulca.ums.host}")
	private String ulca_ums_host;

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

			// checkModelAvailablity(pipelineModelObj);

			// PipelineModel checkModel =
			// pipelineModelDao.findBySubmitterName(pipelineModelObj.getSubmitter().getName());

			if (pipelineModelObj != null) {

				pipelineModelObj.setUserId(userId);
				pipelineModelObj.setSubmittedOn(Instant.now().toEpochMilli());

				io.swagger.pipelinemodel.InferenceAPIEndPoint inferenceAPIEndPoint = pipelineModelObj
						.getInferenceEndPoint();

				if (inferenceAPIEndPoint.getMasterApiKey() != null) {
					InferenceAPIEndPointMasterApiKey pipelineInferenceMasterApiKey = inferenceAPIEndPoint
							.getMasterApiKey();
					if (pipelineInferenceMasterApiKey.getValue() != null
							&& !pipelineInferenceMasterApiKey.getValue().isEmpty()) {
						log.info("SecretKey :: " + SECRET_KEY);
						String originalApiKeyName = pipelineInferenceMasterApiKey.getName();
						log.info("originalApiKeyName :: " + originalApiKeyName);
						String originalApiKeyValue = pipelineInferenceMasterApiKey.getValue();
						log.info("originalApiKeyValue :: " + originalApiKeyValue);
						// String encryptedApiKeyName = Aes256.encrypt(originalApiKeyName, SECRET_KEY);
						String encryptedApiKeyName = EncryptDcryptService.encrypt(originalApiKeyName, SECRET_KEY);

						log.info("encryptedApiKeyName :: " + encryptedApiKeyName);
						// String encryptedApiKeyValue = Aes256.encrypt(originalApiKeyValue,
						// SECRET_KEY);
						String encryptedApiKeyValue = EncryptDcryptService.encrypt(originalApiKeyValue, SECRET_KEY);

						log.info("encryptedApiKeyValue :: " + encryptedApiKeyValue);
						pipelineInferenceMasterApiKey.setName(encryptedApiKeyName);
						pipelineInferenceMasterApiKey.setValue(encryptedApiKeyValue);
						pipelineModelObj.getInferenceEndPoint().setMasterApiKey(pipelineInferenceMasterApiKey);

					}
				}

			}

			/*
			 * else { pipelineModelObj.setPipelineModelId(checkModel.getPipelineModelId());
			 * pipelineModelObj.setUserId(userId);
			 * pipelineModelObj.setSubmittedOn(Instant.now().toEpochMilli()); if
			 * (pipelineModelObj.getInferenceEndPoint().getMasterApiKey() != null) {
			 * InferenceAPIEndPointMasterApiKey pipelineInferenceMasterApiKey =
			 * pipelineModelObj .getInferenceEndPoint().getMasterApiKey(); if
			 * (pipelineInferenceMasterApiKey.getValue() != null) { log.info("SecretKey :: "
			 * + SECRET_KEY); String originalApiKeyName =
			 * pipelineInferenceMasterApiKey.getName(); log.info("originalApiKeyName :: " +
			 * originalApiKeyName); String originalApiKeyValue =
			 * pipelineInferenceMasterApiKey.getValue(); log.info("originalApiKeyValue :: "
			 * + originalApiKeyValue); String encryptedApiKeyName =
			 * Aes256.encrypt(originalApiKeyName, SECRET_KEY);
			 * log.info("encryptedApiKeyName :: " + encryptedApiKeyName); String
			 * encryptedApiKeyValue = Aes256.encrypt(originalApiKeyValue, SECRET_KEY);
			 * log.info("encryptedApiKeyValue :: " + encryptedApiKeyValue);
			 * pipelineInferenceMasterApiKey.setName(encryptedApiKeyName);
			 * pipelineInferenceMasterApiKey.setValue(encryptedApiKeyValue);
			 * pipelineModelObj.getInferenceEndPoint().setMasterApiKey(
			 * pipelineInferenceMasterApiKey);
			 * 
			 * } }
			 * 
			 * }
			 */
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
						if (ttsInference.getSupportedVoices() != null) {
							List<VoiceTypes> supportedVoices = ttsInference.getSupportedVoices();
							if (!supportedVoices.isEmpty()) {

								inferenceAPIEndPoint = modelInferenceEndPointService
										.validateCallBackUrl(inferenceAPIEndPoint);
							} else {
								throw new ModelValidationException("Supported Voices Type is not available");

							}
						} else {
							throw new ModelValidationException("Supported Voices Type is not available");

						}
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
					// String encryptedName = Aes256.encrypt(originalName, SECRET_KEY);
					String encryptedName = EncryptDcryptService.encrypt(originalName, SECRET_KEY);

					log.info("encryptedName ::" + encryptedName);
					// String encryptedValue = Aes256.encrypt(originalValue, SECRET_KEY);
					String encryptedValue = EncryptDcryptService.encrypt(originalValue, SECRET_KEY);

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
          
		log.info("#############Entry validate model##############");		
		
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

		if (model.getLicense()== null)
			throw new ModelValidationException("license is not available or it is not from supported licence list!");
	
			
			if (model.getLicense() == License.CUSTOM_LICENSE) {
				
				if(model.getLicenseUrl()==null)
					throw new ModelValidationException("custom licenseUrl is required field");

				if (model.getLicenseUrl().isBlank())
					throw new ModelValidationException("custom licenseUrl should not be blank!");
			}
			
			
		

		

		if (model.getDomain() == null) {
			throw new ModelValidationException("domain is required field");
		} else {
		Domain domain=	model.getDomain();
			for (String domainName : domain) {
				    
                      
                      if(DomainEnum.fromValue(domainName)==null) {
                    	  
      					throw new ModelValidationException(domainName + " is not exist in supported domain list !");
 
                      }
                      
                  }
		}
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
		
		log.info("#############Exit validate model##############");		

		return true;
	}

	private Boolean validatePipelineModel(PipelineModel model) throws ModelValidationException {

		if (model.getName() == null || model.getName().isBlank())
			throw new ModelValidationException("name is required field");

		if (model.getVersion() == null || model.getVersion().isBlank())
			throw new ModelValidationException("version is required field");

		if (model.getDescription() == null || model.getDescription().isBlank())
			throw new ModelValidationException("description is required field");

		if (model.getDomain() == null) {
			throw new ModelValidationException("domain is required field");
		} else {
		Domain domain=	model.getDomain();
			for (String domainName : domain) {
				    
                      
                      if(DomainEnum.fromValue(domainName)==null) {
                    	  
      					throw new ModelValidationException(domainName + " is not exist in supported domain list !");
 
                      }
                      
                  }
		}

		if (model.getServiceProvider() == null)
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

	public boolean checkModelAvailablity(PipelineModel pipelineModel) {

		if (pipelineModel.getTaskSpecifications() != null && !pipelineModel.getTaskSpecifications().isEmpty()) {

			for (TaskSpecification taskSpecification : pipelineModel.getTaskSpecifications()) {

				if (taskSpecification.getTaskConfig() != null) {
					SupportedTasks taskType = taskSpecification.getTaskType();
					for (ConfigSchema configSchema : taskSpecification.getTaskConfig()) {

						String modelId = configSchema.getModelId();
						ModelExtended model = modelDao.findByModelId(modelId);
						if (model != null) {
							if (model.getTask().getType().equals(taskType)) {
								boolean flag = false;
								for (LanguagePair languagePair : model.getLanguages()) {

									if (configSchema.getSourceLanguage() != null
											&& configSchema.getTargetLanguage() == null) {

										if (languagePair.getSourceLanguage().equals(configSchema.getSourceLanguage())) {
											flag = true;
											break;
										}
									} else {
										if (languagePair.getSourceLanguage().equals(configSchema.getSourceLanguage())
												&& languagePair.getTargetLanguage()
														.equals(configSchema.getTargetLanguage())) {
											flag = true;
											break;
										}

									}
								}

								if (!flag) {
									if (configSchema.getSourceLanguage() != null
											&& configSchema.getTargetLanguage() == null) {
										throw new ModelValidationException(configSchema.getSourceLanguage()
												+ " language is not supported by model :: " + modelId + " in "
												+ taskType + " task type !");

									} else {

										throw new ModelValidationException(configSchema.getSourceLanguage() + "-"
												+ configSchema.getTargetLanguage()
												+ " language pair is not supported by model :: " + modelId + " in "
												+ taskType + " task type !");

									}

								}

							} else {

								throw new ModelValidationException(
										"Model ID :: " + modelId + " don't have " + taskType + " task type!");

							}

						} else {

							throw new ModelValidationException("Model is not available for modelId :: " + modelId);

						}

					}
				}
			}

		} else {
			throw new ModelValidationException("TaskSpecifications should not be empty or null !");

		}

		return true;
	}

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

		return modelInferenceEndPointService.compute(modelObj, compute);
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

	public ObjectNode getModelsPipeline(String jsonRequest, String userID, String ulcaApiKey) throws Exception {
		// log.info("File :: " + file.toString());
		PipelineRequest pipelineRequestCheckedTaskType = checkTaskType(jsonRequest);

		PipelineRequest pipelineRequest = checkLanguageSequence(pipelineRequestCheckedTaskType);
		log.info("pipelineRequest :: " + pipelineRequest);
		if (pipelineRequest != null) {
			validatePipelineRequest(pipelineRequest);
		} else {
			throw new PipelineValidationException("Pipeline validation failed. Check uploaded file syntax",
					HttpStatus.BAD_REQUEST);
		}

		// Make query and find pipeline model with the submitter name

		/*
		 * Query dynamicQuery1 = new Query(); Criteria modelTypeCriteria1 =
		 * Criteria.where("_class").is("com.ulca.model.dao.PipelineModel");
		 * dynamicQuery1.addCriteria(modelTypeCriteria1); //Criteria submitterCriteria1
		 * = Criteria.where("submitter.name")
		 * //.is(pipelineRequest.getPipelineRequestConfig().getSubmitter()); Criteria
		 * submitterCriteria1 = Criteria.where("serviceProvider.name")
		 * .is(pipelineRequest.getPipelineRequestConfig().getSubmitter());
		 * dynamicQuery1.addCriteria(submitterCriteria1); log.info("dynamicQuery : " +
		 * dynamicQuery1.toString()); PipelineModel pipelineModel =
		 * mongoTemplate.findOne(dynamicQuery1, PipelineModel.class);
		 */
		PipelineModel pipelineModel = pipelineModelDao
				.findByPipelineModelId(pipelineRequest.getPipelineRequestConfig().getPipelineId());

		ArrayList<PipelineTask> pipelineTasks = pipelineRequest.getPipelineTasks();

		// ArrayList<String> pipelineTaskSequence = new ArrayList<String>();

		PipelineResponse pipelineResponse = new PipelineResponse();
		/// Ulca ApiKey Part

		PipelineInferenceAPIEndPoint pipelineInferenceAPIEndPoint = new PipelineInferenceAPIEndPoint();
		pipelineInferenceAPIEndPoint.setCallbackUrl(pipelineModel.getInferenceEndPoint().getCallbackUrl());
		pipelineInferenceAPIEndPoint.setIsSyncApi(pipelineModel.getInferenceEndPoint().isIsSyncApi());
		pipelineInferenceAPIEndPoint
				.setIsMultilingualEnabled(pipelineModel.getInferenceEndPoint().isIsMultilingualEnabled());
		pipelineInferenceAPIEndPoint.setAsyncApiDetails(pipelineModel.getInferenceEndPoint().getAsyncApiDetails());

		// TranslationTaskInferenceInferenceApiKey
		// translationTaskInferenceInferenceApiKey = new
		// TranslationTaskInferenceInferenceApiKey();

		TranslationTaskInferenceInferenceApiKey translationTaskInferenceInferenceApiKey = validateUserDetails(userID,
				ulcaApiKey, pipelineModel.getPipelineModelId());

		/*
		 * String dbUserId = userId;
		 * 
		 * String inferenceApiKeyName1 = "Authorization"; String ulcaApiKey1 =
		 * ulcaapikey1; String inferenceApiKeyValue1 = aessecretkey1;
		 * 
		 * String inferenceApiKeyName2 = "Authorization"; String ulcaApiKey2 =
		 * ulcaapikey2; String inferenceApiKeyValue2 = aessecretkey2;
		 * 
		 * if (userID.equals(dbUserId)) { if (ulcaApiKey.equals(ulcaApiKey1)) {
		 * 
		 * translationTaskInferenceInferenceApiKey.setName(inferenceApiKeyName1);
		 * translationTaskInferenceInferenceApiKey.setValue(inferenceApiKeyValue1);
		 * 
		 * } else if (ulcaApiKey.equals(ulcaApiKey2)) {
		 * 
		 * translationTaskInferenceInferenceApiKey.setName(inferenceApiKeyName2);
		 * translationTaskInferenceInferenceApiKey.setValue(inferenceApiKeyValue2);
		 * 
		 * } else {
		 * 
		 * throw new PipelineValidationException("Ulca Api Key does not exist!",
		 * HttpStatus.BAD_REQUEST);
		 * 
		 * }
		 * 
		 * } else { throw new PipelineValidationException("User Id does not exist!",
		 * HttpStatus.BAD_REQUEST);
		 * 
		 * }
		 */
		pipelineInferenceAPIEndPoint.setInferenceApiKey(translationTaskInferenceInferenceApiKey);

		pipelineResponse.setPipelineInferenceAPIEndPoint(pipelineInferenceAPIEndPoint);

		ArrayList<LanguagesList> languagesArrayList = new ArrayList<LanguagesList>();
		pipelineResponse.getLanguages();
		TaskSpecifications pipelineTaskSpecifications = pipelineModel.getTaskSpecifications();
		boolean first_task = true;

		PipelineTask pipelineTask = pipelineTasks.get(0);

		LanguagesList firstTaskLanguageList = new LanguagesList();
		String task = pipelineTask.getTaskType();
		if (task == "translation") {
			// TranslationTaskInference translationTaskInference = new
			// TranslationTaskInference();
			// translationTaskInference.setTaskType(SupportedTasks.fromValue(task));

			TranslationTask translationTask = (TranslationTask) pipelineTask;
			// If task has languages
			if (translationTask.getConfig() != null && translationTask.getConfig().getLanguage() != null
					&& translationTask.getConfig().getLanguage().getSourceLanguage() != null) {
				TranslationRequestConfig translationRequestConfig = translationTask.getConfig();
				LanguageSchema firstTaskLanguageSchema = new LanguageSchema();
				LanguagePair languagePair = translationRequestConfig.getLanguage();
				if (languagePair.getSourceLanguage() != null) {
					firstTaskLanguageSchema.setSourceLanguage(
							SupportedLanguages.fromValue(languagePair.getSourceLanguage().toString().toLowerCase()));
				}
				if (languagePair.getTargetLanguage() != null) {
					// targetLanguages.clear();
					firstTaskLanguageSchema.addTargetLanguageListItem(
							SupportedLanguages.fromValue(languagePair.getTargetLanguage().toString().toLowerCase()));
				} else {
					for (TaskSpecification firstTaskSpec : pipelineTaskSpecifications) {
						if (firstTaskSpec.getTaskType().toString() == task) {
							io.swagger.pipelinemodel.ConfigList firstTaskLanguages = firstTaskSpec.getTaskConfig();
							for (io.swagger.pipelinemodel.ConfigSchema specLanguageSchema : firstTaskLanguages) {
								if (specLanguageSchema.getSourceLanguage().equals(languagePair.getSourceLanguage())) {

									firstTaskLanguageSchema.addTargetLanguageListItem(SupportedLanguages.fromValue(
											specLanguageSchema.getTargetLanguage().toString().toLowerCase()));

								}

							}
						}
					}

				}
				firstTaskLanguageList.add(firstTaskLanguageSchema);
			}
			// If task doesn't have languages
			else {
				for (TaskSpecification firstTaskSpec : pipelineTaskSpecifications) {
					if (firstTaskSpec.getTaskType().toString() == task) {
						io.swagger.pipelinemodel.ConfigList firstTaskLanguages = firstTaskSpec.getTaskConfig();
						for (io.swagger.pipelinemodel.ConfigSchema specLanguageSchema : firstTaskLanguages) {
							LanguageSchema firstTaskLanguageSchema = new LanguageSchema();
							// Go through each spec within submitted model
							boolean sourceLangExists = false;
							for (LanguageSchema eachStoredFirstTaskSchema : firstTaskLanguageList) {
								// if that source language exists within our stored first task schema
								if (eachStoredFirstTaskSchema.getSourceLanguage() != null && eachStoredFirstTaskSchema
										.getSourceLanguage().equals(specLanguageSchema.getSourceLanguage())) {
									eachStoredFirstTaskSchema
											.addTargetLanguageListItem((specLanguageSchema.getTargetLanguage()));
									sourceLangExists = true;
								}
							}
							if (sourceLangExists == false) {
								firstTaskLanguageSchema.setSourceLanguage(specLanguageSchema.getSourceLanguage());
								firstTaskLanguageSchema
										.addTargetLanguageListItem((specLanguageSchema.getTargetLanguage()));
								firstTaskLanguageList.add(firstTaskLanguageSchema);
							}
						}
					}
				}
			}

		} else if (task == "asr") {
			// TranslationTaskInference translationTaskInference = new
			// TranslationTaskInference();
			// translationTaskInference.setTaskType(SupportedTasks.fromValue(task));

			ASRTask asrTask = (ASRTask) pipelineTask;
			// If task has languages
			if (asrTask.getConfig() != null && asrTask.getConfig().getLanguage() != null
					&& asrTask.getConfig().getLanguage().getSourceLanguage() != null) {
				ASRRequestConfig asrRequestConfig = asrTask.getConfig();
				LanguageSchema firstTaskLanguageSchema = new LanguageSchema();
				LanguagePair languagePair = asrRequestConfig.getLanguage();
				{
					log.info("CODE HERE");
					firstTaskLanguageSchema.setSourceLanguage(
							SupportedLanguages.fromValue(languagePair.getSourceLanguage().toString().toLowerCase()));
				}
				firstTaskLanguageSchema.addTargetLanguageListItem(
						SupportedLanguages.fromValue(languagePair.getSourceLanguage().toString().toLowerCase()));

				firstTaskLanguageList.add(firstTaskLanguageSchema);
			}
			// If task doesn't have languages
			else {
				for (TaskSpecification firstTaskSpec : pipelineTaskSpecifications) {
					if (firstTaskSpec.getTaskType().toString() == task) {
						io.swagger.pipelinemodel.ConfigList firstTaskLanguages = firstTaskSpec.getTaskConfig();
						for (io.swagger.pipelinemodel.ConfigSchema specLanguageSchema : firstTaskLanguages) {
							LanguageSchema firstTaskLanguageSchema = new LanguageSchema();
							// Go through each spec within submitted model
							boolean sourceLangExists = false;
							for (LanguageSchema eachStoredFirstTaskSchema : firstTaskLanguageList) {
								// if that source language exists within our stored first task schema
								if (eachStoredFirstTaskSchema.getSourceLanguage() != null && eachStoredFirstTaskSchema
										.getSourceLanguage().equals(specLanguageSchema.getSourceLanguage())) {
									eachStoredFirstTaskSchema
											.addTargetLanguageListItem((specLanguageSchema.getSourceLanguage()));
									sourceLangExists = true;
								}
							}
							if (sourceLangExists == false) {
								firstTaskLanguageSchema.setSourceLanguage(specLanguageSchema.getSourceLanguage());
								firstTaskLanguageSchema
										.addTargetLanguageListItem((specLanguageSchema.getSourceLanguage()));
								firstTaskLanguageList.add(firstTaskLanguageSchema);
							}
						}
					}
				}
			}

		} else if (task == "tts") {
			// TranslationTaskInference translationTaskInference = new
			// TranslationTaskInference();
			// translationTaskInference.setTaskType(SupportedTasks.fromValue(task));

			TTSTask ttsTask = (TTSTask) pipelineTask;
			// If task has languages
			if (ttsTask.getConfig() != null && ttsTask.getConfig().getLanguage() != null
					&& ttsTask.getConfig().getLanguage().getSourceLanguage() != null) {
				TTSRequestConfig ttsRequestTask = ttsTask.getConfig();
				LanguageSchema firstTaskLanguageSchema = new LanguageSchema();
				LanguagePair languagePair = ttsRequestTask.getLanguage();
				{
					firstTaskLanguageSchema.setSourceLanguage(
							SupportedLanguages.fromValue(languagePair.getSourceLanguage().toString().toLowerCase()));
				}
				// targetLanguages.clear();
				firstTaskLanguageSchema.addTargetLanguageListItem(
						SupportedLanguages.fromValue(languagePair.getSourceLanguage().toString().toLowerCase()));

				firstTaskLanguageList.add(firstTaskLanguageSchema);
			}
			// If task doesn't have languages
			else {
				for (TaskSpecification firstTaskSpec : pipelineTaskSpecifications) {
					if (firstTaskSpec.getTaskType().toString() == task) {
						io.swagger.pipelinemodel.ConfigList firstTaskLanguages = firstTaskSpec.getTaskConfig();
						for (io.swagger.pipelinemodel.ConfigSchema specLanguageSchema : firstTaskLanguages) {
							LanguageSchema firstTaskLanguageSchema = new LanguageSchema();
							// Go through each spec within submitted model
							boolean sourceLangExists = false;
							for (LanguageSchema eachStoredFirstTaskSchema : firstTaskLanguageList) {
								// if that source language exists within our stored first task schema
								if (eachStoredFirstTaskSchema.getSourceLanguage() != null && eachStoredFirstTaskSchema
										.getSourceLanguage().equals(specLanguageSchema.getSourceLanguage())) {
									eachStoredFirstTaskSchema
											.addTargetLanguageListItem((specLanguageSchema.getSourceLanguage()));
									sourceLangExists = true;
								}
							}
							if (sourceLangExists == false) {
								firstTaskLanguageSchema.setSourceLanguage(specLanguageSchema.getSourceLanguage());
								firstTaskLanguageSchema
										.addTargetLanguageListItem((specLanguageSchema.getSourceLanguage()));
								firstTaskLanguageList.add(firstTaskLanguageSchema);
							}
						}
					}
				}
			}
		} else {
			throw new PipelineValidationException("TaskType is not valid!", HttpStatus.BAD_REQUEST);

		}

		languagesArrayList.add(firstTaskLanguageList);

		// Do the same task for remaining languages.
		// firstTaskLanguage list consists of all language conbinations of first task.:

		int taskLength = pipelineTasks.size();
		if (taskLength > 1) {
			for (int i = 1; i < taskLength; i++) // For each task from 2nd task
			{
				PipelineTask currentTask = pipelineTasks.get(i);
				LanguagesList currentTaskLangList = new LanguagesList();
				String currentTaskType = currentTask.getTaskType();
				LanguagesList previousTaskLangList = languagesArrayList.get(i - 1); // get last task's languages
				if (currentTaskType == "translation") {
					for (LanguageSchema previousSchema : previousTaskLangList) // For each previous task's schema
					{
						List<SupportedLanguages> previousTargetLanguageList = previousSchema.getTargetLanguageList();
						for (SupportedLanguages previousTargetLanguage : previousTargetLanguageList) // For each target
																										// language in
																										// previous
																										// task's schema
						{
							LanguageSchema currentTaskLanguageSchema = new LanguageSchema();
							// Go through each spec within submitted model

							TaskSpecifications taskSpecificiations = pipelineModel.getTaskSpecifications();
							for (TaskSpecification curTaskSpec : taskSpecificiations) {
								if (curTaskSpec.getTaskType().toString() == currentTaskType) {
									io.swagger.pipelinemodel.ConfigList curTaskLanguages = curTaskSpec.getTaskConfig();
									for (io.swagger.pipelinemodel.ConfigSchema specLanguageSchema : curTaskLanguages) {
										if (specLanguageSchema.getSourceLanguage() == previousTargetLanguage) {
											boolean sourceLanguageExists = false;
											for (LanguageSchema eachSchema : currentTaskLangList) {
												// if previous target language of prev. task already exists as a source
												// language in cur. task
												if (eachSchema.getSourceLanguage() != null && eachSchema
														.getSourceLanguage().equals(previousTargetLanguage)) {
													sourceLanguageExists = true;
													break;
												}
											}
											if (sourceLanguageExists == false) {
												currentTaskLanguageSchema.setSourceLanguage(previousTargetLanguage);
												// Add all target languages where source Language is pa

												for (io.swagger.pipelinemodel.ConfigSchema specLangSchema : curTaskLanguages) {
													// TODO: This is if there are no target languages in the current
													// source language for translation

													if (specLangSchema.getSourceLanguage()
															.equals(previousTargetLanguage)) {
														TranslationTask translationTask = (TranslationTask) pipelineTasks
																.get(i);
														TranslationRequestConfig translationConfig = translationTask
																.getConfig();
														SupportedLanguages currentTaskTarget;
														if (translationConfig != null
																&& translationConfig.getLanguage() != null
																&& translationConfig.getLanguage()
																		.getTargetLanguage() != null) {
															currentTaskTarget = translationConfig.getLanguage()
																	.getTargetLanguage();
														} else {
															currentTaskTarget = specLangSchema.getTargetLanguage();
														}
														if (currentTaskLanguageSchema.getTargetLanguageList() == null)
															currentTaskLanguageSchema
																	.addTargetLanguageListItem(currentTaskTarget);
														else if (!currentTaskLanguageSchema.getTargetLanguageList()
																.contains(currentTaskTarget))
															currentTaskLanguageSchema
																	.addTargetLanguageListItem(currentTaskTarget);
													}
												}
												currentTaskLangList.add(currentTaskLanguageSchema);
											}
										}
									}
								}
							}
						}
					}
					languagesArrayList.add(currentTaskLangList);
				}
				if (currentTaskType == "asr") {
					for (LanguageSchema previousSchema : previousTaskLangList) // For each previous task's schema
					{
						List<SupportedLanguages> previousTargetLanguageList = previousSchema.getTargetLanguageList();
						for (SupportedLanguages previousTargetLanguage : previousTargetLanguageList) // For each target
																										// language in
																										// previous
																										// task's schema
						{
							LanguageSchema currentTaskLanguageSchema = new LanguageSchema();
							// Go through each spec within submitted model

							TaskSpecifications taskSpecificiations = pipelineModel.getTaskSpecifications();
							for (TaskSpecification curTaskSpec : taskSpecificiations) {
								if (curTaskSpec.getTaskType().toString() == currentTaskType) {
									io.swagger.pipelinemodel.ConfigList curTaskLanguages = curTaskSpec.getTaskConfig();
									for (io.swagger.pipelinemodel.ConfigSchema specLanguageSchema : curTaskLanguages) {
										if (specLanguageSchema.getSourceLanguage() == previousTargetLanguage) {
											boolean sourceLanguageExists = false;
											for (LanguageSchema eachSchema : currentTaskLangList) {
												// if previous target language of prev. task already exists as a source
												// language in cur. task
												if (eachSchema.getSourceLanguage() != null && eachSchema
														.getSourceLanguage().equals(previousTargetLanguage)) {
													sourceLanguageExists = true;
													break;
												}
											}
											if (sourceLanguageExists == false) {
												currentTaskLanguageSchema.setSourceLanguage(previousTargetLanguage);
												// Add all target languages where source Language is pa
												currentTaskLanguageSchema.addTargetLanguageListItem(
														specLanguageSchema.getSourceLanguage());
												// specLanguageSchema
												// for(io.swagger.pipelinemodel.LanguageSchema specLangSchema :
												// curTaskLanguages)
												// {
												// if(specLangSchema.getSourceLanguage().equals(previousTargetLanguage))
												// currentTaskLanguageSchema.addTargetLanguageListItem(specLangSchema.getTargetLanguage());
												// }
												currentTaskLangList.add(currentTaskLanguageSchema);
											}
										}
									}
								}
							}
						}
					}
					languagesArrayList.add(currentTaskLangList);
				}
				if (currentTaskType == "tts") {
					for (LanguageSchema previousSchema : previousTaskLangList) // For each previous task's schema
					{
						List<SupportedLanguages> previousTargetLanguageList = previousSchema.getTargetLanguageList();
						for (SupportedLanguages previousTargetLanguage : previousTargetLanguageList) // For each target
																										// language in
																										// previous
																										// task's schema
						{
							LanguageSchema currentTaskLanguageSchema = new LanguageSchema();
							// Go through each spec within submitted model

							TaskSpecifications taskSpecificiations = pipelineModel.getTaskSpecifications();
							for (TaskSpecification curTaskSpec : taskSpecificiations) {
								if (curTaskSpec.getTaskType().toString() == currentTaskType) {
									io.swagger.pipelinemodel.ConfigList curTaskLanguages = curTaskSpec.getTaskConfig();
									for (io.swagger.pipelinemodel.ConfigSchema specLanguageSchema : curTaskLanguages) {
										if (specLanguageSchema.getSourceLanguage() == previousTargetLanguage) {
											boolean sourceLanguageExists = false;
											for (LanguageSchema eachSchema : currentTaskLangList) {
												// if previous target language of prev. task already exists as a source
												// language in cur. task
												if (eachSchema.getSourceLanguage() != null && eachSchema
														.getSourceLanguage().equals(previousTargetLanguage)) {
													sourceLanguageExists = true;
													break;
												}
											}
											if (sourceLanguageExists == false) {
												currentTaskLanguageSchema.setSourceLanguage(previousTargetLanguage);
												// Add all target languages where source Language is pa
												currentTaskLanguageSchema.addTargetLanguageListItem(
														specLanguageSchema.getSourceLanguage());
												// specLanguageSchema
												// for(io.swagger.pipelinemodel.LanguageSchema specLangSchema :
												// curTaskLanguages)
												// {
												// if(specLangSchema.getSourceLanguage().equals(previousTargetLanguage))
												// currentTaskLanguageSchema.addTargetLanguageListItem(specLangSchema.getTargetLanguage());
												// }
												currentTaskLangList.add(currentTaskLanguageSchema);
											}
										}
									}
								}
							}
						}
					}
					languagesArrayList.add(currentTaskLangList);
				}
			}

		}

		log.info("FIRST LANGUAGE LIST :: " + languagesArrayList);

		ASRTaskInference asrInference = new ASRTaskInference();
		TTSTaskInference ttsInference = new TTSTaskInference();
		TranslationTaskInference translationInference = new TranslationTaskInference();

		// TODO
		// Go through each first sourceLanguage in languagesArrayList and get a
		// LanguageList element and update
		LanguagesList newLanguageList = new LanguagesList();
		// newLanguageList.clear();
		TaskSchemaList newPipelineResponseConfig = new TaskSchemaList();
		// newPipelineResponseConfig.clear();
		for (LanguageSchema firstTaskSchema : languagesArrayList.get(0)) {
			log.info("FIRST Task Source Languages ::" + firstTaskSchema.getSourceLanguage());
			LanguageSchema pipelineSchema = new LanguageSchema();
			pipelineSchema.setSourceLanguage(firstTaskSchema.getSourceLanguage());
			List<SupportedLanguages> targetLangList = new ArrayList<SupportedLanguages>();
			List<SupportedLanguages> targetLangListCopy = new ArrayList<SupportedLanguages>();
			if (firstTaskSchema.getTargetLanguageList() != null) {
				log.info("FIRST Task Target Languages :: " + firstTaskSchema.getTargetLanguageList());

				for (SupportedLanguages sl : firstTaskSchema.getTargetLanguageList()) {
					targetLangList.add(sl);
					targetLangListCopy.add(sl);
				}

			}
			// DFS for targetLangList creation [Final Pipeline Target Lang List]
			int targetLangSize = targetLangList.size();
			int targetLangSizeCopy = targetLangSize;
			int currentTaskIndex = 1;
			while (currentTaskIndex < taskLength) {
				for (int i = 0; i < targetLangSize; i++) {
					SupportedLanguages targetLanguage = targetLangList.get(0); // refers to prev. target language task
																				// list
					targetLangList.remove(0);
					LanguagesList currentLangList = languagesArrayList.get(currentTaskIndex);
					for (LanguageSchema curLangSchema : currentLangList) {
						LanguageSchema tempLanguageSchema = new LanguageSchema();
						if (targetLanguage.equals(curLangSchema.getSourceLanguage())) {
							if (curLangSchema.getTargetLanguageList() != null) {
								for (SupportedLanguages targLang : curLangSchema.getTargetLanguageList()) {
									targetLangList.add(targLang);
								}
							} else
								targetLangList.add(curLangSchema.getSourceLanguage());
						}
						tempLanguageSchema.setSourceLanguage(curLangSchema.getSourceLanguage());
						tempLanguageSchema.setTargetLanguageList(curLangSchema.getTargetLanguageList());
					}
				}
				targetLangSize = targetLangList.size();
				currentTaskIndex++;
			}
			pipelineSchema.setTargetLanguageList(targetLangList);
			boolean srcLangExists = false;
			for (LanguageSchema each_schema : newLanguageList) {
				if (each_schema.getSourceLanguage().equals(pipelineSchema.getSourceLanguage())) {
					srcLangExists = true;
					break;
				}
			}
			if (srcLangExists == false && pipelineSchema.getTargetLanguageList().size() != 0) {
				// log.info("MODEL BUILD LIST :: "+modelBuildList);
				newLanguageList.add(pipelineSchema);
				currentTaskIndex = 1;

				// TODO: ADD Query for First Task as well

				String firstTaskType = pipelineTasks.get(0).getTaskType();
				if (firstTaskType == "asr") {
					// Do Mongo Query for targetLangListCopy
					Boolean modelExists = false;
					for (ASRResponseConfig each_task : asrInference.getConfig()) {
						if (each_task.getLanguage() != null) {
							if (each_task.getLanguage().getSourceLanguage()
									.equals(firstTaskSchema.getSourceLanguage())) {
								modelExists = true;
								break;
							}
						}
					}
					if (modelExists == false) {

						// TODO 24-03-2023: Search based on model id alone
						/*
						 * Query dynamicQuery = new Query();
						 * 
						 * Criteria modelTypeCriteria =
						 * Criteria.where("_class").is("com.ulca.model.dao.ModelExtended");
						 * dynamicQuery.addCriteria(modelTypeCriteria);
						 * 
						 * Criteria submitterCriteria = Criteria.where("submitter.name")
						 * .is(pipelineRequest.getPipelineRequestConfig().getSubmitter());
						 * dynamicQuery.addCriteria(submitterCriteria);
						 * 
						 * Criteria taskCriteria = Criteria.where("task.type").is("ASR");
						 * dynamicQuery.addCriteria(taskCriteria);
						 * 
						 * Criteria languageCriteria = Criteria.where("languages.sourceLanguage")
						 * .is(firstTaskSchema.getSourceLanguage());
						 * dynamicQuery.addCriteria(languageCriteria);
						 * dynamicQuery.with(Sort.by(Sort.Direction.DESC, "submittedOn"));
						 * 
						 * log.info("dynamicQuery in ASR task search ::" + dynamicQuery.toString());
						 * 
						 * List<ModelExtended> asrModels = mongoTemplate.find(dynamicQuery,
						 * ModelExtended.class);
						 * 
						 * log.info("MODEL : " + asrModels.get(0));
						 */

						ASRResponseConfig asrResponseConfig = new ASRResponseConfig();

						for (TaskSpecification taskSpecification : pipelineTaskSpecifications) {
							if (taskSpecification.getTaskType().name().equals("ASR")) {

								for (ConfigSchema configSchema : taskSpecification.getTaskConfig()) {
									if (configSchema.getSourceLanguage().equals(firstTaskSchema.getSourceLanguage())) {

										ModelExtended model = modelDao.findByModelId(configSchema.getModelId());
										log.info("Model Name :: " + model.getName());
										LanguagePairs langPair = model.getLanguages();
										for (LanguagePair lp : langPair) {
											// TODO 24-03-2023: Set the service ID and model ID of that model
											asrResponseConfig.setLanguage(lp);

										}
										asrResponseConfig.setServiceId(configSchema.getServiceId());
										asrResponseConfig.setModelId(configSchema.getModelId());
										asrResponseConfig.setDomain(model.getDomain());
									}

								}
							}

						}

						asrInference.addConfigItem(asrResponseConfig);

						/*
						 * ASRResponseConfig asrResponseConfig = new ASRResponseConfig();
						 * log.info("Model Name :: " + asrModels.get(0).getName()); LanguagePairs
						 * langPair = asrModels.get(0).getLanguages(); for (LanguagePair lp : langPair)
						 * { //TODO 24-03-2023: Set the service ID and model ID of that model
						 * asrResponseConfig.setServiceId(""); asrResponseConfig.setLanguage(lp); } //
						 * asrResponseConfig.setDomain(asrModels.get(0).getDomain());
						 */

					}
				}

				else if (firstTaskType == "translation") {

					// Do Mongo Query for targetLangListCopy
					Boolean modelExists = false;
					for (TranslationResponseConfig each_task : translationInference.getConfig()) {
						if (each_task.getLanguage().getSourceLanguage().equals(firstTaskSchema.getSourceLanguage())) {
							modelExists = true;
							break;
						}
					}
					if (modelExists == false) {
						for (SupportedLanguages targetLang : firstTaskSchema.getTargetLanguageList()) {

							/*
							 * Query dynamicQuery = new Query();
							 * 
							 * Criteria modelTypeCriteria = Criteria.where("_class")
							 * .is("com.ulca.model.dao.ModelExtended");
							 * dynamicQuery.addCriteria(modelTypeCriteria);
							 * 
							 * Criteria submitterCriteria = Criteria.where("submitter.name")
							 * .is(pipelineRequest.getPipelineRequestConfig().getSubmitter());
							 * dynamicQuery.addCriteria(submitterCriteria);
							 * 
							 * Criteria taskCriteria = Criteria.where("task.type").is("TRANSLATION");
							 * dynamicQuery.addCriteria(taskCriteria);
							 * 
							 * Criteria languageCriteria = Criteria.where("languages.sourceLanguage")
							 * .is(firstTaskSchema.getSourceLanguage());
							 * dynamicQuery.addCriteria(languageCriteria);
							 * dynamicQuery.with(Sort.by(Sort.Direction.DESC, "submittedOn"));
							 * 
							 * log.info("dynamicQuery in ASR task search ::" + dynamicQuery.toString());
							 * 
							 * Criteria targetLanguageCriteria =
							 * Criteria.where("languages.targetLanguage").is(targetLang);
							 * dynamicQuery.addCriteria(targetLanguageCriteria);
							 * 
							 * List<ModelExtended> translationModels = mongoTemplate.find(dynamicQuery,
							 * ModelExtended.class);
							 * 
							 * if (translationModels.size() > 0) { TranslationResponseConfig
							 * translationResponseConfig = new TranslationResponseConfig();
							 * log.info("Model Name :: " + translationModels.get(0).getName());
							 * LanguagePairs langPair = translationModels.get(0).getLanguages(); for
							 * (LanguagePair lp : langPair) { translationResponseConfig.setServiceId("");
							 * translationResponseConfig.setLanguage(lp); } // TODO: Read each model and
							 * store the results in PipelineResponseConfig //
							 * translationResponseConfig.setDomain(translationModels.get(0).getDomain());
							 * 
							 * translationInference.addConfigItem(translationResponseConfig);
							 * 
							 * }
							 */

							TranslationResponseConfig translationResponseConfig = new TranslationResponseConfig();
							for (TaskSpecification taskSpecification : pipelineTaskSpecifications) {

								if (taskSpecification.getTaskType().name().equals("TRANSLATION")) {

									for (ConfigSchema configSchema : taskSpecification.getTaskConfig()) {
										if (configSchema.getSourceLanguage().equals(firstTaskSchema.getSourceLanguage())
												&& configSchema.getTargetLanguage().equals(targetLang)) {

											ModelExtended model = modelDao.findByModelId(configSchema.getModelId());
											log.info("Model Name :: " + model.getName());
											LanguagePairs langPair = model.getLanguages();
											for (LanguagePair lp : langPair) {
												// TODO 24-03-2023: Set the service ID and model ID of that model
												translationResponseConfig.setLanguage(lp);

											}
											translationResponseConfig.setServiceId(configSchema.getServiceId());
											translationResponseConfig.setModelId(configSchema.getModelId());

										}

									}
								}

							}

							translationInference.addConfigItem(translationResponseConfig);

						}

					}

				}

				else if (firstTaskType == "tts") {
					// Do Mongo Query for targetLangListCopy
					// Do Mongo Query for targetLangListCopy
					Boolean modelExists = false;
					for (TTSResponseConfig each_task : ttsInference.getConfig()) {
						if (each_task.getLanguage().getSourceLanguage().equals(firstTaskSchema.getSourceLanguage())) {
							modelExists = true;
							break;
						}
					}
					if (modelExists == false) {
						/*
						 * Query dynamicQuery = new Query();
						 * 
						 * Criteria modelTypeCriteria =
						 * Criteria.where("_class").is("com.ulca.model.dao.ModelExtended");
						 * dynamicQuery.addCriteria(modelTypeCriteria);
						 * 
						 * Criteria submitterCriteria = Criteria.where("submitter.name")
						 * .is(pipelineRequest.getPipelineRequestConfig().getSubmitter());
						 * dynamicQuery.addCriteria(submitterCriteria);
						 * 
						 * Criteria taskCriteria = Criteria.where("task.type").is("TTS");
						 * dynamicQuery.addCriteria(taskCriteria);
						 * 
						 * Criteria languageCriteria = Criteria.where("languages.sourceLanguage")
						 * .is(firstTaskSchema.getSourceLanguage());
						 * dynamicQuery.addCriteria(languageCriteria);
						 * dynamicQuery.with(Sort.by(Sort.Direction.DESC, "submittedOn"));
						 * 
						 * log.info("dynamicQuery in ASR task search ::" + dynamicQuery.toString());
						 * 
						 * List<ModelExtended> ttsModels = mongoTemplate.find(dynamicQuery,
						 * ModelExtended.class);
						 * 
						 * log.info("MODEL : " + ttsModels.get(0));
						 * 
						 * TTSResponseConfig ttsResponseConfig = new TTSResponseConfig();
						 * log.info("Model Name :: " + ttsModels.get(0).getName()); LanguagePairs
						 * langPair = ttsModels.get(0).getLanguages(); for (LanguagePair lp : langPair)
						 * { ttsResponseConfig.setServiceId(""); ttsResponseConfig.setLanguage(lp);
						 * //TODO 24-03-2023: Set supportedVoices } // TODO: Read each model and store
						 * the results in PipelineResponseConfig //
						 * ttsResponseConfig.setDomain(ttsModels.get(0).getDomain());
						 * 
						 * ttsInference.addConfigItem(ttsResponseConfig);
						 */

						TTSResponseConfig ttsResponseConfig = new TTSResponseConfig();

						for (TaskSpecification taskSpecification : pipelineTaskSpecifications) {

							if (taskSpecification.getTaskType().name().equals("TTS")) {

								for (ConfigSchema configSchema : taskSpecification.getTaskConfig()) {
									if (configSchema.getSourceLanguage().equals(firstTaskSchema.getSourceLanguage())) {

										ModelExtended model = modelDao.findByModelId(configSchema.getModelId());
										log.info("Model Name :: " + model.getName());
										LanguagePairs langPair = model.getLanguages();
										for (LanguagePair lp : langPair) {
											// TODO 24-03-2023: Set the service ID and model ID of that model
											ttsResponseConfig.setLanguage(lp);
										}
										ttsResponseConfig.setServiceId(configSchema.getServiceId());
										ttsResponseConfig.setModelId(configSchema.getModelId());
										TTSInference tTSInference = (TTSInference) model.getInferenceEndPoint()
												.getSchema();
										ttsResponseConfig.setSupportedVoices(tTSInference.getSupportedVoices());
									}

								}
							}

						}

						ttsInference.addConfigItem(ttsResponseConfig);

					}
				}

				while (currentTaskIndex < taskLength) {
					for (int i = 0; i < targetLangSizeCopy; i++) {
						SupportedLanguages sourceLang = targetLangListCopy.get(0);
						log.info("CURRENT TASK " + currentTaskIndex + " SOURCE LANGUAGE :: "
								+ targetLangListCopy.get(0));
						SupportedLanguages targetLanguage = targetLangListCopy.get(0); // refers to prev. target
																						// language task list
						targetLangListCopy.remove(0);
						LanguagesList currentLangList = languagesArrayList.get(currentTaskIndex);
						for (LanguageSchema curLangSchema : currentLangList) {
							LanguageSchema tempLanguageSchema = new LanguageSchema();
							if (targetLanguage.equals(curLangSchema.getSourceLanguage())) {
								if (curLangSchema.getTargetLanguageList() != null) {
									for (SupportedLanguages targLang : curLangSchema.getTargetLanguageList()) {
										targetLangListCopy.add(targLang);
									}
								} else
									targetLangListCopy.add(curLangSchema.getSourceLanguage());
							}
							tempLanguageSchema.setSourceLanguage(curLangSchema.getSourceLanguage());
							tempLanguageSchema.setTargetLanguageList(curLangSchema.getTargetLanguageList());
						}
						log.info("CURRENT TASK " + pipelineTasks.get(currentTaskIndex).getTaskType() + " "
								+ currentTaskIndex + " TARGET LANGUAGE :: " + targetLangListCopy);
						List<SupportedLanguages> targetLanguageList = targetLangListCopy;
						// Add pipelineModels by searching within Mongo via here.

						// FOR EACH TASK TYPE: CALL MONGO QUERIES FOR SOURCELANG AND TARGETLANGUAGELIST

						// Add TranslationTaskInference, ASRTaskInference and TTSTaskInference within
						// newPipelineResponseConfig
						String currentTaskType = pipelineTasks.get(currentTaskIndex).getTaskType();
						if (currentTaskType == "asr") {
							// Do Mongo Query for targetLangListCopy
							Boolean modelExists = false;
							for (ASRResponseConfig each_task : asrInference.getConfig()) {
								if (each_task.getLanguage().getSourceLanguage().equals(sourceLang)) {
									modelExists = true;
									break;
								}
							}
							if (modelExists == false) {

								/*
								 * Query dynamicQuery = new Query();
								 * 
								 * Criteria modelTypeCriteria = Criteria.where("_class")
								 * .is("com.ulca.model.dao.ModelExtended");
								 * dynamicQuery.addCriteria(modelTypeCriteria);
								 * 
								 * Criteria submitterCriteria = Criteria.where("submitter.name")
								 * .is(pipelineRequest.getPipelineRequestConfig().getSubmitter());
								 * dynamicQuery.addCriteria(submitterCriteria);
								 * 
								 * Criteria taskCriteria = Criteria.where("task.type").is("ASR");
								 * dynamicQuery.addCriteria(taskCriteria);
								 * 
								 * Criteria languageCriteria =
								 * Criteria.where("languages.sourceLanguage").is(sourceLang);
								 * dynamicQuery.addCriteria(languageCriteria);
								 * dynamicQuery.with(Sort.by(Sort.Direction.DESC, "submittedOn"));
								 * 
								 * log.info("dynamicQuery in ASR task search ::" + dynamicQuery.toString());
								 * 
								 * List<ModelExtended> asrModels = mongoTemplate.find(dynamicQuery,
								 * ModelExtended.class);
								 * 
								 * log.info("MODEL : " + asrModels.get(0));
								 * 
								 * ASRResponseConfig asrResponseConfig = new ASRResponseConfig();
								 * log.info("Model Name :: " + asrModels.get(0).getName()); LanguagePairs
								 * langPair = asrModels.get(0).getLanguages(); for (LanguagePair lp : langPair)
								 * { asrResponseConfig.setServiceId(""); asrResponseConfig.setLanguage(lp); } //
								 * TODO: Read each model and store the results in PipelineResponseConfig
								 * asrResponseConfig.setDomain(asrModels.get(0).getDomain());
								 * 
								 * asrInference.addConfigItem(asrResponseConfig);
								 */

								ASRResponseConfig asrResponseConfig = new ASRResponseConfig();

								for (TaskSpecification taskSpecification : pipelineTaskSpecifications) {

									if (taskSpecification.getTaskType().name().equals("ASR")) {

										for (ConfigSchema configSchema : taskSpecification.getTaskConfig()) {
											if (configSchema.getSourceLanguage()
													.equals(firstTaskSchema.getSourceLanguage())) {

												ModelExtended model = modelDao.findByModelId(configSchema.getModelId());
												log.info("Model Name :: " + model.getName());
												LanguagePairs langPair = model.getLanguages();
												for (LanguagePair lp : langPair) {
													// TODO 24-03-2023: Set the service ID and model ID of that model
													asrResponseConfig.setLanguage(lp);

												}
												asrResponseConfig.setServiceId(configSchema.getServiceId());
												asrResponseConfig.setModelId(configSchema.getModelId());
												asrResponseConfig.setDomain(model.getDomain());
											}

										}
									}

								}

								asrInference.addConfigItem(asrResponseConfig);

							}
						}

						else if (currentTaskType == "translation") {

							// Do Mongo Query for targetLangListCopy
							Boolean modelExists = false;
							for (TranslationResponseConfig each_task : translationInference.getConfig()) {
								if (each_task.getLanguage() != null) {
									if (each_task.getLanguage().getSourceLanguage().equals(sourceLang)) {
										modelExists = true;
										break;
									}
								}
							}
							if (modelExists == false) {
								for (SupportedLanguages targetLang : targetLanguageList) {
									/*
									 * Query dynamicQuery = new Query();
									 * 
									 * Criteria modelTypeCriteria = Criteria.where("_class")
									 * .is("com.ulca.model.dao.ModelExtended");
									 * dynamicQuery.addCriteria(modelTypeCriteria);
									 * 
									 * Criteria submitterCriteria = Criteria.where("submitter.name")
									 * .is(pipelineRequest.getPipelineRequestConfig().getSubmitter());
									 * dynamicQuery.addCriteria(submitterCriteria);
									 * 
									 * Criteria taskCriteria = Criteria.where("task.type").is("TRANSLATION");
									 * dynamicQuery.addCriteria(taskCriteria);
									 * 
									 * Criteria languageCriteria = Criteria.where("languages.sourceLanguage")
									 * .is(sourceLang); dynamicQuery.addCriteria(languageCriteria);
									 * dynamicQuery.with(Sort.by(Sort.Direction.DESC, "submittedOn"));
									 * 
									 * log.info("dynamicQuery in ASR task search ::" + dynamicQuery.toString());
									 * 
									 * Criteria targetLanguageCriteria = Criteria.where("languages.targetLanguage")
									 * .is(targetLang); dynamicQuery.addCriteria(targetLanguageCriteria);
									 * 
									 * List<ModelExtended> translationModels = mongoTemplate.find(dynamicQuery,
									 * ModelExtended.class);
									 * 
									 * if (translationModels.size() > 0) { TranslationResponseConfig
									 * translationResponseConfig = new TranslationResponseConfig();
									 * log.info("Model Name :: " + translationModels.get(0).getName());
									 * LanguagePairs langPair = translationModels.get(0).getLanguages(); for
									 * (LanguagePair lp : langPair) { translationResponseConfig.setServiceId("");
									 * translationResponseConfig.setLanguage(lp); } // TODO: Read each model and
									 * store the results in PipelineResponseConfig //
									 * translationResponseConfig.setDomain(translationModels.get(0).getDomain());
									 * 
									 * translationInference.addConfigItem(translationResponseConfig);
									 * 
									 * }
									 */

									TranslationResponseConfig translationResponseConfig = new TranslationResponseConfig();
									for (TaskSpecification taskSpecification : pipelineTaskSpecifications) {

										if (taskSpecification.getTaskType().name().equals("TRANSLATION")) {

											for (ConfigSchema configSchema : taskSpecification.getTaskConfig()) {
												if (configSchema.getSourceLanguage()
														.equals(firstTaskSchema.getSourceLanguage())
														&& configSchema.getTargetLanguage().equals(targetLang)) {

													ModelExtended model = modelDao
															.findByModelId(configSchema.getModelId());
													log.info("Model Name :: " + model.getName());
													LanguagePairs langPair = model.getLanguages();
													for (LanguagePair lp : langPair) {
														// TODO 24-03-2023: Set the service ID and model ID of that
														// model
														translationResponseConfig.setLanguage(lp);

													}
													translationResponseConfig.setServiceId(configSchema.getServiceId());
													translationResponseConfig.setModelId(configSchema.getModelId());

												}

											}
										}

									}

									translationInference.addConfigItem(translationResponseConfig);

								}

							}

						}

						else if (currentTaskType == "tts") {
							// Do Mongo Query for targetLangListCopy
							// Do Mongo Query for targetLangListCopy
							Boolean modelExists = false;
							for (TTSResponseConfig each_task : ttsInference.getConfig()) {
								if (each_task.getLanguage().getSourceLanguage().equals(sourceLang)) {
									modelExists = true;
									break;
								}
							}
							if (modelExists == false) {

								/*
								 * Query dynamicQuery = new Query();
								 * 
								 * Criteria modelTypeCriteria = Criteria.where("_class")
								 * .is("com.ulca.model.dao.ModelExtended");
								 * dynamicQuery.addCriteria(modelTypeCriteria);
								 * 
								 * Criteria submitterCriteria = Criteria.where("submitter.name")
								 * .is(pipelineRequest.getPipelineRequestConfig().getSubmitter());
								 * dynamicQuery.addCriteria(submitterCriteria);
								 * 
								 * Criteria taskCriteria = Criteria.where("task.type").is("TTS");
								 * dynamicQuery.addCriteria(taskCriteria);
								 * 
								 * Criteria languageCriteria =
								 * Criteria.where("languages.sourceLanguage").is(sourceLang);
								 * dynamicQuery.addCriteria(languageCriteria);
								 * dynamicQuery.with(Sort.by(Sort.Direction.DESC, "submittedOn"));
								 * 
								 * log.info("dynamicQuery in ASR task search ::" + dynamicQuery.toString());
								 * 
								 * List<ModelExtended> ttsModels = mongoTemplate.find(dynamicQuery,
								 * ModelExtended.class);
								 * 
								 * log.info("MODEL : " + ttsModels.get(0));
								 * 
								 * TTSResponseConfig ttsResponseConfig = new TTSResponseConfig();
								 * log.info("Model Name :: " + ttsModels.get(0).getName()); LanguagePairs
								 * langPair = ttsModels.get(0).getLanguages(); for (LanguagePair lp : langPair)
								 * { ttsResponseConfig.setServiceId(""); ttsResponseConfig.setLanguage(lp); } //
								 * TODO: Read each model and store the results in PipelineResponseConfig //
								 * ttsResponseConfig.setDomain(ttsModels.get(0).getDomain());
								 * 
								 * ttsInference.addConfigItem(ttsResponseConfig);
								 */

								TTSResponseConfig ttsResponseConfig = new TTSResponseConfig();

								for (TaskSpecification taskSpecification : pipelineTaskSpecifications) {

									if (taskSpecification.getTaskType().name().equals("TTS")) {

										for (ConfigSchema configSchema : taskSpecification.getTaskConfig()) {
											// targetLang
											// if (configSchema.getSourceLanguage()
											// .equals(firstTaskSchema.getSourceLanguage())) {

											if (configSchema.getSourceLanguage().equals(sourceLang)) {
												ModelExtended model = modelDao.findByModelId(configSchema.getModelId());
												log.info("Model Name :: " + model.getName());
												LanguagePairs langPair = model.getLanguages();
												for (LanguagePair lp : langPair) {
													// TODO 24-03-2023: Set the service ID and model ID of that model
													ttsResponseConfig.setLanguage(lp);
												}
												ttsResponseConfig.setServiceId(configSchema.getServiceId());
												ttsResponseConfig.setModelId(configSchema.getModelId());
												TTSInference tTSInference = (TTSInference) model.getInferenceEndPoint()
														.getSchema();
												ttsResponseConfig.setSupportedVoices(tTSInference.getSupportedVoices());
											}

										}
									}

								}

								ttsInference.addConfigItem(ttsResponseConfig);
							}
						}

					}
					targetLangSizeCopy = targetLangListCopy.size();
					currentTaskIndex++;
				}
			}
		}
		int countResponseConfigs = 0;
		if (asrInference.getConfig().size() != 0) {
			newPipelineResponseConfig.add(asrInference);
			countResponseConfigs++;
		}
		if (ttsInference.getConfig().size() != 0) {
			newPipelineResponseConfig.add(ttsInference);
			countResponseConfigs++;
		}
		if (translationInference.getConfig().size() != 0) {
			newPipelineResponseConfig.add(translationInference);
			countResponseConfigs++;
		}

		if (pipelineTasks.size() != countResponseConfigs) {
			log.info("SHOULD RETURN ERROR");
			throw new PipelineValidationException("Sequence of languages not supported", HttpStatus.BAD_REQUEST);

		}

		pipelineResponse.setPipelineResponseConfig(newPipelineResponseConfig);
		log.info("NEW LANGUAGE LIST :: " + newLanguageList);
		pipelineResponse.setLanguages(newLanguageList);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		String json = mapper.writeValueAsString(pipelineResponse);
		// json = json.replaceAll("\"","");
		// PipelineResponse responsePipeline =
		// mapper.readValue(json,PipelineResponse.class);
		// log.info("String JSON :: "+json);

		ObjectNode node = mapper.valueToTree(pipelineResponse);
		return node;
	}

	/*
	 * public ObjectNode getModelsPipeline(String jsonRequest, String userID, String
	 * ulcaApiKey) throws Exception { PipelineRequest pipelineRequestCheckedTaskType
	 * = checkTaskType(jsonRequest);
	 * 
	 * PipelineRequest pipelineRequest =
	 * checkLanguageSequence(pipelineRequestCheckedTaskType);
	 * log.info("pipelineRequest :: " + pipelineRequest); if (pipelineRequest !=
	 * null) { validatePipelineRequest(pipelineRequest); } else { throw new
	 * PipelineValidationException("Pipeline validation failed. Check uploaded file syntax"
	 * , HttpStatus.BAD_REQUEST); }
	 * 
	 * return null; }
	 */

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
			throw new PipelineValidationException("PipelineTasks is required field", HttpStatus.BAD_REQUEST);

		log.info("pipelineRequest.getPipelineTasks() validated");

		if (pipelineRequest.getPipelineRequestConfig() == null
				&& pipelineRequest.getPipelineRequestConfig().getPipelineId() == null)
			throw new PipelineValidationException("PipelineRequestConfig and PipelineId are required field");

		log.info("pipelineRequest.getPipelineRequestConfig() is validated");

		ArrayList<PipelineTask> pipelineTasks = pipelineRequest.getPipelineTasks();

		ArrayList<String> pipelineTaskSequence = new ArrayList<String>();

		for (PipelineTask pipelineTask : pipelineTasks) {

			log.info("pipelineTask :: " + pipelineTask.getTaskType());
			if (pipelineTask.getTaskType() == null || pipelineTask.getTaskType().isEmpty()) {
				throw new PipelineValidationException("TaskType is not valid !", HttpStatus.BAD_REQUEST);
			} else {

				pipelineTaskSequence.add(pipelineTask.getTaskType());

			}
		}
		log.info("Pipeline Sequence :: " + pipelineTaskSequence);

		// Make query and find pipeline model with the submitter name

		/*
		 * Query dynamicQuery = new Query(); Criteria modelTypeCriteria =
		 * Criteria.where("_class").is("com.ulca.model.dao.PipelineModel");
		 * dynamicQuery.addCriteria(modelTypeCriteria); //Criteria submitterCriteria =
		 * Criteria.where("submitter.name")
		 * //.is(pipelineRequest.getPipelineRequestConfig().getSubmitter()); Criteria
		 * submitterCriteria = Criteria.where("serviceProvider.name")
		 * .is(pipelineRequest.getPipelineRequestConfig().getSubmitter());
		 * dynamicQuery.addCriteria(submitterCriteria); log.info("dynamicQuery : " +
		 * dynamicQuery.toString()); PipelineModel pipelineModel =
		 * mongoTemplate.findOne(dynamicQuery, PipelineModel.class);
		 */

		PipelineModel pipelineModel = pipelineModelDao
				.findByPipelineModelId(pipelineRequest.getPipelineRequestConfig().getPipelineId());

		if (pipelineModel == null)
			throw new PipelineValidationException("Pipeline model with the request PipelineId does not exist",
					HttpStatus.BAD_REQUEST);

		ArrayList<PipelineTaskSequence> supportedPipelines = pipelineModel.getSupportedPipelines();
		boolean flag = false;
		for (PipelineTaskSequence sequence : supportedPipelines) {

			log.info("Sequence :: " + sequence);
			ArrayList<String> pipelineTaskSequenceInModel = new ArrayList<String>();
			for (SupportedTasks task : sequence) {
				pipelineTaskSequenceInModel.add(task.name().toLowerCase());

			}
			log.info("pipelineTaskSequenceInModel :: " + pipelineTaskSequenceInModel);

			if (pipelineTaskSequenceInModel.equals(pipelineTaskSequence)) {
				log.info("available");
				flag = true;
				break;
			}

		}

		if (!flag)
			throw new PipelineValidationException("Requested pipeline does not exist with this submitter!",
					HttpStatus.BAD_REQUEST);

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

	public TranslationTaskInferenceInferenceApiKey validateUserDetails(String userID, String ulcaApiKey,
			String pipelineModelId) {

		log.info("++++++++++++++++Entry to validate User Details+++++++++++++++");

		TranslationTaskInferenceInferenceApiKey infKey = new TranslationTaskInferenceInferenceApiKey();
		String name = null;
		String value = null;
		JSONObject data = new JSONObject();
		data.put("userID", userID);
		data.put("ulcaApiKey", ulcaApiKey);
		data.put("pipelineId", pipelineModelId);

		// String requestUrl = ulca_ums_host + "/ulca/user-mgmt/v1/users/getApiKeys";
		String requestUrl = ulca_ums_host + "/ulca/user-mgmt/v1/users/generateServiceProviderKey";

		log.info("requestUrl :: " + requestUrl);
		String responseJsonStr = null;
		try {
			String requestJson = data.toString();

			log.info("requestJson :: " + requestJson);
			OkHttpClient client = new OkHttpClient();

			RequestBody body = RequestBody.create(requestJson, okhttp3.MediaType.parse("application/json"));

			log.info("body :: " + body.toString());
			Request httpRequest = new Request.Builder().url(requestUrl).post(body).build();
			log.info("httpRequest : " + httpRequest.toString());

			Response httpResponse = client.newCall(httpRequest).execute();

			/*
			 * if (httpResponse.code() < 200 || httpResponse.code() > 204) {
			 * 
			 * log.info(httpResponse.toString()); throw new
			 * PipelineValidationException("Ulca Api Key does not exist!",
			 * HttpStatus.BAD_REQUEST);
			 * 
			 * }
			 */

			if (httpResponse.code() == 200) {
				responseJsonStr = httpResponse.body().string();

				log.info("responseJsonStr :: " + responseJsonStr);
				JSONObject jsonObj = new JSONObject(responseJsonStr);
				JSONObject arr = (JSONObject) jsonObj.get("serviceProviderKeys");

				JSONObject obj1 = (JSONObject) arr.get("inferenceApiKey");

				name = (String) obj1.get("name");
				value = (String) obj1.get("value");

				log.info("name :: " + name);
				log.info("value :: " + value);

			} else if (httpResponse.code() == 404) {

				throw new PipelineValidationException("Something went wrong!", HttpStatus.BAD_REQUEST);

			} else {
				responseJsonStr = httpResponse.body().string();

				log.info("responseJsonStr :: " + responseJsonStr);
				JSONObject jsonObj = new JSONObject(responseJsonStr);
				String message = jsonObj.getString("message");
				log.info("message :: " + message);
				throw new PipelineValidationException(message, HttpStatus.BAD_REQUEST);

			}

			if (name == null || value == null) {
				throw new PipelineValidationException("Ulca Api Key does not exist!", HttpStatus.BAD_REQUEST);

			}

			infKey.setName(name);
			infKey.setValue(value);

		} catch (Exception e) {
			e.printStackTrace();

			throw new PipelineValidationException(e.getMessage(), HttpStatus.BAD_REQUEST);

		}

		// JSONObject json = (JSONObject) JsonParser.parseString(responseJsonStr);
		// log.info("OBJECT :: "+json);
		// String responseJsonStr = "{\"message\": \"UserApiKey found successfully\",
		// \"data\": [{\"appName\": \"Bhashaverse - android - prod\", \"ulcaApiKey\":
		// \"apikey1\", \"serviceProviderKeys\": [{\"serviceProviderName\": \"Dhruva\",
		// \"inferenceApiKey\": {\"name\": \"Authorization\", \"value\":
		// \"b4be0986-dfa2-4ca0-8945-ce6e05ac713b\"}}]}, {\"appName\": \"Bhashaverse -
		// iOS - prod\", \"ulcaApiKey\": \"35764737a9-3a15-4e9c-ad95-c7b69fe22qqq\",
		// \"serviceProviderKeys\": [{\"serviceProviderName\": \"Dhruva\",
		// \"inferenceApiKey\": {\"name\": \"Authorization\", \"value\":
		// \"e294b2b0-272c-4d8c-adb7-8d0f6cefe523\"}}]}]}";

		/*
		 * log.info("responseJsonStr ::" + responseJsonStr); JSONObject jsonObj1 = new
		 * JSONObject(responseJsonStr);
		 * 
		 * String name = null; String value = null; if (!jsonObj1.isNull("data")) {
		 * 
		 * JSONObject jsonObj = new JSONObject(responseJsonStr); JSONArray arr =
		 * (JSONArray) jsonObj.get("data"); log.info("JSONArray :: " + arr.toString());
		 * log.info("length ::" + arr.length());
		 * 
		 * for (int i = 0; i < arr.length(); i++) { JSONObject obj1 = (JSONObject)
		 * arr.get(i);
		 * 
		 * 
		 * log.info("obj1 :: " + obj1.toString());
		 * 
		 * if (obj1.get("ulcaApiKey").equals(ulcaApiKey)) {
		 * 
		 * JSONArray arr1 = (JSONArray) obj1.get("serviceProviderKeys");
		 * 
		 * JSONObject obj2 = (JSONObject) arr1.get(0);
		 * 
		 * JSONObject obj3 = (JSONObject) obj2.get("inferenceApiKey");
		 * 
		 * name = (String) obj3.get("name"); value = (String) obj3.get("value");
		 * 
		 * log.info("name :: " + name); log.info("value :: " + value);
		 * 
		 * break;
		 * 
		 * } } } else {
		 * 
		 * throw new PipelineValidationException("User ID does not exist!",
		 * HttpStatus.BAD_REQUEST);
		 * 
		 * }
		 */
		log.info("++++++++++++++++Exit to validate User Details+++++++++++++++");

		return infKey;
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

	public PipelineRequest checkTaskType(String jsonRequest) {

		ObjectMapper om = new ObjectMapper();
		JSONObject jo = om.convertValue(jsonRequest, JSONObject.class);
		log.info("jo :: " + jo.toString());
		String[] taskArray = { "translation", "asr", "tts" };
		List<String> taskList = Arrays.asList(taskArray);
		JSONArray ja = (JSONArray) jo.get("pipelineTasks");
		if (ja.length() < 1) {

			throw new PipelineValidationException("TaskType is not available", HttpStatus.BAD_REQUEST);

		}

		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo1 = (JSONObject) ja.get(i);
			if (jo1.isNull("taskType") || !taskList.contains(jo1.get("taskType"))) {
				throw new PipelineValidationException("TaskType is not valid !", HttpStatus.BAD_REQUEST);

			}
		}

		PipelineRequest pipelineRequest = null;
		try {
			pipelineRequest = om.readValue(jsonRequest, PipelineRequest.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.info("pipelineRequest :: " + pipelineRequest.toString());
		return pipelineRequest;
	}

	public PipelineRequest checkLanguageSequence(PipelineRequest pipelineRequest) {
		PipelineTasks pipelineTasks = pipelineRequest.getPipelineTasks();

		for (int i = 0; i < pipelineTasks.size() - 1; i++) {

			if (pipelineTasks.get(i).getTaskType() == "asr") {

				ASRTask aSRTask = (ASRTask) pipelineTasks.get(i);
				if (aSRTask.getConfig() != null) {
					ASRRequestConfig aSRRequestConfig = aSRTask.getConfig();
					if (pipelineTasks.get(i + 1).getTaskType() == "asr") {

						throw new PipelineValidationException("Invalid Task Type Sequence!", HttpStatus.BAD_REQUEST);

					} else if (pipelineTasks.get(i + 1).getTaskType() == "translation") {

						TranslationTask translationTaskNext = (TranslationTask) pipelineTasks.get(i + 1);
						if (translationTaskNext.getConfig() != null) {
							TranslationRequestConfig translationRequestConfigNext = translationTaskNext.getConfig();

							if (aSRRequestConfig.getLanguage().getSourceLanguage() != null
									&& translationRequestConfigNext.getLanguage().getSourceLanguage() != null) {

								if (!aSRRequestConfig.getLanguage().getSourceLanguage()
										.equals(translationRequestConfigNext.getLanguage().getSourceLanguage())) {
									throw new PipelineValidationException("Invalid Language Sequence!",
											HttpStatus.BAD_REQUEST);

								}

							}

						}

					} else if (pipelineTasks.get(i + 1).getTaskType() == "tts") {
						TTSTask tTSTaskNext = (TTSTask) pipelineTasks.get(i + 1);
						if (tTSTaskNext.getConfig() != null) {
							TTSRequestConfig tTSRequestConfigNext = tTSTaskNext.getConfig();

							if (aSRRequestConfig.getLanguage().getSourceLanguage() != null
									&& tTSRequestConfigNext.getLanguage().getSourceLanguage() != null) {

								if (!aSRRequestConfig.getLanguage().getSourceLanguage()
										.equals(tTSRequestConfigNext.getLanguage().getSourceLanguage())) {
									throw new PipelineValidationException("Invalid Language Sequence!",
											HttpStatus.BAD_REQUEST);

								}

							}

						}

					} else {
						throw new PipelineValidationException("Invalid Task Type!", HttpStatus.BAD_REQUEST);

					}

				}

			} else if (pipelineTasks.get(i).getTaskType() == "translation") {

				TranslationTask translationTask = (TranslationTask) pipelineTasks.get(i);
				if (translationTask.getConfig() != null) {
					TranslationRequestConfig translationRequestConfig = translationTask.getConfig();
					if (pipelineTasks.get(i + 1).getTaskType() == "asr") {

						throw new PipelineValidationException("Invalid Task Type Sequence!", HttpStatus.BAD_REQUEST);

					} else if (pipelineTasks.get(i + 1).getTaskType() == "translation") {

						throw new PipelineValidationException("Invalid Task Type Sequence!", HttpStatus.BAD_REQUEST);

					} else if (pipelineTasks.get(i + 1).getTaskType() == "tts") {
						TTSTask tTSTaskNext = (TTSTask) pipelineTasks.get(i + 1);
						if (tTSTaskNext.getConfig() != null) {
							TTSRequestConfig tTSRequestConfigNext = tTSTaskNext.getConfig();

							if (translationRequestConfig.getLanguage().getTargetLanguage() != null
									&& tTSRequestConfigNext.getLanguage().getSourceLanguage() != null) {

								if (!translationRequestConfig.getLanguage().getTargetLanguage()
										.equals(tTSRequestConfigNext.getLanguage().getSourceLanguage())) {
									throw new PipelineValidationException("Invalid Language Sequence!",
											HttpStatus.BAD_REQUEST);

								}

							}

						}

					} else {

						throw new PipelineValidationException("Invalid Task Type!", HttpStatus.BAD_REQUEST);
					}

				}

			} else if (pipelineTasks.get(i).getTaskType() == "tts") {
				TTSTask tTSTask = (TTSTask) pipelineTasks.get(i);

				if (tTSTask.getConfig() != null) {
					TTSRequestConfig tTSRequestConfig = tTSTask.getConfig();
					if (pipelineTasks.get(i + 1).getTaskType() == "asr") {

						ASRTask aSRTaskNext = (ASRTask) pipelineTasks.get(i + 1);
						if (aSRTaskNext.getConfig() != null) {
							ASRRequestConfig aSRRequestConfigNext = aSRTaskNext.getConfig();

							if (tTSRequestConfig.getLanguage().getSourceLanguage() != null
									&& aSRRequestConfigNext.getLanguage().getSourceLanguage() != null) {

								if (!tTSRequestConfig.getLanguage().getTargetLanguage()
										.equals(aSRRequestConfigNext.getLanguage().getSourceLanguage())) {
									throw new PipelineValidationException("Invalid Language Sequence!",
											HttpStatus.BAD_REQUEST);

								}

							}

						}

					} else if (pipelineTasks.get(i + 1).getTaskType() == "translation") {

						throw new PipelineValidationException("Invalid Task Type Sequence!", HttpStatus.BAD_REQUEST);

					} else if (pipelineTasks.get(i + 1).getTaskType() == "tts") {

						throw new PipelineValidationException("Invalid Task Type Sequence!", HttpStatus.BAD_REQUEST);

					} else {
						throw new PipelineValidationException("Invalid Task Type!", HttpStatus.BAD_REQUEST);

					}

				}

			} else {
				throw new PipelineValidationException("Invalid Task Type!", HttpStatus.BAD_REQUEST);

			}

		}

		return pipelineRequest;
	}

	/*
	 * public PipelineRequest convertEmptyToNull(PipelineRequest pipelineRequest) {
	 * 
	 * 
	 * PipelineTasks pipelineTasks = pipelineRequest.getPipelineTasks();
	 * 
	 * for(PipelineTask pipelineTask:pipelineTasks) {
	 * 
	 * if (pipelineTask.getTaskType() == "asr") {
	 * 
	 * ASRTask aSRTask =(ASRTask)pipelineTask; if(aSRTask.getConfig() !=null) {
	 * if(aSRTask.getConfig().getLanguage().getSourceLanguage()!=null) {
	 * if(aSRTask.getConfig().getLanguage().getSourceLanguage().name().isEmpty()) {
	 * aSRTask.getConfig().getLanguage().setSourceLanguage(null);
	 * 
	 * }
	 * 
	 * } if(aSRTask.getConfig().getLanguage().getTargetLanguage()!=null) {
	 * if(aSRTask.getConfig().getLanguage().getTargetLanguage().name().isEmpty()) {
	 * aSRTask.getConfig().getLanguage().setTargetLanguage(null);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * } else if (pipelineTask.getTaskType() == "translation") {
	 * 
	 * TranslationTask translationTask =(TranslationTask)pipelineTask;
	 * 
	 * 
	 * } else if (pipelineTask.getTaskType() == "tts") { TTSTask tTSTask
	 * =(TTSTask)pipelineTask;
	 * 
	 * 
	 * }else {
	 * 
	 * throw new PipelineValidationException("Invalid Task Type!",
	 * HttpStatus.BAD_REQUEST);
	 * 
	 * }
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * return pipelineRequest; }
	 */

	public PipelinesResponse explorePipelines() {

		List<PipelineModel> pipelineModels = pipelineModelDao.findAll();
		int noOfPipelineModels = (int) pipelineModelDao.count();
		log.info("noOfPipelineModels ::::::" + noOfPipelineModels);
		List<PipelineModelResponse> pipelineResponse = new ArrayList<PipelineModelResponse>();
		for (PipelineModel pipelineModel : pipelineModels) {
			PipelineModelResponse pipelineModelResponse = new PipelineModelResponse();
			pipelineModelResponse.setPipelineId(pipelineModel.getPipelineModelId());
			pipelineModelResponse.setName(pipelineModel.getName());
			pipelineModelResponse.setDescription(pipelineModel.getDescription());
			pipelineModelResponse.setServiceProviderName(pipelineModel.getServiceProvider().getName());
			pipelineModelResponse.setSupportedTasks(pipelineModel.getSupportedPipelines());
			pipelineResponse.add(pipelineModelResponse);
		}

		log.info("***********pipelineResponse******************");
		log.info(pipelineResponse.toString());

		if (noOfPipelineModels == 0) {

			throw new PipelineValidationException("Pipeline Models are not available!", HttpStatus.BAD_REQUEST);
		} else {
			return new PipelinesResponse(pipelineResponse, noOfPipelineModels);

		}

	}

}
