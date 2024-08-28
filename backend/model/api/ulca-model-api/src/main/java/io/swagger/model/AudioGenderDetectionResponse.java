package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AudioGenderDetectionOutput;
import io.swagger.model.SupportedTasks;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the response for translation.  Standard http status codes to be used.
 */
@Schema(description = "the response for translation.  Standard http status codes to be used.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-06-13T12:41:05.989770988Z[GMT]")


public class AudioGenderDetectionResponse   {
  @JsonProperty("output")
  private AudioGenderDetectionOutput output = null;

  @JsonProperty("taskType")
  private SupportedTasks taskType = null;

  public AudioGenderDetectionResponse output(AudioGenderDetectionOutput output) {
    this.output = output;
    return this;
  }

  /**
   * Get output
   * @return output
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public AudioGenderDetectionOutput getOutput() {
    return output;
  }

  public void setOutput(AudioGenderDetectionOutput output) {
    this.output = output;
  }

  public AudioGenderDetectionResponse taskType(SupportedTasks taskType) {
    this.taskType = taskType;
    return this;
  }

  /**
   * Get taskType
   * @return taskType
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public SupportedTasks getTaskType() {
    return taskType;
  }

  public void setTaskType(SupportedTasks taskType) {
    this.taskType = taskType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AudioGenderDetectionResponse audioGenderDetectionResponse = (AudioGenderDetectionResponse) o;
    return Objects.equals(this.output, audioGenderDetectionResponse.output) &&
        Objects.equals(this.taskType, audioGenderDetectionResponse.taskType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(output, taskType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AudioGenderDetectionResponse {\n");
    
    sb.append("    output: ").append(toIndentedString(output)).append("\n");
    sb.append("    taskType: ").append(toIndentedString(taskType)).append("\n");
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
