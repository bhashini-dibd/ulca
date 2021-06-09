package com.ulca.dataset.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Fileidentifier {

	@Id
	private String fileId;
	private String fileType;
	private String fileLocationURL;
	private String fileUlcaUrl;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private Date createdOn;
	private String submitterId;

}
