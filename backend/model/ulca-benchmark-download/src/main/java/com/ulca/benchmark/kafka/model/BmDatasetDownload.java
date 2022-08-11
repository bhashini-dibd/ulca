package com.ulca.benchmark.kafka.model;

import java.util.Map;

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
public class BmDatasetDownload {
	
	String benchmarkDatasetId;
	String modelId;
	Map<String, String> benchmarkProcessIdsMap;

}
