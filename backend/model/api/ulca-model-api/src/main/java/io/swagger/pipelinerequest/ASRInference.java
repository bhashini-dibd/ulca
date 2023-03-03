package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.ASRRequest;
import io.swagger.model.ASRResponse;
import io.swagger.model.AudioFormat;
import io.swagger.model.ModelProcessingType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ASRInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T08:00:21.046011704Z[GMT]")


public class ASRInference  implements InferenceSchema {
  @JsonProperty("taskType")
  private SupportedTasks taskType = null;

  @JsonProperty("modelProcessingType")
  private ModelProcessingType modelProcessingType = null;

  @JsonProperty("supportedInputAudioFormats")
  @Valid
  private List<AudioFormat> supportedInputAudioFormats = null;

  @JsonProperty("supportedOutputTextFormats")
  @Valid
  private List<TextFormat1> supportedOutputTextFormats = null;

  @JsonProperty("request")
  private ASRRequest request = null;

  @JsonProperty("response")
  private ASRResponse response = null;

  public ASRInference taskType(SupportedTasks taskType) {
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

  public ASRInference supportedInputAudioFormats(List<AudioFormat> supportedInputAudioFormats) {
    this.supportedInputAudioFormats = supportedInputAudioFormats;
    return this;
  }

  public ASRInference addSupportedInputAudioFormatsItem(AudioFormat supportedInputAudioFormatsItem) {
    if (this.supportedInputAudioFormats == null) {
      this.supportedInputAudioFormats = new ArrayList<AudioFormat>();
    }
    this.supportedInputAudioFormats.add(supportedInputAudioFormatsItem);
    return this;
  }

  /**
   * list of
   * @return supportedInputAudioFormats
   **/
  @Schema(description = "list of")
      @Valid
    public List<AudioFormat> getSupportedInputAudioFormats() {
    return supportedInputAudioFormats;
  }

  public void setSupportedInputAudioFormats(List<AudioFormat> supportedInputAudioFormats) {
    this.supportedInputAudioFormats = supportedInputAudioFormats;
  }

  public ASRInference supportedOutputTextFormats(List<TextFormat1> supportedOutputTextFormats) {
    this.supportedOutputTextFormats = supportedOutputTextFormats;
    return this;
  }

  public ASRInference addSupportedOutputTextFormatsItem(TextFormat1 supportedOutputTextFormatsItem) {
    if (this.supportedOutputTextFormats == null) {
      this.supportedOutputTextFormats = new ArrayList<TextFormat1>();
    }
    this.supportedOutputTextFormats.add(supportedOutputTextFormatsItem);
    return this;
  }

  /**
   * list of
   * @return supportedOutputTextFormats
   **/
  @Schema(description = "list of")
      @Valid
    public List<TextFormat1> getSupportedOutputTextFormats() {
    return supportedOutputTextFormats;
  }

  public void setSupportedOutputTextFormats(List<TextFormat1> supportedOutputTextFormats) {
    this.supportedOutputTextFormats = supportedOutputTextFormats;
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
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ASRInference asRInference = (ASRInference) o;
    return Objects.equals(this.taskType, asRInference.taskType) &&
        Objects.equals(this.modelProcessingType, asRInference.modelProcessingType) &&
        Objects.equals(this.supportedInputAudioFormats, asRInference.supportedInputAudioFormats) &&
        Objects.equals(this.supportedOutputTextFormats, asRInference.supportedOutputTextFormats) &&
        Objects.equals(this.request, asRInference.request) &&
        Objects.equals(this.response, asRInference.response);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskType, modelProcessingType, supportedInputAudioFormats, supportedOutputTextFormats, request, response);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ASRInference {\n");
    
    sb.append("    taskType: ").append(toIndentedString(taskType)).append("\n");
    sb.append("    modelProcessingType: ").append(toIndentedString(modelProcessingType)).append("\n");
    sb.append("    supportedInputAudioFormats: ").append(toIndentedString(supportedInputAudioFormats)).append("\n");
    sb.append("    supportedOutputTextFormats: ").append(toIndentedString(supportedOutputTextFormats)).append("\n");
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
