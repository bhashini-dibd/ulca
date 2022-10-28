package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * ASRInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-10-21T11:20:10.991Z[GMT]")


public class ASRInference  implements OneOfInferenceAPIEndPointSchema {
  @JsonProperty("taskType")
  private ModelTask taskType = null;

  @JsonProperty("modelProcessingType")
  private ModelProcessingType modelProcessingType = null;

  @JsonProperty("request")
  private ASRRequest request = null;

  @JsonProperty("response")
  private ASRResponse response = null;

  public ASRInference taskType(ModelTask taskType) {
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

  public ASRInference modelProcessingType(ModelProcessingType modelProcessingType) {
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

  public ASRInference request(ASRRequest request) {
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
  public ASRRequest getRequest() {
    return request;
  }

  public void setRequest(ASRRequest request) {
    this.request = request;
  }

  public ASRInference response(ASRResponse response) {
    this.response = response;
    return this;
  }

  /**
   * Get response
   * @return response
   **/
  @Schema(description = "")

  @Valid
  public ASRResponse getResponse() {
    return response;
  }

  public void setResponse(ASRResponse response) {
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
    ASRInference asRInference = (ASRInference) o;
    return Objects.equals(this.taskType, asRInference.taskType) &&
            Objects.equals(this.modelProcessingType, asRInference.modelProcessingType) &&
            Objects.equals(this.request, asRInference.request) &&
            Objects.equals(this.response, asRInference.response);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskType, modelProcessingType, request, response);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ASRInference {\n");

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
