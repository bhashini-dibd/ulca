package io.swagger.pipelinemodel;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PipelineInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T06:06:14.793576134Z[GMT]")


public class PipelineInference   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("version")
  private String version = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("domain")
  private Domain domain = null;

  @JsonProperty("refUrl")
  private String refUrl = null;

  @JsonProperty("submitter")
  private Submitter submitter = null;

  @JsonProperty("inferenceEndPoint")
  private InferenceAPIEndPoint inferenceEndPoint = null;

  @JsonProperty("supportedPipelines")
  private ListOfPipelines supportedPipelines = null;

  @JsonProperty("taskSpecifications")
  private TaskSpecifications taskSpecifications = null;

  public PipelineInference name(String name) {
    this.name = name;
    return this;
  }

  /**
   * model name that you want your users to see
   * @return name
   **/
  @Schema(example = "vakyansh asr model", required = true, description = "model name that you want your users to see")
      @NotNull

  @Size(min=3,max=100)   public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PipelineInference version(String version) {
    this.version = version;
    return this;
  }

  /**
   * version for the model
   * @return version
   **/
  @Schema(example = "v1", required = true, description = "version for the model")
      @NotNull

  @Size(min=1,max=20)   public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public PipelineInference description(String description) {
    this.description = description;
    return this;
  }

  /**
   * brief description about model, its goal, basically something sweet about it
   * @return description
   **/
  @Schema(example = "Speech recognition model for classroom lecture", required = true, description = "brief description about model, its goal, basically something sweet about it")
      @NotNull

  @Size(min=25,max=1000)   public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public PipelineInference domain(Domain domain) {
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

  public PipelineInference refUrl(String refUrl) {
    this.refUrl = refUrl;
    return this;
  }

  /**
   * github link or url giving further info about the model
   * @return refUrl
   **/
  @Schema(example = "https://github.com/Open-Speech-EkStep/vakyansh-models", required = true, description = "github link or url giving further info about the model")
      @NotNull

  @Size(min=5,max=200)   public String getRefUrl() {
    return refUrl;
  }

  public void setRefUrl(String refUrl) {
    this.refUrl = refUrl;
  }

  public PipelineInference submitter(Submitter submitter) {
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

  public PipelineInference inferenceEndPoint(InferenceAPIEndPoint inferenceEndPoint) {
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

  public PipelineInference supportedPipelines(ListOfPipelines supportedPipelines) {
    this.supportedPipelines = supportedPipelines;
    return this;
  }

  /**
   * Get supportedPipelines
   * @return supportedPipelines
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ListOfPipelines getSupportedPipelines() {
    return supportedPipelines;
  }

  public void setSupportedPipelines(ListOfPipelines supportedPipelines) {
    this.supportedPipelines = supportedPipelines;
  }

  public PipelineInference taskSpecifications(TaskSpecifications taskSpecifications) {
    this.taskSpecifications = taskSpecifications;
    return this;
  }

  /**
   * Get taskSpecifications
   * @return taskSpecifications
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public TaskSpecifications getTaskSpecifications() {
    return taskSpecifications;
  }

  public void setTaskSpecifications(TaskSpecifications taskSpecifications) {
    this.taskSpecifications = taskSpecifications;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PipelineInference pipelineInference = (PipelineInference) o;
    return Objects.equals(this.name, pipelineInference.name) &&
        Objects.equals(this.version, pipelineInference.version) &&
        Objects.equals(this.description, pipelineInference.description) &&
        Objects.equals(this.domain, pipelineInference.domain) &&
        Objects.equals(this.refUrl, pipelineInference.refUrl) &&
        Objects.equals(this.submitter, pipelineInference.submitter) &&
        Objects.equals(this.inferenceEndPoint, pipelineInference.inferenceEndPoint) &&
        Objects.equals(this.supportedPipelines, pipelineInference.supportedPipelines) &&
        Objects.equals(this.taskSpecifications, pipelineInference.taskSpecifications);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, version, description, domain, refUrl, submitter, inferenceEndPoint, supportedPipelines, taskSpecifications);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PipelineInference {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
    sb.append("    refUrl: ").append(toIndentedString(refUrl)).append("\n");
    sb.append("    submitter: ").append(toIndentedString(submitter)).append("\n");
    sb.append("    inferenceEndPoint: ").append(toIndentedString(inferenceEndPoint)).append("\n");
    sb.append("    supportedPipelines: ").append(toIndentedString(supportedPipelines)).append("\n");
    sb.append("    taskSpecifications: ").append(toIndentedString(taskSpecifications)).append("\n");
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
