package com.ulca.benchmark.model;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.model.Benchmark;

@Document(collection = "benchmark")
public class BenchmarkExtended extends Benchmark {

	String dataset;
	
}
