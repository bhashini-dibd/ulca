package com.ulca.dataset.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ulca.dataset.request.DatasetCorpusSearchRequest;
import com.ulca.dataset.request.DatasetSubmitRequest;
import com.ulca.dataset.response.DatasetByIdResponse;
import com.ulca.dataset.response.DatasetByServiceReqNrResponse;
import com.ulca.dataset.response.DatasetCorpusSearchResponse;
import com.ulca.dataset.response.DatasetListByUserIdResponse;
import com.ulca.dataset.response.DatasetSearchStatusResponse;
import com.ulca.dataset.response.DatasetSubmitResponse;
import com.ulca.dataset.response.SearchListByUserIdResponse;
import com.ulca.dataset.service.DatasetService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/ulca/apis/v0/dataset")
public class DatasetController {

	@Autowired
	DatasetService datasetService;

	@PostMapping("/corpus/submit")
	public DatasetSubmitResponse datasetSubmit(@Valid @RequestBody DatasetSubmitRequest request) {

		log.info("******** Entry DatasetController:: datasetSubmit *******");
		return datasetService.datasetSubmit(request);
	}

	@GetMapping("/listByUserId")
	public DatasetListByUserIdResponse listByUserId(@RequestParam String userId,
			@RequestParam(required = false) Integer startPage, @RequestParam(required = false) Integer endPage,
			@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String name) {

		log.info("******** Entry DatasetController:: listByUserId *******");
		return datasetService.datasetListByUserId(userId, startPage, endPage, pageSize, name);
	}

	@GetMapping("/getByDatasetId")
	public DatasetByIdResponse datasetById(@RequestParam String datasetId) {

		log.info("******** Entry DatasetController:: listByUserId *******");
		return datasetService.datasetById(datasetId);
	}

	@GetMapping("/getByServiceRequestNumber")
	public DatasetByServiceReqNrResponse datasetByServiceRequestNumber(@RequestParam String serviceRequestNumber) {

		log.info("******** Entry DatasetController:: listByUserId *******");
		return datasetService.datasetByServiceRequestNumber(serviceRequestNumber);
	}

	@PostMapping("/corpus/search")
	public DatasetCorpusSearchResponse corpusSearch(@RequestBody DatasetCorpusSearchRequest request)
			throws JsonProcessingException {

		log.info("******** Entry DatasetController:: corpusSearch *******");
		return datasetService.corpusSearch(request);
	}

	@GetMapping("/corpus/search/status")
	public DatasetSearchStatusResponse searchStatus(@RequestParam String serviceRequestNumber) {
		
		log.info("******** Entry DatasetController:: searchStatus *******");
		return datasetService.searchStatus(serviceRequestNumber);
	}

	@GetMapping("/corpus/search/listByUserId")
	public SearchListByUserIdResponse searchListByUserId(@RequestParam String userId,
			@RequestParam(required = false) Integer startPage, @RequestParam(required = false) Integer endPage) {
		
		log.info("******** Entry DatasetController:: searchListByUserId *******");
		return datasetService.searchListByUserId(userId, startPage, endPage);
	}
	
	@GetMapping("/searchDataset")
	public DatasetListByUserIdResponse datasetListByString(@RequestParam String userId,
			 @RequestParam(required = false) String name) {

		log.info("******** Entry DatasetController:: datasetListByString *******");
		return datasetService.datasetListByString(userId, name);
	}
}
