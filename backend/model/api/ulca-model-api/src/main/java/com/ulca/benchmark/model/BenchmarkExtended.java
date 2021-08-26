package com.ulca.benchmark.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.model.Benchmark;
import io.swagger.model.ModelTask;

@Document(collection = "benchmark")
public class BenchmarkExtended extends Benchmark {

	String dataset;
	
	
	
}
