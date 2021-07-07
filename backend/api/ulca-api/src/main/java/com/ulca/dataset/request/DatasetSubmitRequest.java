package com.ulca.dataset.request;

import javax.validation.constraints.NotBlank;

import io.swagger.model.DatasetType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DatasetSubmitRequest {
	
	private final String userId;
	
	@NotBlank(message="datasetType is required")
	private final DatasetType type;
	
	@NotBlank(message="datasetName is required")
    private final String datasetName;
	
	@NotBlank(message="url is required")
    private final String url;
	
	
   
}
