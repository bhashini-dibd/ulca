package com.ulca.dataset.model.deserializer;

public enum NerDatasetSchemaKeys {

	// required
	datasetType, 
	languages, 
	collectionSource, 
	domain, 
	license, 
	submitter, 
	tagsFormat,
    
	// optional
	version, 
	licenseUrl, 
	isStopwordsRemoved,
	formatDescription,
	collectionMethod
	
	
}
