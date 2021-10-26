package com.ulca.benchmark.response;

import java.io.Serializable;
import java.util.Date;

import io.swagger.model.Benchmark;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
public class BenchmarkSubmitResponse implements Serializable {
	
		private static final long serialVersionUID = 1L;
		
			private String message;

			Data data;
			
			public BenchmarkSubmitResponse(Benchmark benchmark, String datasetId, String timestamp) {
				super();
				this.message = message;
				this.data = new Data(benchmark);
			}

			@Getter
			@Setter
			private class Data{  
				 private  Benchmark benchmark;

				public Data(Benchmark benchmark) {
					super();
					this.benchmark = benchmark;
					
				}
				 
			}  

	}

}
