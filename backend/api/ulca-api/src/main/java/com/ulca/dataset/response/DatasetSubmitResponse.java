package com.ulca.dataset.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DatasetSubmitResponse {
	
	
	 private final String serviceRequestNumber;
	 private final String datasetId;
	 private final String timestamp;

}
