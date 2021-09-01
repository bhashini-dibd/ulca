package com.ulca.benchmark.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ulca.benchmark.request.BenchmarkSearchRequest;
import com.ulca.benchmark.request.BenchmarkSearchResponse;
import com.ulca.benchmark.request.ExecuteBenchmarkRequest;
import com.ulca.benchmark.request.ExecuteBenchmarkResponse;
import com.ulca.benchmark.service.BenchmarkService;

import io.swagger.model.Benchmark;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/ulca/apis/v0/benchmark")

public class BenchmarkController {

	@Autowired
	BenchmarkService benchmarkService;

	@PostMapping("/submit")
	public ResponseEntity<Benchmark> submitBenchmark(@RequestBody Benchmark request) {

		log.info("******** Entry BenchMarkController:: Submit *******");
		Benchmark benchmark = benchmarkService.submitBenchmark(request);

		return new ResponseEntity<>(benchmark, HttpStatus.OK);
	}

	@PostMapping("/execute")
	public ResponseEntity<ExecuteBenchmarkResponse> executeBenchmark(
			@Valid @RequestBody ExecuteBenchmarkRequest request) {

		log.info("******** Entry BenchMarkController:: Submit *******");
		
		ExecuteBenchmarkResponse response = benchmarkService.executeBenchmark(request);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/getByTask")
	public ResponseEntity<BenchmarkSearchResponse> listBytask(@Valid @RequestBody BenchmarkSearchRequest request) {

		log.info("******** Entry BenchMarkController:: getByTask *******");

		BenchmarkSearchResponse response = benchmarkService.listByTaskID(request);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
