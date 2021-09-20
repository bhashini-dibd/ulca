package com.ulca.benchmark.response;

import java.util.List;

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
	List<Benchmark> data;
	int count;

}
