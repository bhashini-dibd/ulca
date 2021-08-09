package com.ulca.dataset.model;

import java.util.Date;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Task Tracker entity
 */
@Schema(description = "Task Tracker entity")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-08T12:36:29.236Z[GMT]")

@Document(collection = "ulca-pt-tasks")
public class TaskTracker   {
  @JsonProperty("serviceRequestNumber")
  private String serviceRequestNumber = null;

  /**
   * Tool updating this data
   */
  public enum ToolEnum {
    download("download"),
    pseudo("pseudo"),
    ingest("ingest"),
    
    validate("validate"),
    
    publish("publish"),
    
    search("search"),
    
    delete("delete");

    private String value;

    ToolEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ToolEnum fromValue(String text) {
      for (ToolEnum b : ToolEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("tool")
  private ToolEnum tool = null;

  /**
   * Status of the task
   */
  public enum StatusEnum {
    pending("Pending"),
    
    inprogress("In-Progress"),
    
    completed("Completed"),
    
    failed("Failed"),
	na("N/A");

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
  
  @Id
  @JsonProperty("id")
  private String id = null;
  
  @JsonProperty("status")
  private String status = null;

  @JsonProperty("details")
  private String details = null;

  @JsonProperty("startTime")
  //@DateTimeFormat(iso=ISO.DATE_TIME)
  private String startTime = null;

  @JsonProperty("endTime")
 // @DateTimeFormat(iso=ISO.DATE_TIME)
  private String endTime = null;

  @JsonProperty("lastModified")
 // @DateTimeFormat(iso=ISO.DATE_TIME)
  private String lastModified = null;

  @JsonProperty("error")
  private Error error = null;

  public TaskTracker serviceRequestNumber(String serviceRequestNumber) {
    this.serviceRequestNumber = serviceRequestNumber;
    return this;
  }

  /**
   * Unique identifier of the service request
   * @return serviceRequestNumber
   **/
  @Schema(description = "Unique identifier of the service request")
  
    public String getServiceRequestNumber() {
    return serviceRequestNumber;
  }

  public void setServiceRequestNumber(String serviceRequestNumber) {
    this.serviceRequestNumber = serviceRequestNumber;
  }

  public TaskTracker tool(ToolEnum tool) {
    this.tool = tool;
    return this;
  }

  /**
   * Tool updating this data
   * @return tool
   **/
  @Schema(description = "Tool updating this data")
  
    public ToolEnum getTool() {
    return tool;
  }

  public void setTool(ToolEnum tool) {
    this.tool = tool;
  }

  public TaskTracker status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Status of the task
   * @return status
   **/
  @Schema(description = "Status of the task")
  
    public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public TaskTracker details(String details) {
    this.details = details;
    return this;
  }

  /**
   * Details of the current status of the task
   * @return details
   **/
  @Schema(description = "Details of the current status of the task")
  
    public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public TaskTracker startTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * ISO timestamp of the instance of the start of process
   * @return startTime
   **/
  @Schema(description = "ISO timestamp of the instance of the start of process")
  
    @Valid
    
    public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public TaskTracker endTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

  /**
   * ISO timestamp of the instance of the end of process
   * @return endTime
   **/
  @Schema(description = "ISO timestamp of the instance of the end of process")
  
    @Valid
    public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public TaskTracker lastModified(String lastModified) {
    this.lastModified = lastModified;
    return this;
  }

  /**
   * ISO timestamp of the instance of the end of process
   * @return lastModified
   **/
  @Schema(description = "ISO timestamp of the instance of the end of process")
  
    @Valid
    public String getLastModified() {
    return lastModified;
  }

  public void setLastModified(String lastModified) {
    this.lastModified = lastModified;
  }

  public TaskTracker error(Error error) {
    this.error = error;
    return this;
  }

  /**
   * Get error
   * @return error
   **/
  @Schema(description = "")
  
    @Valid
    public Error getError() {
    return error;
  }

  public void setError(Error error) {
    this.error = error;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskTracker taskTracker = (TaskTracker) o;
    return Objects.equals(this.serviceRequestNumber, taskTracker.serviceRequestNumber) &&
        Objects.equals(this.tool, taskTracker.tool) &&
        Objects.equals(this.status, taskTracker.status) &&
        Objects.equals(this.details, taskTracker.details) &&
        Objects.equals(this.startTime, taskTracker.startTime) &&
        Objects.equals(this.endTime, taskTracker.endTime) &&
        Objects.equals(this.lastModified, taskTracker.lastModified) &&
        Objects.equals(this.error, taskTracker.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceRequestNumber, tool, status, details, startTime, endTime, lastModified, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskTracker {\n");
    
    sb.append("    serviceRequestNumber: ").append(toIndentedString(serviceRequestNumber)).append("\n");
    sb.append("    tool: ").append(toIndentedString(tool)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    details: ").append(toIndentedString(details)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    lastModified: ").append(toIndentedString(lastModified)).append("\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
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
