package com.ulca.dataset.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Fileidentifier {

	@Id
	private String fileId;
	private String fileType;
	private String fileLocationURL;
	private String fileUlcaUrl;
	private String md5hash;
	
	private String createdOn;
	private String submitterId;

}
