package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.SupportedTasks;
import io.swagger.model.TranslationRequest;
import io.swagger.model.TranslationResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TNInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-10-07T07:56:29.862452554Z[GMT]")


public class TNInference  implements OneOfInferenceAPIEndPointSchema {
  @JsonProperty("taskType")

  private SupportedTasks taskType = null;

  @JsonProperty("request")

  private TranslationRequest request = null;

  @JsonProperty("response")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private TranslationResponse response = null;


  public TNInference taskType(SupportedTasks taskType) { 

    this.taskType = taskType;
    return this;
  }

  /**
   * Get taskType
   * @return taskType
   **/
  
  @Schema(required = true, description = "")
  
@Valid
  @NotNull
  public SupportedTasks getTaskType() {  
    return taskType;
  }



  public void setTaskType(SupportedTasks taskType) { 

    this.taskType = taskType;
  }

  public TNInference request(TranslationRequest request) { 

    this.request = request;
    return this;
  }

  /**
   * Get request
   * @return request
   **/
  
  @Schema(required = true, description = "")
  
@Valid
  @NotNull
  public TranslationRequest getRequest() {  
    return request;
  }



  public void setRequest(TranslationRequest request) { 

    this.request = request;
  }

  public TNInference response(TranslationResponse response) { 

    this.response = response;
    return this;
  }

  /**
   * Get response
   * @return response
   **/
  
  @Schema(description = "")
  
@Valid
  public TranslationResponse getResponse() {  
    return response;
  }



  public void setResponse(TranslationResponse response) { 
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
    TNInference tnInference = (TNInference) o;
    return Objects.equals(this.taskType, tnInference.taskType) &&
        Objects.equals(this.request, tnInference.request) &&
        Objects.equals(this.response, tnInference.response);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskType, request, response);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TNInference {\n");
    
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
