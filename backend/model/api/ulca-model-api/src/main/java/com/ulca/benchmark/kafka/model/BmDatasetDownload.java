package com.ulca.benchmark.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BmDatasetDownload {
	
	String benchmarkProcessId;
	List<String> benchmarkProcessIdList;

	public BmDatasetDownload(String benchmarkProcessId) {
		this.benchmarkProcessId = benchmarkProcessId;
	}

}
