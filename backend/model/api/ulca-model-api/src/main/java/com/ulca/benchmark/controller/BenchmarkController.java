package com.ulca.benchmark.controller;

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

import com.ulca.benchmark.request.BenchmarkListByModelRequest;
import com.ulca.benchmark.request.BenchmarkSearchRequest;
import com.ulca.benchmark.request.BenchmarkSubmitRequest;
import com.ulca.benchmark.request.ExecuteBenchmarkAllMetricRequest;
import com.ulca.benchmark.request.ExecuteBenchmarkRequest;
import com.ulca.benchmark.response.BenchmarkListByModelResponse;
import com.ulca.benchmark.response.BenchmarkListByUserIdResponse;
import com.ulca.benchmark.response.BenchmarkSearchResponse;
import com.ulca.benchmark.response.BenchmarkSubmitResponse;
import com.ulca.benchmark.response.ExecuteBenchmarkResponse;
import com.ulca.benchmark.response.GetBenchmarkByIdResponse;
import com.ulca.benchmark.service.BenchmarkService;
import com.ulca.model.exception.RequestParamValidationException;
import com.ulca.model.response.BmProcessListByProcessIdResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/ulca/apis/v0/benchmark")

public class BenchmarkController {

	@Autowired
	BenchmarkService benchmarkService;

	@PostMapping("/submit")
	public ResponseEntity<BenchmarkSubmitResponse> submitBenchmark(@Valid @RequestBody BenchmarkSubmitRequest request) throws RequestParamValidationException {

		log.info("******** Entry BenchMarkController:: Submit *******");
		BenchmarkSubmitResponse response = benchmarkService.submitBenchmark(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/listByUserId")
	public BenchmarkListByUserIdResponse listByUserId(@RequestParam String userId, @RequestParam(required = false) Integer startPage,
			@RequestParam(required = false) Integer endPage,@RequestParam(required = false) Integer pageSize,@RequestParam(required = false) String name) {
		log.info("******** Entry BenchMarkController:: listByUserId *******");

		return benchmarkService.benchmarkListByUserId(userId, startPage, endPage,pageSize,name);
	}

	@PostMapping("/execute")
	public ResponseEntity<ExecuteBenchmarkResponse> executeBenchmark(@Valid @RequestBody ExecuteBenchmarkRequest request) {

		log.info("******** Entry BenchMarkController:: Submit *******");
		
		ExecuteBenchmarkResponse response = benchmarkService.executeBenchmark(request);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/execute/allMetric")
	public ResponseEntity<ExecuteBenchmarkResponse> executeBenchmarkAllMetric(@Valid @RequestBody ExecuteBenchmarkAllMetricRequest request) {
			
		log.info("******** Entry BenchMarkController:: Submit *******");
		
		ExecuteBenchmarkResponse response = benchmarkService.executeBenchmarkAllMetric(request);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

	@GetMapping("/getBenchmark")
	public GetBenchmarkByIdResponse getBenchmarkById( @RequestParam(required = true) String benchmarkId ) {
		log.info("******** Entry BenchMarkController:: getBenchmarkById *******");

		return benchmarkService.getBenchmarkById(benchmarkId);
	}
	
	@PostMapping("/getByTask")
	public ResponseEntity<BenchmarkListByModelResponse> listBytask(@Valid @RequestBody BenchmarkListByModelRequest request) {

		log.info("******** Entry BenchMarkController:: getByTask *******");

		BenchmarkListByModelResponse response = benchmarkService.listByTaskID(request);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/search")
	public BenchmarkSearchResponse searchBenchmark(@Valid @RequestBody BenchmarkSearchRequest request, @RequestParam(required = false) Integer startPage,
			@RequestParam(required = false) Integer endPage) {

		log.info("******** Entry BenchmarkController:: searchBenchmark *******");
		return benchmarkService.searchBenchmark(request,startPage,endPage);
	}
	
	@GetMapping("/process/status")
	public ResponseEntity<BmProcessListByProcessIdResponse> processStatus(@RequestParam String benchmarkProcessId){
		
		BmProcessListByProcessIdResponse response = benchmarkService.processStatus(benchmarkProcessId);
		
		return new ResponseEntity<>(response, HttpStatus.OK); 
	}
}
