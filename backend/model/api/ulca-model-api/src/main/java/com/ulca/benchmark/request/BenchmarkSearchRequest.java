package com.ulca.benchmark.request;

import io.swagger.model.Benchmark;
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
public class BenchmarkSearchRequest {
	
	
   private ModelTask task ;
   private Benchmark domain ;

}
