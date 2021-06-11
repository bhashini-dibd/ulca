package com.ulca.dataset.response;

import java.util.Date;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DatasetListByUserIdResponse {

	private final String datasetId;
	private final String serviceRequestNumber;
	private final String datasetName;
	private final Date submittedOn;
	private final String status;
}
