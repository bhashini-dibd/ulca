package com.ulca.model.dao;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.model.Domain;
import io.swagger.model.InferenceAPIEndPoint;
import io.swagger.model.LanguagePairs;
import io.swagger.model.License;
import io.swagger.model.Model;
import io.swagger.model.ModelTask;
import io.swagger.model.Submitter;
import io.swagger.model.TrainingDataset;
import io.swagger.v3.oas.annotations.media.Schema;

public class ModelDto {
	
	@Schema(defaultValue = "bcd")
	  private String name = null;

	@Schema(defaultValue = "bcd")
	  private String version = null;


	@Schema(defaultValue = "bcd")
	  private String description = null;

	@Schema(defaultValue = "bcd")
	  private String refUrl = null;


	  private ModelTask task = null;

	
	  private LanguagePairs languages = null;

	
	  private License license = null;

	  @Schema(defaultValue = "bcd")
	  private String licenseUrl = null;

	  private Domain domain = null;


	  private Submitter submitter = null;

	 
	  private InferenceAPIEndPointDto inferenceEndPoint;;

	
	  private TrainingDataset trainingDataset = null;

	  public ModelDto name(String name) {
	    this.name = name;
	    return this;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public ModelDto version(String version) {
	    this.version = version;
	    return this;
	  }

     public String getVersion() {
	    return version;
	  }

	  public void setVersion(String version) {
	    this.version = version;
	  }

	  public ModelDto description(String description) {
	    this.description = description;
	    return this;
	  }

	  public String getDescription() {
	    return description;
	  }

	  public void setDescription(String description) {
	    this.description = description;
	  }

	  public ModelDto refUrl(String refUrl) {
	    this.refUrl = refUrl;
	    return this;
	  }

	  public String getRefUrl() {
	    return refUrl;
	  }

	  public void setRefUrl(String refUrl) {
	    this.refUrl = refUrl;
	  }

	  public ModelDto task(ModelTask task) {
	    this.task = task;
	    return this;
	  }

	 
	    public ModelTask getTask() {
	    return task;
	  }

	  public void setTask(ModelTask task) {
	    this.task = task;
	  }

	  public ModelDto languages(LanguagePairs languages) {
	    this.languages = languages;
	    return this;
	  }

	

	    public LanguagePairs getLanguages() {
	    return languages;
	  }

	  public void setLanguages(LanguagePairs languages) {
	    this.languages = languages;
	  }

	  public ModelDto license(License license) {
	    this.license = license;
	    return this;
	  }

	  
	    public License getLicense() {
	    return license;
	  }

	  public void setLicense(License license) {
	    this.license = license;
	  }

	  public ModelDto licenseUrl(String licenseUrl) {
	    this.licenseUrl = licenseUrl;
	    return this;
	  }


	  
	    public String getLicenseUrl() {
	    return licenseUrl;
	  }

	  public void setLicenseUrl(String licenseUrl) {
	    this.licenseUrl = licenseUrl;
	  }

	  public ModelDto domain(Domain domain) {
	    this.domain = domain;
	    return this;
	  }

	
	    public Domain getDomain() {
	    return domain;
	  }

	  public void setDomain(Domain domain) {
	    this.domain = domain;
	  }

	  public ModelDto submitter(Submitter submitter) {
	    this.submitter = submitter;
	    return this;
	  }


	    public Submitter getSubmitter() {
	    return submitter;
	  }

	  public void setSubmitter(Submitter submitter) {
	    this.submitter = submitter;
	  }
	  
	  
	  
	  

	  public ModelDto inferenceEndPoint(InferenceAPIEndPointDto inferenceEndPoint) {
	    this.inferenceEndPoint = inferenceEndPoint;
	    return this;
	  }

	

	

	

	public InferenceAPIEndPointDto getInferenceEndPoint() {
		return inferenceEndPoint;
	}

	public void setInferenceEndPoint(InferenceAPIEndPointDto inferenceEndPoint) {
		this.inferenceEndPoint = inferenceEndPoint;
	}

	public ModelDto trainingDataset(TrainingDataset trainingDataset) {
	    this.trainingDataset = trainingDataset;
	    return this;
	  }


	    public TrainingDataset getTrainingDataset() {
	    return trainingDataset;
	  }

	  public void setTrainingDataset(TrainingDataset trainingDataset) {
	    this.trainingDataset = trainingDataset;
	  }


	  @Override
	  public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    ModelDto model = (ModelDto) o;
	    return Objects.equals(this.name, model.name) &&
	        Objects.equals(this.version, model.version) &&
	        Objects.equals(this.description, model.description) &&
	        Objects.equals(this.refUrl, model.refUrl) &&
	        Objects.equals(this.task, model.task) &&
	        Objects.equals(this.languages, model.languages) &&
	        Objects.equals(this.license, model.license) &&
	        Objects.equals(this.licenseUrl, model.licenseUrl) &&
	        Objects.equals(this.domain, model.domain) &&
	        Objects.equals(this.submitter, model.submitter) &&
	        Objects.equals(this.inferenceEndPoint, model.inferenceEndPoint) &&
	        Objects.equals(this.trainingDataset, model.trainingDataset);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(name, version, description, refUrl, task, languages, license, licenseUrl, domain, submitter, inferenceEndPoint, trainingDataset);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class Model {\n");
	    
	    sb.append("    name: ").append(toIndentedString(name)).append("\n");
	    sb.append("    version: ").append(toIndentedString(version)).append("\n");
	    sb.append("    description: ").append(toIndentedString(description)).append("\n");
	    sb.append("    refUrl: ").append(toIndentedString(refUrl)).append("\n");
	    sb.append("    task: ").append(toIndentedString(task)).append("\n");
	    sb.append("    languages: ").append(toIndentedString(languages)).append("\n");
	    sb.append("    license: ").append(toIndentedString(license)).append("\n");
	    sb.append("    licenseUrl: ").append(toIndentedString(licenseUrl)).append("\n");
	    sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
	    sb.append("    submitter: ").append(toIndentedString(submitter)).append("\n");
	    sb.append("    inferenceAPIEndPointDto: ").append(toIndentedString(inferenceEndPoint)).append("\n");
	    sb.append("    trainingDataset: ").append(toIndentedString(trainingDataset)).append("\n");
	    sb.append("}");
	    return sb.toString();
	  }

	  /**
	   * Convert the given object to string with each line indented by 4 spaces
	   * (except the first line).
	   */
	  private String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	  }
	

}
