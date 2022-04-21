package com.ulca.model.response;

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
public class ModelFeedbackSubmitResponse {
	
	private String message;
	
	Data data;

	public ModelFeedbackSubmitResponse(String message, String feedbackId) {
		super();
		this.message = message;
		this.data = new Data(feedbackId);
	}
	
	@Getter
	@Setter
	private class Data{  
		 private  String feedbackId;
		 
		public Data(String feedbackId) {
			super();
			this.feedbackId = feedbackId;
			
		}
		 
	}  
	

}
