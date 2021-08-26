package com.ulca.benchmark.download.kafka.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteBenchmarkRequest {

	 	 String modelId;
	     List<BenchmarkMetricRequest> benchmarks;
	    
}
