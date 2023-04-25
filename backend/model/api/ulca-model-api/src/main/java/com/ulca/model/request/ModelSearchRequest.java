package com.ulca.model.request;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModelSearchRequest {

	@Schema(defaultValue = "translation")
    @NotBlank(message="task is required")
	private String task;
	
	@Schema(defaultValue = "HI")

	private String sourceLanguage;
	
	@Schema(defaultValue = "EN")

	private String targetLanguage;
	
	@Schema(defaultValue = "domain")

	private String domain;
	@Schema(defaultValue = "AI4Bharat")

	private String submitter;
	
	@Schema(defaultValue = "bcd")

	private String userId;
	
	  
}
