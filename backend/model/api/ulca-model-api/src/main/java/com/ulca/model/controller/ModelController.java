package com.ulca.model.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.ulca.model.request.ModelComputeRequest;
import com.ulca.model.request.ModelLeaderboardRequest;
import com.ulca.model.request.ModelSearchRequest;
import com.ulca.model.response.ModelComputeResponse;
import com.ulca.model.response.ModelLeaderboardResponse;
import com.ulca.model.response.ModelListByUserIdResponse;
import com.ulca.model.response.ModelListResponseDto;
import com.ulca.model.response.ModelSearchResponse;
import com.ulca.model.response.UploadModelResponse;
import com.ulca.model.service.ModelService;

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
	public ModelListResponseDto getModel( @RequestParam(required = true) String modelId ) {
		log.info("******** Entry ModelController:: getModel *******");

		return modelService.getModelDescription(modelId);
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
	
	@PostMapping("/leaderboard")
	public ModelLeaderboardResponse searchLeaderboard(@Valid @RequestBody ModelLeaderboardRequest request) {

		log.info("******** Entry ModelController:: leaderboard *******");
		return modelService.searchLeaderboard(request);
	}
	
	
	@GetMapping("/leaderboard/filters")
	public ResponseEntity<Object>   leaderBoardFilters() throws IOException {

		log.info("******** Entry ModelController:: leaderBoardFilters *******");
		
		return new ResponseEntity<>(modelService.leaderBoardFilters(), HttpStatus.OK); 
	}
	
}
