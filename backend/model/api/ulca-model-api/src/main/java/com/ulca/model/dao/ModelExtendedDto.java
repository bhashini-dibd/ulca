package com.ulca.model.dao;



import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelExtendedDto extends ModelDto {
	
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

	public ModelExtendedDto modelId(String modelId) {
		this.modelId = modelId;
		return this;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	

	public ModelExtendedDto userId(String userId) {
		this.userId = userId;
		return this;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ModelExtendedDto submittedOn(Long submittedOn) {
		this.submittedOn = submittedOn;
		return this;
	}
	
	public Long getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(Long submittedOn) {
		this.submittedOn = submittedOn;
	}
	
	public ModelExtendedDto publishedOn(Long publishedOn) {
		this.publishedOn = publishedOn;
		return this;
	}

	public Long getPublishedOn() {
		return publishedOn;
	}

	public void setPublishedOn(Long publishedOn) {
		this.publishedOn = publishedOn;
	}

	public ModelExtendedDto status(String status) {
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
