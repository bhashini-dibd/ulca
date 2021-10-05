package com.ulca.benchmark.kafka.model;


import io.swagger.model.BenchmarkSubmissionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BenchmarkIngest {
	
	
	
	 String baseLocation;
	 String mode; 
	 String md5hash;
	 
	 private BenchmarkSubmissionType benchmarkSubmissionType;
	 private String url;
	 private String Id;
		

}

