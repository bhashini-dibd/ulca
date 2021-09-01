package com.ulca.benchmark.request;

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
