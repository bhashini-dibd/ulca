package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.ImgLangDetectionResponse;
import io.swagger.model.OCRRequest;
import io.swagger.model.SupportedTasks;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ImgLangDetectionInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-04-16T09:37:42.477810161Z[GMT]")


public class ImgLangDetectionInference  implements OneOfInferenceAPIEndPointSchema {
  @JsonProperty("taskType")

  private SupportedTasks taskType = null;

  @JsonProperty("request")

  private OCRRequest request = null;

  @JsonProperty("response")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private ImgLangDetectionResponse response = null;


  public ImgLangDetectionInference taskType(SupportedTasks taskType) { 

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

  public ImgLangDetectionInference request(OCRRequest request) { 

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
  public OCRRequest getRequest() {  
    return request;
  }



  public void setRequest(OCRRequest request) { 

    this.request = request;
  }

  public ImgLangDetectionInference response(ImgLangDetectionResponse response) { 

    this.response = response;
    return this;
  }

  /**
   * Get response
   * @return response
   **/
  
  @Schema(description = "")
  
@Valid
  public ImgLangDetectionResponse getResponse() {  
    return response;
  }



  public void setResponse(ImgLangDetectionResponse response) { 
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
    ImgLangDetectionInference imgLangDetectionInference = (ImgLangDetectionInference) o;
    return Objects.equals(this.taskType, imgLangDetectionInference.taskType) &&
        Objects.equals(this.request, imgLangDetectionInference.request) &&
        Objects.equals(this.response, imgLangDetectionInference.response);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskType, request, response);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ImgLangDetectionInference {\n");
    
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
