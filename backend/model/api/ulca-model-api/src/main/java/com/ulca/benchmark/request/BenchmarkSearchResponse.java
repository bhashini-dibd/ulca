package com.ulca.benchmark.request;

import java.util.List;

import com.ulca.benchmark.response.BenchmarkDto;

import io.swagger.model.Benchmark;
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
public class BenchmarkSearchResponse {

	String message;
	List<BenchmarkDto> benchmark;
	
	int count;

}
