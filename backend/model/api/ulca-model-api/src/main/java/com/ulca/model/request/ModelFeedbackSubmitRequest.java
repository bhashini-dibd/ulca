package com.ulca.model.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelFeedbackSubmitRequest {

	@NotNull(message = "tasktype must not be null")
	String taskType;
	String modelId;
	String userId;
	MultipartFile multipartInput; //data or url
	MultipartFile multipartOutput; //data or url
	Object feedback;
	List<ModelFeedbackSubmitRequest> detailedFeedback;
	
}
