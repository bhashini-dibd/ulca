package com.ulca.model.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.model.Model;

@Document(collection = "model")
@CompoundIndexes({
    @CompoundIndex(name = "name_version", def = "{'name': 1, 'version': 1}", unique = true)
})
public class ModelExtended extends Model {

	@Id
	@JsonProperty("modelId")
	private String modelId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("submittedOn")
	private Long submittedOn;
	
	@JsonProperty("publishedOn")
	private Long publishedOn;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("unpublishReason")
	private String unpublishReason;

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

	public ModelExtended submittedOn(Long submittedOn) {
		this.submittedOn = submittedOn;
		return this;
	}
	
	public Long getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(Long submittedOn) {
		this.submittedOn = submittedOn;
	}
	
	public ModelExtended publishedOn(Long publishedOn) {
		this.publishedOn = publishedOn;
		return this;
	}

	public Long getPublishedOn() {
		return publishedOn;
	}

	public void setPublishedOn(Long publishedOn) {
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

	public String getUnpublishReason() {
		return unpublishReason;
	}

	public void setUnpublishReason(String unpublishReason) {
		this.unpublishReason = unpublishReason;
	}
	
}
