package com.ulca.dataset.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatasetCorpusSearchResponse {
	
	 private final String serviceRequestNumber;
	 private final Date timestamp;


}
