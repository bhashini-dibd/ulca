package com.ulca.benchmark.response;


import java.util.List;

import com.ulca.benchmark.model.BenchmarkProcess;

import io.swagger.model.Benchmark;
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

public class GetBenchmarkByIdResponse extends Benchmark {

	private List<String> metric;
	List<BenchmarkProcess> benchmarkPerformance ;
	
}
