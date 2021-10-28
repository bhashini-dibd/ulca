package com.ulca.benchmark.response;

import java.util.List;

import io.swagger.model.Benchmark;

public class BenchmarkListByUserIdResponse {

	
	private String message;
	private List<Benchmark>  benchmark;
	private int count;
	
	public BenchmarkListByUserIdResponse(String message, List<Benchmark> benchmark, int count) {
		this.message = message;
		this.benchmark = benchmark;
		this.count = count;
	}

	
}
