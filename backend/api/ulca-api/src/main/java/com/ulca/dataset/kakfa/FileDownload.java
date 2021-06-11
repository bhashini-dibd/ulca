package com.ulca.dataset.kakfa;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDownload {
	
	private String userId;
	private String fileUrl;
	private String datasetId;
	private String serviceRequestNumber;

}
