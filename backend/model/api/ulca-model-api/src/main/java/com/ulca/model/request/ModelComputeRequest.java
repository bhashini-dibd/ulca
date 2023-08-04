package com.ulca.model.request;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.model.Gender;
import io.swagger.model.OCRRequest;
import io.swagger.model.OneOfInferenceAPIEndPointSchema;
import io.swagger.model.TTSRequest;
import io.swagger.model.TranslationRequest;
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
public class ModelComputeRequest {

	@NotBlank(message="modelId is required")
	public String modelId;
	
	public String task;
	
	public String userId;
	
	private OneOfRequest request = null;
     
	
}





