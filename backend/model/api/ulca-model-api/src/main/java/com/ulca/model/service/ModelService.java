package com.ulca.model.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulca.benchmark.dao.BenchmarkDao;
import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.benchmark.util.ModelConstants;
import com.ulca.model.dao.ModelDao;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.exception.FileExtensionNotSupportedException;
import com.ulca.model.exception.ModelNotFoundException;
import com.ulca.model.exception.ModelStatusChangeException;
import com.ulca.model.exception.ModelValidationException;
import com.ulca.model.request.ModelComputeRequest;
import com.ulca.model.request.ModelSearchRequest;
import com.ulca.model.request.ModelStatusChangeRequest;
import com.ulca.model.response.ModelComputeResponse;
import com.ulca.model.response.ModelListByUserIdResponse;
import com.ulca.model.response.ModelListResponseDto;
import com.ulca.model.response.ModelSearchResponse;
import com.ulca.model.response.ModelStatusChangeResponse;
import com.ulca.model.response.UploadModelResponse;

import io.swagger.model.ImageFormat;
import io.swagger.model.InferenceAPIEndPoint;
import io.swagger.model.LanguagePair;
import io.swagger.model.LanguagePair.SourceLanguageEnum;
import io.swagger.model.LanguagePair.TargetLanguageEnum;
import io.swagger.model.LanguagePairs;
import io.swagger.model.License;
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
	BenchmarkDao benchmarkDao;


	@Value("${ulca.model.upload.folder}")
	private String modelUploadFolder;

	@Autowired
	ModelInferenceEndPointService modelInferenceEndPointService;
	
	@Autowired
	WebClient.Builder builder;
	
	@Autowired
	ModelConstants modelConstants;
	

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

	public ModelListResponseDto getModelByModelId(String modelId) {
		log.info("******** Entry ModelService:: getModelDescription *******");
		Optional<ModelExtended> result = modelDao.findById(modelId);

		if (!result.isEmpty()) {
			
			ModelExtended model = result.get();
			ModelListResponseDto modelDto = new ModelListResponseDto();
			BeanUtils.copyProperties(model, modelDto);
			List<String> metricList = modelConstants.getMetricListByModelTask(model.getTask().getType().toString());
			modelDto.setMetric(metricList);
			
			List<BenchmarkProcess> benchmarkProcess = benchmarkProcessDao.findByModelIdAndStatus(model.getModelId(), "Completed");
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
	
	public String storeModelTryMeFile(MultipartFile file) throws Exception {
		
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		/*
		 * check file extension
		 */
		String fileExtension = FilenameUtils.getExtension(fileName);
		try {
			ImageFormat imageformat = ImageFormat.fromValue(fileExtension);
			if(imageformat == null && !fileExtension.equalsIgnoreCase("jpg")) {
				log.info("Extension " + fileExtension + " not supported. It should be jpg/jpeg/bmp/png/tiff format");
				throw new FileExtensionNotSupportedException("Extension " + fileExtension + " not supported. It should be jpeg/bmp/png/tiff format");
			}
		}catch(FileExtensionNotSupportedException ex) {
			
			throw new FileExtensionNotSupportedException("Extension " + fileExtension + " not supported. It should be jpg/jpeg/bmp/png/tiff format");
			
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

		String modelFilePath = storeModelFile(file);
		ModelExtended modelObj = getUploadedModel(modelFilePath);
		
		validateModel(modelObj);
		
		modelObj.setUserId(userId);
		modelObj.setSubmittedOn(new Date().toString());
		modelObj.setPublishedOn(new Date().toString());
		modelObj.setStatus("unpublished");
		
		InferenceAPIEndPoint inferenceAPIEndPoint = modelObj.getInferenceEndPoint();
		String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();
		OneOfInferenceAPIEndPointSchema schema = inferenceAPIEndPoint.getSchema();
		schema = modelInferenceEndPointService.validateCallBackUrl(callBackUrl, schema);
		inferenceAPIEndPoint.setSchema(schema);
		modelObj.setInferenceEndPoint(inferenceAPIEndPoint);
		//modelDao.save(modelObj);
		
		if (modelObj != null) {
			try {
				modelDao.save(modelObj);
			} catch (DuplicateKeyException ex) {
				ex.printStackTrace();
				throw new DuplicateKeyException("Model with same name and version exist in system");
			}
		}

		return new UploadModelResponse("Model Saved Successfully", modelObj);
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
	
	private Boolean validateModel(ModelExtended model) throws ModelValidationException {
		
		if(model.getName() == null || model.getName().isBlank()) 
			throw new ModelValidationException("name is required field");
		
		if(model.getVersion() == null || model.getVersion().isBlank())
			throw new ModelValidationException("version is required field");
		
		if(model.getDescription() == null ||  model.getDescription().isBlank())
			throw new ModelValidationException("description is required field");
		
		if(model.getTask() == null)
			throw new ModelValidationException("task is required field");
		
		if(model.getLanguages() == null)
			throw new ModelValidationException("languages is required field");
		
		if(model.getLicense() == null)
			throw new ModelValidationException("license is required field");
		
		if(model.getLicense() == License.CUSTOM_LICENSE) {
			if(model.getLicenseUrl().isBlank())
				throw new ModelValidationException("custom licenseUrl is required field");
		}
		
		if(model.getDomain() == null)
			throw new ModelValidationException("domain is required field");
		
		if(model.getSubmitter() == null)
			throw new ModelValidationException("submitter is required field");
		
		if(model.getInferenceEndPoint() == null)
			throw new ModelValidationException("inferenceEndPoint is required field");
		
		if(model.getTrainingDataset() == null)
			throw new ModelValidationException("trainingDataset is required field");
		
		return true;
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
		
		Collections.shuffle(list); // randomize the search
		return new ModelSearchResponse("Model Search Result", list, list.size());

	}
	
	

	public ModelComputeResponse computeModel(ModelComputeRequest compute)
			throws URISyntaxException, IOException, KeyManagementException, NoSuchAlgorithmException {

		String modelId = compute.getModelId();
		ModelExtended modelObj = modelDao.findById(modelId).get();
		InferenceAPIEndPoint inferenceAPIEndPoint = modelObj.getInferenceEndPoint();
		String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();
		OneOfInferenceAPIEndPointSchema schema = inferenceAPIEndPoint.getSchema();

		return modelInferenceEndPointService.compute(callBackUrl, schema, compute);
	}
	
	public ModelComputeResponse tryMeOcrImageContent(MultipartFile file, String modelId) throws Exception {

		String imageFilePath = storeModelTryMeFile(file);
		
		ModelExtended modelObj = modelDao.findById(modelId).get();
		InferenceAPIEndPoint inferenceAPIEndPoint = modelObj.getInferenceEndPoint();
		String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();
		OneOfInferenceAPIEndPointSchema schema = inferenceAPIEndPoint.getSchema();
		
		ModelComputeResponse response = modelInferenceEndPointService.compute(callBackUrl, schema, imageFilePath);
		
		return response;
		
	}
	

	public ModelStatusChangeResponse changeStatus(@Valid ModelStatusChangeRequest request) {
		
		String userId = request.getUserId();
		String modelId = request.getModelId();
		String status = request.getStatus().toString();
		ModelExtended model = modelDao.findByModelId(modelId);
		if(model == null) {
			throw new ModelNotFoundException("model with modelId : " + modelId + " not found");
		}
		if(!model.getUserId().equalsIgnoreCase(userId)) {
			throw new ModelStatusChangeException("Not the submitter of model. So, can not " + status + " it.", status);
		}
		model.setStatus(status);
		modelDao.save(model);
		
		return new ModelStatusChangeResponse("Model " + status +  " successfull.");
	}
	
	

}
