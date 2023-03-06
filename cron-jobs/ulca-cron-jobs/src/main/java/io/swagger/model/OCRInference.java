package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.OCRRequest;
import io.swagger.model.OCRResponse;
import io.swagger.model.SupportedTasks;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OCRInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-02-15T10:08:37.438508852Z[GMT]")


public class OCRInference  implements OneOfInferenceAPIEndPointSchema {
  @JsonProperty("taskType")
  private SupportedTasks taskType = null;

  @JsonProperty("request")
  private OCRRequest request = null;

  @JsonProperty("response")
  private OCRResponse response = null;

  public OCRInference taskType(SupportedTasks taskType) {
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

  public OCRInference request(OCRRequest request) {
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
    public OCRRequest getRequest() {
    return request;
  }

  public void setRequest(OCRRequest request) {
    this.request = request;
  }

  public OCRInference response(OCRResponse response) {
    this.response = response;
    return this;
  }

  /**
   * Get response
   * @return response
   **/
  @Schema(description = "")
  
    @Valid
    public OCRResponse getResponse() {
    return response;
  }

  public void setResponse(OCRResponse response) {
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
    OCRInference ocRInference = (OCRInference) o;
    return Objects.equals(this.taskType, ocRInference.taskType) &&
        Objects.equals(this.request, ocRInference.request) &&
        Objects.equals(this.response, ocRInference.response);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskType, request, response);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OCRInference {\n");
    
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
