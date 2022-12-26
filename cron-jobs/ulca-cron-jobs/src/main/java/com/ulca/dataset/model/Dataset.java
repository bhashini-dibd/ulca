package com.ulca.dataset.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection= "dataset")
public class Dataset {
	
	
	@Id
	private String datasetId;
	
	@Indexed(unique=true)
	private String datasetName;
	
	private String datasetType;
	private String domain;
	private String languages;
	private String collectionMethod;
	private String collectionSource;
	
	private Long createdOn;
	
	private String sampleSize;
	private String averageHumanScore;
	
	@DBRef
	private Fileidentifier datasetFileIdentifier;
	private String license;
	
	private String sampledOn;
	private String submitterId;
	private String Contributors;

}


