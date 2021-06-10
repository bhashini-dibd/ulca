package com.ulca.dataset.request;

import javax.validation.constraints.NotBlank;

import com.ulca.dataset.constants.DatasetType;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DatasetSubmitRequest {
	
	@NotBlank(message="datasetType is required")
	private final String type;
	
	@NotBlank(message="datasetName is required")
    private final String datasetName;
	
	@NotBlank(message="url is required")
    private final String url;
	
	
   
}
