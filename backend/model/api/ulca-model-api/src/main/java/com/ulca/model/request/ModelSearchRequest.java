package com.ulca.model.request;

import javax.validation.constraints.NotBlank;

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

	  
	@NotBlank(message="task is required")
	private String task;
	
	private String sourceLanguage;
	
	private String targetLanguage;
	
	private String domain;
	
	private String submitter;
	
	private String userId;
	
	  
}
