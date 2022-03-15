package com.ulca.benchmark.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "modelinferenceresponse")
public class ModelInferenceResponse {
	
	@Id
	private String id;
	private String benchmarkingProcessId;
	private String modelId;
	private String modelName;
	private String modelTaskType;
	private String metric;
	private String benchmarkDatasetId;
	private String corpus;
	private String userId;
	
}

