package com.ulca.benchmark.model;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

import com.ulca.benchmark.model.BenchmarkError;

/**
 * Task Tracker entity
 */
@Schema(description = "Task Tracker entity")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-08T12:36:29.236Z[GMT]")

@Document(collection = "ulca-bm-tasks")
public class BenchmarkTaskTracker   {
  @JsonProperty("benchmarkProcessId")
  private String benchmarkProcessId = null;

  /**
   * Tool updating this data
   */
  public enum ToolEnum {
    download("download"),
    ingest("ingest"),
    publish("benchmark");

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
  //@DateTimeFormat(unix timestamp millisec)
  private long startTime;

  @JsonProperty("endTime")
 // @DateTimeFormat(unix timestamp millisec)
  private long endTime;

  @JsonProperty("lastModified")
 // @DateTimeFormat(unix timestamp millisec)
  private long lastModified;

  @JsonProperty("error")
  private BenchmarkError error = null;

  public BenchmarkTaskTracker benchmarkProcessId(String benchmarkProcessId) {
    this.benchmarkProcessId = benchmarkProcessId;
    return this;
  }

  /**
   * Unique identifier of the service request
   * @return serviceRequestNumber
   **/
  @Schema(description = "Unique identifier of the service request")
  
    public String getBenchmarkProcessId() {
    return benchmarkProcessId;
  }

  public void setBenchmarkProcessId(String benchmarkProcessId) {
    this.benchmarkProcessId = benchmarkProcessId;
  }

  public BenchmarkTaskTracker tool(ToolEnum tool) {
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

  public BenchmarkTaskTracker status(String status) {
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

  public BenchmarkTaskTracker details(String details) {
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

  public BenchmarkTaskTracker startTime(long startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * ISO timestamp of the instance of the start of process
   * @return startTime
   **/
  @Schema(description = "ISO timestamp of the instance of the start of process")
  
    @Valid
    
    public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public BenchmarkTaskTracker endTime(long endTime) {
    this.endTime = endTime;
    return this;
  }

  /**
   * ISO timestamp of the instance of the end of process
   * @return endTime
   **/
  @Schema(description = "ISO timestamp of the instance of the end of process")
  
    @Valid
    public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  public BenchmarkTaskTracker lastModified(long lastModified) {
    this.lastModified = lastModified;
    return this;
  }

  /**
   * ISO timestamp of the instance of the end of process
   * @return lastModified
   **/
  @Schema(description = "ISO timestamp of the instance of the end of process")
  
    @Valid
    public long getLastModified() {
    return lastModified;
  }

  public void setLastModified(long lastModified) {
    this.lastModified = lastModified;
  }

  public BenchmarkTaskTracker error(BenchmarkError error) {
    this.error = error;
    return this;
  }

  /**
   * Get error
   * @return error
   **/
  @Schema(description = "")
  
    @Valid
    public BenchmarkError getError() {
    return error;
  }

  public void setError(BenchmarkError error) {
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
    BenchmarkTaskTracker taskTracker = (BenchmarkTaskTracker) o;
    return Objects.equals(this.benchmarkProcessId, taskTracker.benchmarkProcessId) &&
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
    return Objects.hash(benchmarkProcessId, tool, status, details, startTime, endTime, lastModified, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskTracker {\n");
    
    sb.append("    benchmarkProcessId: ").append(toIndentedString(benchmarkProcessId)).append("\n");
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