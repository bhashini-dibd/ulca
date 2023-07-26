package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AudioFormat;
import io.swagger.model.ModelProcessingType;
import io.swagger.model.SupportedTasks;
import io.swagger.model.TTSRequest;
import io.swagger.model.TTSResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TTSInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T08:00:21.046011704Z[GMT]")


public class TTSInference  implements InferenceSchema {
  @JsonProperty("taskType")
  private SupportedTasks taskType = null;

  @JsonProperty("modelProcessingType")
  private ModelProcessingType modelProcessingType = null;

  @JsonProperty("supportedInputTextFormats")
  @Valid
  private List<TextFormat1> supportedInputTextFormats = null;

  @JsonProperty("supportedOutputAudioFormats")
  @Valid
  private List<AudioFormat> supportedOutputAudioFormats = null;

  @JsonProperty("request")
  private TTSRequest request = null;

  @JsonProperty("response")
  private TTSResponse response = null;

  public TTSInference taskType(SupportedTasks taskType) {
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

  public TTSInference supportedInputTextFormats(List<TextFormat1> supportedInputTextFormats) {
    this.supportedInputTextFormats = supportedInputTextFormats;
    return this;
  }

  public TTSInference addSupportedInputTextFormatsItem(TextFormat1 supportedInputTextFormatsItem) {
    if (this.supportedInputTextFormats == null) {
      this.supportedInputTextFormats = new ArrayList<TextFormat1>();
    }
    this.supportedInputTextFormats.add(supportedInputTextFormatsItem);
    return this;
  }

  /**
   * list of
   * @return supportedInputTextFormats
   **/
  @Schema(description = "list of")
      @Valid
    public List<TextFormat1> getSupportedInputTextFormats() {
    return supportedInputTextFormats;
  }

  public void setSupportedInputTextFormats(List<TextFormat1> supportedInputTextFormats) {
    this.supportedInputTextFormats = supportedInputTextFormats;
  }

  public TTSInference supportedOutputAudioFormats(List<AudioFormat> supportedOutputAudioFormats) {
    this.supportedOutputAudioFormats = supportedOutputAudioFormats;
    return this;
  }

  public TTSInference addSupportedOutputAudioFormatsItem(AudioFormat supportedOutputAudioFormatsItem) {
    if (this.supportedOutputAudioFormats == null) {
      this.supportedOutputAudioFormats = new ArrayList<AudioFormat>();
    }
    this.supportedOutputAudioFormats.add(supportedOutputAudioFormatsItem);
    return this;
  }

  /**
   * list of
   * @return supportedOutputAudioFormats
   **/
  @Schema(description = "list of")
      @Valid
    public List<AudioFormat> getSupportedOutputAudioFormats() {
    return supportedOutputAudioFormats;
  }

  public void setSupportedOutputAudioFormats(List<AudioFormat> supportedOutputAudioFormats) {
    this.supportedOutputAudioFormats = supportedOutputAudioFormats;
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
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TTSInference ttSInference = (TTSInference) o;
    return Objects.equals(this.taskType, ttSInference.taskType) &&
        Objects.equals(this.modelProcessingType, ttSInference.modelProcessingType) &&
        Objects.equals(this.supportedInputTextFormats, ttSInference.supportedInputTextFormats) &&
        Objects.equals(this.supportedOutputAudioFormats, ttSInference.supportedOutputAudioFormats) &&
        Objects.equals(this.request, ttSInference.request) &&
        Objects.equals(this.response, ttSInference.response);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskType, modelProcessingType, supportedInputTextFormats, supportedOutputAudioFormats, request, response);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TTSInference {\n");
    
    sb.append("    taskType: ").append(toIndentedString(taskType)).append("\n");
    sb.append("    modelProcessingType: ").append(toIndentedString(modelProcessingType)).append("\n");
    sb.append("    supportedInputTextFormats: ").append(toIndentedString(supportedInputTextFormats)).append("\n");
    sb.append("    supportedOutputAudioFormats: ").append(toIndentedString(supportedOutputAudioFormats)).append("\n");
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
