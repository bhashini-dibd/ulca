package com.ulca.model.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.validation.Valid;

import com.ulca.model.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.dao.ModelFeedback;

import com.ulca.model.request.ModelComputeRequest;
import com.ulca.model.request.ModelFeedbackSubmitRequest;
import com.ulca.model.request.ModelSearchRequest;
import com.ulca.model.request.ModelStatusChangeRequest;
import com.ulca.model.service.ModelService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/ulca/apis/v0/model")
public class ModelController {

	@Autowired
	ModelService modelService;

	@GetMapping("/listByUserId")
	public ModelListByUserIdResponse listByUserId(@RequestParam String userId, @RequestParam(required = false) Integer startPage,
			@RequestParam(required = false) Integer endPage,@RequestParam(required = false) Integer pageSize,@RequestParam(required = false) String name) {
		
		log.info("******** Entry ModelController:: listByUserId *******");
		return modelService.modelListByUserId(userId, startPage, endPage,pageSize,name);
	}

	@GetMapping("/getModel")
	public ModelListResponseDto getModel( @RequestParam(required = true) String modelId ) {
		
		log.info("******** Entry ModelController:: getModel *******");
		return modelService.getModelByModelId(modelId);
	}
	
	@PostMapping("/upload")
	public UploadModelResponse uploadModel(@RequestParam("file") MultipartFile file,@RequestParam(required = true) String userId) throws Exception {
		log.info("******** Entry ModelController:: uploadModel *******");
		return modelService.uploadModel(file, userId);
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
	public ModelComputeResponse computeModel(@Valid @RequestBody ModelComputeRequest request) throws URISyntaxException, IOException, KeyManagementException, NoSuchAlgorithmException, InterruptedException {

		log.info("******** Entry ModelController:: computeModel *******");
		return modelService.computeModel(request);
	}
	
	@PostMapping("/tryMe")
	public ModelComputeResponse tryMeOcrImageContent(@RequestParam("file") MultipartFile file, @RequestParam(required = true) String modelId) throws Exception {
		log.info("******** Entry ModelController:: tryMeOcrImageContent *******");
		return modelService.tryMeOcrImageContent(file, modelId);
	}
	
	@PostMapping("/feedback/submit")
	public ModelFeedbackSubmitResponse modelFeedbackSubmit(@Valid @RequestBody ModelFeedbackSubmitRequest request) throws URISyntaxException, IOException, KeyManagementException, NoSuchAlgorithmException, InterruptedException {

		log.info("******** Entry ModelController:: modelFeedbackSubmit *******");
		return modelService.modelFeedbackSubmit(request);

	}
	
	@GetMapping("/feedback/getByModelId")
	public List<ModelFeedback> getModelFeedbackByModelId(@RequestParam(required = true) String modelId ) {
		log.info("******** Entry ModelController:: getModelFeedbackByModelId *******");
		return modelService.getModelFeedbackByModelId(modelId);

	}
	@GetMapping("/getModelHealthStatus")
	public ModelHealthStatusResponse getHealthStatus(@RequestParam(required = false) String taskType , @RequestParam(required = false) Integer startPage
	       , @RequestParam(required = false) Integer endPage) {
		log.info("******** Entry ModelController:: getModelHealthStatus *******");
		return modelService.modelHealthStatus(taskType,startPage,endPage);

	}
	@GetMapping("/feedback/getByTaskType")
	public List<GetModelFeedbackListResponse> getModelFeedbackByTaskType(@RequestParam(required = true) String taskType ) {
		log.info("******** Entry ModelController:: getModelFeedbackByModelId *******");
		return modelService.getModelFeedbackByTaskType(taskType);

	}
	
	
	@GetMapping("/getTransliterationModelId")
	public GetTransliterationModelIdResponse  getTransliterationModelId() {
		log.info("******** Entry ModelController:: getModelFeedbackByModelId *******");
		return modelService.getTransliterationModelId();

	}
	
}
