package com.ulca.model.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.pipelinemodel.PipelineInference;


@Document(collection = "pipeline-model")
public class PipelineModel extends PipelineInference{
    
	
    @Id
	@JsonProperty("pipelineModelId")
	private String pipelineModelId;
	
	@JsonProperty("userId")
	private String userId;
    
	
	@JsonProperty("submittedOn")
	private Long submittedOn;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("publishedOn")
	private Long publishedOn;
	
	@JsonProperty("unpublishReason")
	private String unpublishReason;
	
	public String getPipelineModelId() {
		return pipelineModelId;
	}

	public void setPipelineModelId(String pipelineModelId) {
		this.pipelineModelId = pipelineModelId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(Long submittedOn) {
		this.submittedOn = submittedOn;
	}
	
	
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	


	public Long getPublishedOn() {
		return publishedOn;
	}

	public void setPublishedOn(Long publishedOn) {
		this.publishedOn = publishedOn;
	}
	
	public String getUnpublishReason() {
		return unpublishReason;
	}

	public void setUnpublishReason(String unpublishReason) {
		this.unpublishReason = unpublishReason;
	}
}
