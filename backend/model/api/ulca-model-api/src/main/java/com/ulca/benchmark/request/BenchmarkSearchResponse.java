package com.ulca.benchmark.request;

import com.ulca.benchmark.model.BenchmarkExtended;

import io.swagger.model.ModelTask;
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
	
	private List<BenchmarkExtended>  benchmarkExtendedlist;

}
