package com.ulca.model.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelFeedbackSubmitRequest {

	String taskType;
	String modelId;
	String userId;
	String input; //data or url
	String output; //data or url
	Object feedback;
	List<ModelFeedbackSubmitRequest> detailedFeedback;
	
}
