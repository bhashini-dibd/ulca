package com.ulca.model.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
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
	
	@Schema(defaultValue = "uegebde367828wywhwyyw2u")
    @Id
    String feedbackId;
	@Schema(defaultValue = "jusuye6e3773wuwuwu")

	String stsFeedbackId;
	
	@Schema(defaultValue = "translation")

	String taskType;
	
	@Schema(defaultValue = "heheyw3663778wyww77")

	String modelId;
	
	@Schema(defaultValue = "isjujeuw376378ququ")

	String stsPerformanceId;
	
	@Schema(defaultValue = "parshant.tyagi@tarento.com")

	String userId;
	
	@Schema(defaultValue = "Data or url")

	String input; //data or url
	String output; //data or url
	Object feedback;
	
	String createdAt;
	String updatedAt;
	
	
}

