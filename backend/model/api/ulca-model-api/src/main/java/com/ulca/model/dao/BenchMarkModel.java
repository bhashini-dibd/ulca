package com.ulca.model.dao;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;

import io.swagger.model.Model;

@Entity
public class BenchMarkModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value="uuid4")
	private String id;

	@JsonProperty("userId")
	private String name;
	
	
	private String description;
	
	@JsonProperty("publishedOn")
	private String domain;
	
	@CreatedDate
	@JsonProperty("createdOn")
	private Date createdDate;
	
	@LastModifiedDate
	@JsonProperty("submittedOn")
	private Date lastModifiedDate;


}
