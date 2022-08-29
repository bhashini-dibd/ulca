package com.ulca.dataset.response;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
public class DatasetSubmitResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		private String message;

		Data data;
		
		public DatasetSubmitResponse(String message, String serviceRequestNumber, String datasetId, long timestamp) {
			super();
			this.message = message;
			this.data = new Data(serviceRequestNumber, datasetId, timestamp);
		}

		@Getter
		@Setter
		private class Data{  
			 private  String serviceRequestNumber;
			 private  String datasetId;
			 private  long timestamp;
			public Data(String serviceRequestNumber, String datasetId, long timestamp) {
				super();
				this.serviceRequestNumber = serviceRequestNumber;
				this.datasetId = datasetId;
				this.timestamp = timestamp;
			}
			 
		}  

}
