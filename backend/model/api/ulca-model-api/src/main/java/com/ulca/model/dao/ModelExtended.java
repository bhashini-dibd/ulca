package com.ulca.model.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.model.Model;

@Document(collection = "model")
public class ModelExtended extends Model {

	@Id
	@JsonProperty("modelId")
	private String modelId;

	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("submittedOn")
	private String submittedOn;
	
	@JsonProperty("publishedOn")
	private String publishedOn;
	
	
	@JsonProperty("status")
	private String status;

	public ModelExtended modelId(String modelId) {
		this.modelId = modelId;
		return this;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public ModelExtended userId(String userId) {
		this.userId = userId;
		return this;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public ModelExtended submittedOn(String submittedOn) {
		this.submittedOn = submittedOn;
		return this;
	}
	
	
	public String getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(String submittedOn) {
		this.submittedOn = submittedOn;
	}
	
	public ModelExtended publishedOn(String publishedOn) {
		this.publishedOn = publishedOn;
		return this;
	}

	public String getPublishedOn() {
		return publishedOn;
	}

	public void setPublishedOn(String publishedOn) {
		this.publishedOn = publishedOn;
	}
	
	

	public ModelExtended status(String status) {
		this.status = status;
		return this;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
