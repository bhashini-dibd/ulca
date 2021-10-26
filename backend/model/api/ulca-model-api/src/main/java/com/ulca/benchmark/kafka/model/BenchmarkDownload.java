package com.ulca.benchmark.kafka.model;

import io.swagger.model.BenchmarkSubmissionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BenchmarkDownload {
	
	private String url;
	private String Id;
	private BenchmarkSubmissionType benchmarkSubmissionType;

}
