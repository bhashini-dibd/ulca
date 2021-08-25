package com.ulca.dataset.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ulca.dataset.request.DatasetSubmitRequest;
import com.ulca.dataset.response.DatasetSubmitResponse;
import com.ulca.dataset.service.BenchmarkServices;
import com.ulca.dataset.service.DatasetService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value= "/ulca/apis/v0/dataset")
public class Benchmarkcontroller {
	
	@Autowired
	BenchmarkServices benchmarkServices;
	
	
	@PostMapping("/corpus/submit")
	public DatasetSubmitResponse datasetSubmit(@Valid  @RequestBody BenchmarkSubmitRequest request) {
		
		
	    log.info("******** Entry DatasetController:: datasetSubmit *******" );
	    return benchmarkServices.Submitdataset(request);
	  }


}
