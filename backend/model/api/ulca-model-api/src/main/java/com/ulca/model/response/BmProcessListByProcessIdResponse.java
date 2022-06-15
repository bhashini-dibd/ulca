package com.ulca.model.response;

import java.util.List;

import com.ulca.benchmark.model.BenchmarkProcess;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BmProcessListByProcessIdResponse {
	
	String message;
	List<BenchmarkProcess> data;
	int count;

}
