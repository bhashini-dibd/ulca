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
	
	@NotBlank(message="sourceLanguage is required")
	private String sourceLanguage;
	
	private String targetLanguage;
	  
}
