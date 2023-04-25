package com.ulca.model.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelFeedbackSubmitRequest {
    
	@Schema(defaultValue = "translation")
	@NotNull(message = "tasktype must not be null")
	String taskType;
	@Schema(defaultValue = "7227uwjhshshsjsjkskak")

	String modelId;
	
	@Schema(defaultValue = "abc")

	String userId;
	
	@Schema(defaultValue = "Data or url")

	MultipartFile multipartInput; //data or url
	
	@Schema(defaultValue = "Data or url")

	MultipartFile multipartOutput; //data or url
	Object feedback;
	List<ModelFeedbackSubmitRequest> detailedFeedback;
	
}
