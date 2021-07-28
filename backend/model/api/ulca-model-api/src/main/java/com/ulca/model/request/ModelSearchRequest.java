package com.ulca.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.model.LanguagePairs;
import io.swagger.model.ModelTask;
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

	  private String task;
	  private String sourceLanguage;
	  private String targetLanguage;
	  
}
