package com.ulca.dataset.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ulca.dataset.model.TaskTracker;
import com.ulca.dataset.request.SearchCriteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@AllArgsConstructor
@Getter
@Setter
public class DatasetSearchStatusResponse {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String message;
	
	Data data;
	
	public DatasetSearchStatusResponse(String message, String serviceRequestNumber,  long timestamp, SearchCriteria searchCriteria, List<TaskTracker> status) {
		super();
		this.message = message;
		this.data = new Data(serviceRequestNumber, timestamp, searchCriteria, status);
	}

	@Getter
	@Setter
	private class Data implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L; 
		
		private final String serviceRequestNumber;
		private final long timestamp;
		private final SearchCriteria searchCriteria;
		private final List<TaskTracker> status;
		
		public Data(String serviceRequestNumber, long timestamp, SearchCriteria searchCriteria, List<TaskTracker> status) {
			super();
			this.serviceRequestNumber = serviceRequestNumber;
			this.timestamp = timestamp;
			this.searchCriteria = searchCriteria;
			this.status = status;
		}
		 
		 
	}  

	
	
	
}
