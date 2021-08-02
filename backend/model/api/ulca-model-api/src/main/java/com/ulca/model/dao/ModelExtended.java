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
	
	@JsonProperty("createdOn")
	private String createdOn;
	
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

	public ModelExtended createdOn(String createdOn) {
		this.createdOn = createdOn;
		return this;
	}
	
	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
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
