package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.ProcessStage;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * stage life-cycle of model before it gets published
 */
@Schema(description = "stage life-cycle of model before it gets published")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:21:00.339Z[GMT]")


public class Timeline   {
  @JsonProperty("stage")
  private ProcessStage stage = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("timestamp")
  private String timestamp = null;

  /**
   * status of each stage
   */
  public enum StatusEnum {
    IN_PROGRESS("in-progress"),
    
    STATUS_WITH_ERRORS("status-with-errors"),
    
    SUCCESS("success"),
    
    FAILED("failed");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("status")
  private StatusEnum status = null;

  public Timeline stage(ProcessStage stage) {
    this.stage = stage;
    return this;
  }

  /**
   * Get stage
   * @return stage
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ProcessStage getStage() {
    return stage;
  }

  public void setStage(ProcessStage stage) {
    this.stage = stage;
  }

  public Timeline message(String message) {
    this.message = message;
    return this;
  }

  /**
   * human readable message attached for each stage change
   * @return message
   **/
  @Schema(required = true, description = "human readable message attached for each stage change")
      @NotNull

    public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Timeline timestamp(String timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * timestamp of stage changes
   * @return timestamp
   **/
  @Schema(required = true, description = "timestamp of stage changes")
      @NotNull

    public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public Timeline status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * status of each stage
   * @return status
   **/
  @Schema(required = true, description = "status of each stage")
      @NotNull

    public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Timeline timeline = (Timeline) o;
    return Objects.equals(this.stage, timeline.stage) &&
        Objects.equals(this.message, timeline.message) &&
        Objects.equals(this.timestamp, timeline.timestamp) &&
        Objects.equals(this.status, timeline.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stage, message, timestamp, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Timeline {\n");
    
    sb.append("    stage: ").append(toIndentedString(stage)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
