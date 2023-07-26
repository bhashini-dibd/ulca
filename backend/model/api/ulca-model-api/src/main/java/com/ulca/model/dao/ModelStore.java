package com.ulca.model.dao;

import java.util.Objects;

import io.swagger.model.SupportedLanguages;
import io.swagger.model.SupportedTasks;

public class ModelStore {
    
   private SupportedTasks task;
   
   private SupportedLanguages sourceLanguage;
   
   
   private SupportedLanguages targetLanguage;

	private String modelId;
	
	
	
	

	public SupportedTasks getTask() {
		return task;
	}

	public void setTask(SupportedTasks task) {
		this.task = task;
	}

	public SupportedLanguages getSourceLanguage() {
		return sourceLanguage;
	}

	public void setSourceLanguage(SupportedLanguages sourceLanguage) {
		this.sourceLanguage = sourceLanguage;
	}

	public SupportedLanguages getTargetLanguage() {
		return targetLanguage;
	}

	public void setTargetLanguage(SupportedLanguages targetLanguage) {
		this.targetLanguage = targetLanguage;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(modelId, sourceLanguage, targetLanguage, task);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelStore other = (ModelStore) obj;
		return Objects.equals(modelId, other.modelId) && sourceLanguage == other.sourceLanguage
				&& targetLanguage == other.targetLanguage && task == other.task;
	}

	@Override
	public String toString() {
		return "PipelineModelsList [task=" + task + ", sourceLanguage=" + sourceLanguage + ", targetLanguage="
				+ targetLanguage + ", modelId=" + modelId + "]";
	}
	
	
	
}
