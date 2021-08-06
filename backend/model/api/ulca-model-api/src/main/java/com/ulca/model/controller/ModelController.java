package com.ulca.model.controller;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.request.ModelComputeRequest;
import com.ulca.model.request.ModelSearchRequest;
import com.ulca.model.response.ModelComputeResponse;
import com.ulca.model.response.ModelListByUserIdResponse;
import com.ulca.model.response.ModelSearchResponse;
import com.ulca.model.response.UploadModelResponse;
import com.ulca.model.service.ModelInferenceEndPointService;
import com.ulca.model.service.ModelService;

import io.swagger.model.Model;
import io.swagger.model.TranslationResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/ulca/apis/v0/model")
public class ModelController {

	@Autowired
	ModelService modelService;

	@PostMapping("/submit")
	public ModelExtended submitModel(@Valid @RequestBody ModelExtended request) {

		log.info("******** Entry ModelController:: modelSubmit *******");
		return modelService.modelSubmit(request);
	}

	@GetMapping("/listByUserId")
	public ModelListByUserIdResponse listByUserId(@RequestParam String userId, @RequestParam(required = false) Integer startPage,
			@RequestParam(required = false) Integer endPage) {
		log.info("******** Entry ModelController:: listByUserId *******");

		return modelService.modelListByUserId(userId, startPage, endPage);
	}

	@GetMapping()
	public ModelExtended getModel( @RequestParam(required = true) String modelId ) {
		log.info("******** Entry ModelController:: getModel *******");

		return modelService.getMode(modelId);
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
	
	
	@PostMapping("/compute")
	public ModelComputeResponse computeModel(@Valid @RequestBody ModelComputeRequest request) throws MalformedURLException, URISyntaxException, JsonMappingException, JsonProcessingException {

		log.info("******** Entry ModelController:: computeModel *******");
		return modelService.computeModel(request);

	}
	
}
