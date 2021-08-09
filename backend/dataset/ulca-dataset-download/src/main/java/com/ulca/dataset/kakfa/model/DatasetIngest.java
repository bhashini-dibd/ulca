package com.ulca.dataset.kakfa.model;


import io.swagger.model.DatasetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
