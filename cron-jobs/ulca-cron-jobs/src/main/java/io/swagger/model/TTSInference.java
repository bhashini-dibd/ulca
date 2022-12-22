package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * TTSInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-10-21T11:20:10.991Z[GMT]")


public class TTSInference  implements OneOfInferenceAPIEndPointSchema {
  @JsonProperty("taskType")
  private ModelTask taskType = null;

  @JsonProperty("modelProcessingType")
  private ModelProcessingType modelProcessingType = null;

  @JsonProperty("request")
  private TTSRequest request = null;

  @JsonProperty("response")
  private TTSResponse response = null;

  public TTSInference taskType(ModelTask taskType) {
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
  public ModelTask getTaskType() {
    return taskType;
  }

  public void setTaskType(ModelTask taskType) {
    this.taskType = taskType;
  }

  public TTSInference modelProcessingType(ModelProcessingType modelProcessingType) {
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

  public TTSInference request(TTSRequest request) {
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
  public TTSRequest getRequest() {
    return request;
  }

  public void setRequest(TTSRequest request) {
    this.request = request;
  }

  public TTSInference response(TTSResponse response) {
    this.response = response;
    return this;
  }

  /**
   * Get response
   * @return response
   **/
  @Schema(description = "")

  @Valid
  public TTSResponse getResponse() {
    return response;
  }

  public void setResponse(TTSResponse response) {
    this.response = response;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TTSInference ttSInference = (TTSInference) o;
    return Objects.equals(this.taskType, ttSInference.taskType) &&
            Objects.equals(this.modelProcessingType, ttSInference.modelProcessingType) &&
            Objects.equals(this.request, ttSInference.request) &&
            Objects.equals(this.response, ttSInference.response);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskType, modelProcessingType, request, response);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TTSInference {\n");

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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
