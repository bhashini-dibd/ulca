package com.ulca.model.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ulca.model.dao.ModelFeedback;
import com.ulca.model.request.ModelComputeRequest;
import com.ulca.model.request.ModelFeedbackSubmitRequest;
import com.ulca.model.request.ModelSearchRequest;
import com.ulca.model.request.ModelStatusChangeRequest;
import com.ulca.model.response.AppModelsResponse;
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
import com.ulca.model.response.PipelinesResponse;
import com.ulca.model.response.UploadModelResponse;
import com.ulca.model.response.UploadPipelineResponse;
import com.ulca.model.service.ModelService;

import io.swagger.pipelinerequest.PipelineRequest;
import io.swagger.pipelinerequest.PipelineResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
//@CrossOrigin
@RequestMapping(value = "/ulca/apis/v0/model")
public class ModelController {

	@Autowired
	ModelService modelService;

	@GetMapping("/listByUserId")
	public ModelListByUserIdResponse listByUserId(@RequestParam String userId,
			@RequestParam(required = false) Integer startPage, @RequestParam(required = false) Integer endPage,
			@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String name) {

		log.info("******** Entry ModelController:: listByUserId *******");
		return modelService.modelListByUserId(userId, startPage, endPage, pageSize, name);
	}

	@GetMapping("/getModel")
	public ModelListResponseDto getModel(@RequestParam(required = true) String modelId) {

		log.info("******** Entry ModelController:: getModel *******");
		return modelService.getModelByModelId(modelId);
	}
	
	@PostMapping("/upload")
	public UploadModelResponse uploadModel(@RequestParam("file") MultipartFile file,
			@RequestParam(required = true) String userId) throws Exception {
		log.info("******** Entry ModelController:: uploadModel *******");
		return modelService.uploadModel(file, userId);
	}
	
	
	@PostMapping("/uploadPipeline")
	public UploadPipelineResponse uploadPipeline(@RequestParam("file") MultipartFile file,
			@RequestParam(required = true) String userId) throws Exception {
		log.info("******** Entry ModelController:: uploadModel *******");
		return modelService.uploadPipeline(file, userId);
	}

	@PostMapping("/search")
	public ModelSearchResponse searchModel(@Valid @RequestBody ModelSearchRequest request) {

		log.info("******** Entry ModelController:: modelSeach *******");
		return modelService.searchModel(request);
	}

	@PostMapping("/status/change")
	public ModelStatusChangeResponse changeStatus(@Valid @RequestBody ModelStatusChangeRequest request) {

		log.info("******** Entry ModelController:: changeStatus *******");
		return modelService.changeStatus(request);
	}

	@PostMapping("/compute")
	public ModelComputeResponse computeModel(@Valid @RequestBody ModelComputeRequest request) throws Exception {

		log.info("******** Entry ModelController:: computeModel *******");
		return modelService.computeModel(request);
	}

	@PostMapping("/tryMe")
	public ModelComputeResponse tryMeOcrImageContent(@RequestParam("file") MultipartFile file,
			@RequestParam(required = true) String modelId) throws Exception {
		log.info("******** Entry ModelController:: tryMeOcrImageContent *******");
		return modelService.tryMeOcrImageContent(file, modelId);
	}

	@PostMapping("/feedback/submit")
	public ModelFeedbackSubmitResponse modelFeedbackSubmit(@Valid @RequestBody ModelFeedbackSubmitRequest request)
			throws URISyntaxException, IOException, KeyManagementException, NoSuchAlgorithmException,
			InterruptedException {

		log.info("******** Entry ModelController:: modelFeedbackSubmit *******");
		return modelService.modelFeedbackSubmit(request);

	}

	@GetMapping("/feedback/getByModelId")
	public List<ModelFeedback> getModelFeedbackByModelId(@RequestParam(required = true) String modelId) {
		log.info("******** Entry ModelController:: getModelFeedbackByModelId *******");
		return modelService.getModelFeedbackByModelId(modelId);

	}

	@GetMapping("/getModelHealthStatus")
	public ModelHealthStatusResponse getHealthStatus(@RequestParam(required = false) String taskType,
			@RequestParam(required = false) Integer startPage, @RequestParam(required = false) Integer endPage) {
		log.info("******** Entry ModelController:: getModelHealthStatus *******");
		return modelService.modelHealthStatus(taskType, startPage, endPage);

	}

	@GetMapping("/feedback/getByTaskType")
	public List<GetModelFeedbackListResponse> getModelFeedbackByTaskType(
			@RequestParam(required = true) String taskType) {
		log.info("******** Entry ModelController:: getModelFeedbackByModelId *******");
		return modelService.getModelFeedbackByTaskType(taskType);

	}

	@GetMapping("/getTransliterationModelId")
	public GetTransliterationModelIdResponse getTransliterationModelId(
			@RequestParam(required = true) String sourceLanguage,
			@RequestParam(required = false) String targetLanguage) {
		log.info("******** Entry ModelController:: getModelFeedbackByModelId *******");
		return modelService.getTransliterationModelId(sourceLanguage, targetLanguage);

	}

	/*
	 * @PostMapping("/getModelsPipeline") public String
	 * getModelsPipeline(@RequestParam("file") MultipartFile file,
	 * 
	 * @RequestParam(required = true) String userId) throws Exception {
	 * log.info("******** Entry ModelController:: getModelsPipeline *******");
	 * return modelService.getModelsPipeline(file, userId); }
	 */

	@PostMapping("/getModelsPipeline")
	public ObjectNode getModelsPipeline(@RequestHeader(value = "userID",required = false) String userID,
			@RequestHeader(value = "ulcaApiKey" ,required = false) String ulcaApiKey, @RequestBody String pipelineRequest) throws Exception {
		log.info("******** Entry ModelController:: getModelsPipeline *******");
		log.info("userID :: " + userID);
		log.info("ulcaApiKey :: " + ulcaApiKey);
		return modelService.getModelsPipeline(pipelineRequest, userID, ulcaApiKey);
	}
      
	
	@GetMapping("/explorePipelines")
	public PipelinesResponse explorePipelines(@RequestParam(required = false) String serviceProviderName) {

		log.info("******** Entry ModelController:: explorePipelines *******");
		return modelService.explorePipelines(serviceProviderName);
	}
	
	@GetMapping("/appModels")
	public AppModelsResponse getAppModels(@RequestParam(required = false) String taskType) {

		log.info("******** Entry ModelController:: getAppModels *******");
		return modelService.getAppModels(taskType);
	}
	
	@PostMapping("/getModelsAllPipeline")
	public ObjectNode getModelsAllPipeline(@RequestBody String pipelineRequest) throws Exception {
		log.info("******** Entry ModelController:: getModelsAllPipeline *******");
	
		return modelService.getModelsAllPipeline(pipelineRequest);
	}
}
