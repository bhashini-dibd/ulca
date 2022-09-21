package com.ulca.dataset.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatasetListByUserIdResponseDto {

	private  String datasetId;
	private  String serviceRequestNumber;
	private  String datasetName;
	private  String datasetType;
	private  Long submittedOn;
	private  String status;
	
}
