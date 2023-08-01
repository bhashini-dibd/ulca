package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.NerResponse;
import io.swagger.model.SupportedTagsFormat;
import io.swagger.model.SupportedTasks;
import io.swagger.model.TranslationRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NerInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T08:00:21.046011704Z[GMT]")


public class NerInference  implements InferenceSchema {
  @JsonProperty("taskType")
  private SupportedTasks taskType = null;

  @JsonProperty("tagsFormat")
  private SupportedTagsFormat tagsFormat = null;

  @JsonProperty("request")
  private TranslationRequest request = null;

  @JsonProperty("response")
  private NerResponse response = null;

  public NerInference taskType(SupportedTasks taskType) {
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

  public NerInference tagsFormat(SupportedTagsFormat tagsFormat) {
    this.tagsFormat = tagsFormat;
    return this;
  }

  /**
   * Get tagsFormat
   * @return tagsFormat
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public SupportedTagsFormat getTagsFormat() {
    return tagsFormat;
  }

  public void setTagsFormat(SupportedTagsFormat tagsFormat) {
    this.tagsFormat = tagsFormat;
  }

  public NerInference request(TranslationRequest request) {
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
    public TranslationRequest getRequest() {
    return request;
  }

  public void setRequest(TranslationRequest request) {
    this.request = request;
  }

  public NerInference response(NerResponse response) {
    this.response = response;
    return this;
  }

  /**
   * Get response
   * @return response
   **/
  @Schema(description = "")
  
    @Valid
    public NerResponse getResponse() {
    return response;
  }

  public void setResponse(NerResponse response) {
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
    NerInference nerInference = (NerInference) o;
    return Objects.equals(this.taskType, nerInference.taskType) &&
        Objects.equals(this.tagsFormat, nerInference.tagsFormat) &&
        Objects.equals(this.request, nerInference.request) &&
        Objects.equals(this.response, nerInference.response);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskType, tagsFormat, request, response);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NerInference {\n");
    
    sb.append("    taskType: ").append(toIndentedString(taskType)).append("\n");
    sb.append("    tagsFormat: ").append(toIndentedString(tagsFormat)).append("\n");
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
