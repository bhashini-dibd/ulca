package com.ulca.dataset.request;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DatasetSubmitRequest {
	
	@NotBlank(message="userId is required")
	private final String userId;
	
	@NotBlank(message="datasetName is required")
    private final String datasetName;
	
	@NotBlank(message="url is required")
	@URL
    private final String url;
	
	
   
}
