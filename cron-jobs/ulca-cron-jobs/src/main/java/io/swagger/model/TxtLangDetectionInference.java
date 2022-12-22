package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * TxtLangDetectionInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-09-04T16:26:09.493Z[GMT]")


public class TxtLangDetectionInference  implements OneOfInferenceAPIEndPointSchema {
  @JsonProperty("taskType")
  private ModelTask taskType = null;

  @JsonProperty("request")
  private TxtLangDetectionRequest request = null;

  @JsonProperty("response")
  private TxtLangDetectionResponse response = null;

  public TxtLangDetectionInference taskType(ModelTask taskType) {
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

  public TxtLangDetectionInference request(TxtLangDetectionRequest request) {
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
    public TxtLangDetectionRequest getRequest() {
    return request;
  }

  public void setRequest(TxtLangDetectionRequest request) {
    this.request = request;
  }

  public TxtLangDetectionInference response(TxtLangDetectionResponse response) {
    this.response = response;
    return this;
  }

  /**
   * Get response
   * @return response
   **/
  @Schema(description = "")
  
    @Valid
    public TxtLangDetectionResponse getResponse() {
    return response;
  }

  public void setResponse(TxtLangDetectionResponse response) {
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
    TxtLangDetectionInference txtLangDetectionInference = (TxtLangDetectionInference) o;
    return Objects.equals(this.taskType, txtLangDetectionInference.taskType) &&
        Objects.equals(this.request, txtLangDetectionInference.request) &&
        Objects.equals(this.response, txtLangDetectionInference.response);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskType, request, response);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TxtLangDetectionInference {\n");
    
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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
