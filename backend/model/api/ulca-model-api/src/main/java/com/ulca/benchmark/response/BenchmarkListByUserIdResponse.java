package com.ulca.benchmark.response;

import java.util.List;

import io.swagger.model.Benchmark;
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
public class BenchmarkListByUserIdResponse {

	
	private String message;
	private List<Benchmark>  benchmark;
	private int count;
	private int totalCount;
	
	
	
}
