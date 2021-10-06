package com.ulca.benchmark.response;

import java.util.List;

import io.swagger.model.Benchmark;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor

public class BenchmarkDto  extends Benchmark{
	
	private List<String> availableMetric;

}
