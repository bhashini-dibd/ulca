package com.ulca.model.dao;

import io.swagger.model.Domain;
import io.swagger.model.License;
import io.swagger.model.ModelTask;

public class ExploreModel {
    
	private String modelId;
	private String name ;
	private String userId;
	private String version;
	private String description;
	private ModelTask task;
	private Domain domain;
	private License license ;
	private String status;
	private Long submittedOn;
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ModelTask getTask() {
		return task;
	}
	public void setTask(ModelTask task) {
		this.task = task;
	}
	public Domain getDomain() {
		return domain;
	}
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	public License getLicense() {
		return license;
	}
	public void setLicense(License license) {
		this.license = license;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getSubmittedOn() {
		return submittedOn;
	}
	public void setSubmittedOn(Long submittedOn) {
		this.submittedOn = submittedOn;
	}
	@Override
	public String toString() {
		return "ExploreModel [modelId=" + modelId + ", name=" + name + ", userId=" + userId + ", version=" + version
				+ ", description=" + description + ", task=" + task + ", domain=" + domain + ", license=" + license
				+ ", status=" + status + ", submittedOn=" + submittedOn + "]";
	}
	
	

	
	
	
}
