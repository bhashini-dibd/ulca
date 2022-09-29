package com.ulca.dataset.response;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Getter
@Setter
public class DatasetCorpusSearchResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String message;
	
	Data data;
	
	public DatasetCorpusSearchResponse(String message, String serviceRequestNumber) {
		super();
		this.message = message;
		this.data = new Data(serviceRequestNumber, new Date());
	}

	@Getter
	@Setter
	private class Data implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L; 
		
		 private  String serviceRequestNumber;
		 private  Date timestamp;
		public Data(String serviceRequestNumber, Date timestamp) {
			super();
			this.serviceRequestNumber = serviceRequestNumber;
			this.timestamp = timestamp;
		}
		 
		 
	}  

}
