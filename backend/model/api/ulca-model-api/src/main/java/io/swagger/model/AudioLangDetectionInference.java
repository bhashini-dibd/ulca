package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AudioLangDetectionRequest;
import io.swagger.model.AudioLangDetectionResponse;
import io.swagger.model.SupportedTasks;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AudioLangDetectionInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T03:55:25.562740452Z[GMT]")


public class AudioLangDetectionInference  implements OneOfInferenceAPIEndPointSchema {
  @JsonProperty("taskType")
  private SupportedTasks taskType = null;

  @JsonProperty("request")
  private AudioLangDetectionRequest request = null;

  @JsonProperty("response")
  private AudioLangDetectionResponse response = null;

  public AudioLangDetectionInference taskType(SupportedTasks taskType) {
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

  public AudioLangDetectionInference request(AudioLangDetectionRequest request) {
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
    public AudioLangDetectionRequest getRequest() {
    return request;
  }

  public void setRequest(AudioLangDetectionRequest request) {
    this.request = request;
  }

  public AudioLangDetectionInference response(AudioLangDetectionResponse response) {
    this.response = response;
    return this;
  }

  /**
   * Get response
   * @return response
   **/
  @Schema(description = "")
  
    @Valid
    public AudioLangDetectionResponse getResponse() {
    return response;
  }

  public void setResponse(AudioLangDetectionResponse response) {
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
    AudioLangDetectionInference audioLangDetectionInference = (AudioLangDetectionInference) o;
    return Objects.equals(this.taskType, audioLangDetectionInference.taskType) &&
        Objects.equals(this.request, audioLangDetectionInference.request) &&
        Objects.equals(this.response, audioLangDetectionInference.response);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskType, request, response);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AudioLangDetectionInference {\n");
    
    sb.append("    taskType: ").append(toIndentedString(taskType)).append("\n");
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
