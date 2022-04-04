package com.ulca.model.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "model-feedback")
public class ModelFeedback {

	@Id
    String feedbackId;
	String stsFeedbackId;
	String taskType;
	String modelId;
	String stsPerformanceId;
	String userId;
	String input; //data or url
	String output; //data or url
	Object feedback;
	
	String createdAt;
	String updatedAt;
	
	
}

