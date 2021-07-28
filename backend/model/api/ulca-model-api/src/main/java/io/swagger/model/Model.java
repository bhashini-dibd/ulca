package io.swagger.model;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Model
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-07-26T10:42:04.802Z[GMT]")

@Document(collection= "model")
public class Model   {
	
   @Id
   @JsonProperty("modelId")
   private String modelId;
   
   @JsonProperty("submitterId")
   private String submitterId;
   
  @Indexed(unique=true)
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("task")
  private ModelTask task = null;

  @JsonProperty("languages")
  private LanguagePairs languages = null;

  @JsonProperty("license")
  private License license = null;

  @JsonProperty("domain")
  private Domain domain = null;

  @JsonProperty("submitter")
  private Submitter submitter = null;

  @JsonProperty("inferenceEndPoint")
  private InferenceAPIEndPoint inferenceEndPoint = null;

  @JsonProperty("trainingDataset")
  private TrainingDataset trainingDataset = null;
  
  public Model modelId(String modelId) {
	    this.modelId = modelId;
	    return this;
	  }
  
  public String getModelId() {
	    return modelId;
	  }
  public void setModelId(String modelId) {
	    this.modelId = modelId;
	  }
  
  public Model submitterId(String submitterId) {
	    this.submitterId = submitterId;
	    return this;
	  }

  public String getSubmitterId() {
	    return submitterId;
	  }
  public void setSubmitterId(String submitterId) {
	    this.submitterId = submitterId;
	  }


  public Model name(String name) {
    this.name = name;
    return this;
  }

  /**
   * model name that you want your users to see
   * @return name
   **/
  @Schema(required = true, description = "model name that you want your users to see")
      @NotNull

  @Size(min=10,max=100)   public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  
  public Model description(String description) {
    this.description = description;
    return this;
  }

  /**
   * brief description about model, its goal, basically something sweet about it
   * @return description
   **/
  @Schema(example = "Speech recognition model for classroom lecture", required = true, description = "brief description about model, its goal, basically something sweet about it")
      @NotNull

  @Size(min=50,max=200)   public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Model task(ModelTask task) {
    this.task = task;
    return this;
  }

  /**
   * Get task
   * @return task
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ModelTask getTask() {
    return task;
  }

  public void setTask(ModelTask task) {
    this.task = task;
  }

  public Model languages(LanguagePairs languages) {
    this.languages = languages;
    return this;
  }

  /**
   * Get languages
   * @return languages
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public LanguagePairs getLanguages() {
    return languages;
  }

  public void setLanguages(LanguagePairs languages) {
    this.languages = languages;
  }

  public Model license(License license) {
    this.license = license;
    return this;
  }

  /**
   * Get license
   * @return license
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public License getLicense() {
    return license;
  }

  public void setLicense(License license) {
    this.license = license;
  }

  public Model domain(Domain domain) {
    this.domain = domain;
    return this;
  }

  /**
   * Get domain
   * @return domain
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public Domain getDomain() {
    return domain;
  }

  public void setDomain(Domain domain) {
    this.domain = domain;
  }

  public Model submitter(Submitter submitter) {
    this.submitter = submitter;
    return this;
  }

  /**
   * Get submitter
   * @return submitter
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public Submitter getSubmitter() {
    return submitter;
  }

  public void setSubmitter(Submitter submitter) {
    this.submitter = submitter;
  }

  public Model inferenceEndPoint(InferenceAPIEndPoint inferenceEndPoint) {
    this.inferenceEndPoint = inferenceEndPoint;
    return this;
  }

  /**
   * Get inferenceEndPoint
   * @return inferenceEndPoint
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public InferenceAPIEndPoint getInferenceEndPoint() {
    return inferenceEndPoint;
  }

  public void setInferenceEndPoint(InferenceAPIEndPoint inferenceEndPoint) {
    this.inferenceEndPoint = inferenceEndPoint;
  }

  public Model trainingDataset(TrainingDataset trainingDataset) {
    this.trainingDataset = trainingDataset;
    return this;
  }

  /**
   * Get trainingDataset
   * @return trainingDataset
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
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
    Model model = (Model) o;
    return Objects.equals(this.name, model.name) &&
        Objects.equals(this.description, model.description) &&
        Objects.equals(this.task, model.task) &&
        Objects.equals(this.languages, model.languages) &&
        Objects.equals(this.license, model.license) &&
        Objects.equals(this.domain, model.domain) &&
        Objects.equals(this.submitter, model.submitter) &&
        Objects.equals(this.inferenceEndPoint, model.inferenceEndPoint) &&
        Objects.equals(this.trainingDataset, model.trainingDataset);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, task, languages, license, domain, submitter, inferenceEndPoint, trainingDataset);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Model {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    task: ").append(toIndentedString(task)).append("\n");
    sb.append("    languages: ").append(toIndentedString(languages)).append("\n");
    sb.append("    license: ").append(toIndentedString(license)).append("\n");
    sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
    sb.append("    submitter: ").append(toIndentedString(submitter)).append("\n");
    sb.append("    inferenceEndPoint: ").append(toIndentedString(inferenceEndPoint)).append("\n");
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
