package com.ulca.benchmark.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
public class BenchmarkSubmitResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		private String message;

		Data data;
		
		public BenchmarkSubmitResponse(String message, String serviceRequestNumber, String benchmarkId, long timestamp) {
			super();
			this.message = message;
			this.data = new Data(serviceRequestNumber, benchmarkId, timestamp);
		}

		@Getter
		@Setter
		private class Data{  
			 private  String serviceRequestNumber;
			 private  String benchmarkId;
			 private  long timestamp;
			public Data(String serviceRequestNumber, String benchmarkId, long timestamp) {
				super();
				this.serviceRequestNumber = serviceRequestNumber;
				this.benchmarkId = benchmarkId;
				this.timestamp = timestamp;
			}
			 
		}  

}

