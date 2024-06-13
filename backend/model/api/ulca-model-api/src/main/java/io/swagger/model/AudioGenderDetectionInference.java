package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AudioGenderDetectionRequest;
import io.swagger.model.AudioGenderDetectionResponse;
import io.swagger.model.ModelProcessingType;
import io.swagger.model.SupportedTasks;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AudioGenderDetectionInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-06-13T12:41:05.989770988Z[GMT]")


public class AudioGenderDetectionInference  implements OneOfInferenceAPIEndPointSchema {
  @JsonProperty("taskType")
  private SupportedTasks taskType = null;

  @JsonProperty("modelProcessingType")
  private ModelProcessingType modelProcessingType = null;

  @JsonProperty("request")
  private AudioGenderDetectionRequest request = null;

  @JsonProperty("response")
  private AudioGenderDetectionResponse response = null;

  public AudioGenderDetectionInference taskType(SupportedTasks taskType) {
    this.taskType = taskType;
    return this;
  }

  /**
   * Get taskType
   * @return taskType
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public SupportedTasks getTaskType() {
    return taskType;
  }

  public void setTaskType(SupportedTasks taskType) {
    this.taskType = taskType;
  }

  public AudioGenderDetectionInference modelProcessingType(ModelProcessingType modelProcessingType) {
    this.modelProcessingType = modelProcessingType;
    return this;
  }

  /**
   * Get modelProcessingType
   * @return modelProcessingType
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ModelProcessingType getModelProcessingType() {
    return modelProcessingType;
  }

  public void setModelProcessingType(ModelProcessingType modelProcessingType) {
    this.modelProcessingType = modelProcessingType;
  }

  public AudioGenderDetectionInference request(AudioGenderDetectionRequest request) {
    this.request = request;
    return this;
  }

  /**
   * Get request
   * @return request
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public AudioGenderDetectionRequest getRequest() {
    return request;
  }

  public void setRequest(AudioGenderDetectionRequest request) {
    this.request = request;
  }

  public AudioGenderDetectionInference response(AudioGenderDetectionResponse response) {
    this.response = response;
    return this;
  }

  /**
   * Get response
   * @return response
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public AudioGenderDetectionResponse getResponse() {
    return response;
  }

  public void setResponse(AudioGenderDetectionResponse response) {
    this.response = response;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AudioGenderDetectionInference audioGenderDetectionInference = (AudioGenderDetectionInference) o;
    return Objects.equals(this.taskType, audioGenderDetectionInference.taskType) &&
        Objects.equals(this.modelProcessingType, audioGenderDetectionInference.modelProcessingType) &&
        Objects.equals(this.request, audioGenderDetectionInference.request) &&
        Objects.equals(this.response, audioGenderDetectionInference.response);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskType, modelProcessingType, request, response);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AudioGenderDetectionInference {\n");
    
    sb.append("    taskType: ").append(toIndentedString(taskType)).append("\n");
    sb.append("    modelProcessingType: ").append(toIndentedString(modelProcessingType)).append("\n");
    sb.append("    request: ").append(toIndentedString(request)).append("\n");
    sb.append("    response: ").append(toIndentedString(response)).append("\n");
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
