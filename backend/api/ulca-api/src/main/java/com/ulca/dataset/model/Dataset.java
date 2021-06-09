package com.ulca.dataset.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private Date createdOn;
	private String sampleSize;
	private String averageHumanScore;
	
	@DBRef
	private Fileidentifier datasetFileIdentifier;
	private String license;
	
	private String sampledOn;
	private String submitterId;
	private String Contributors;

}


