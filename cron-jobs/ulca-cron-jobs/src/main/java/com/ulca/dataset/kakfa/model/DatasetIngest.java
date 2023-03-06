package com.ulca.dataset.kakfa.model;


import io.swagger.model.DatasetType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DatasetIngest {
	
	
	 String serviceRequestNumber;
	 String baseLocation;
	 String mode; // (real/pseudo)
	 String md5hash;
	 
	 DatasetType datasetType;
	 String userId;
	 String datasetId;
	 String datasetName;

}

