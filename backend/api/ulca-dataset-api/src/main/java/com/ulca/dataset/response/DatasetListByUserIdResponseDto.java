package com.ulca.dataset.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DatasetListByUserIdResponseDto {

	private final String datasetId;
	private final String serviceRequestNumber;
	private final String datasetName;
	private final String datasetType;
	private final String submittedOn;
	private final String status;
}
