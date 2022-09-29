package com.ulca.dataset.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Task Tracker entity
 */
@Schema(description = "Task Tracker entity")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-08T12:36:29.236Z[GMT]")


public class DetailsSubmit   {
  @JsonProperty("currentRecordIndex")
  private BigDecimal currentRecordIndex = null;

  @JsonProperty("processedCount")
  private ProcessedCount processedCount = null;

  @JsonProperty("timeStamp")
  private BigDecimal timeStamp = null;

  public DetailsSubmit currentRecordIndex(BigDecimal currentRecordIndex) {
    this.currentRecordIndex = currentRecordIndex;
    return this;
  }

  /**
   * Index of the record under processing
   * @return currentRecordIndex
   **/
  @Schema(description = "Index of the record under processing")
  
    @Valid
    public BigDecimal getCurrentRecordIndex() {
    return currentRecordIndex;
  }

  public void setCurrentRecordIndex(BigDecimal currentRecordIndex) {
    this.currentRecordIndex = currentRecordIndex;
  }

  public DetailsSubmit processedCount(ProcessedCount processedCount) {
    this.processedCount = processedCount;
    return this;
  }

  /**
   * Get processedCount
   * @return processedCount
   **/
  @Schema(description = "")
  
    @Valid
    public ProcessedCount getProcessedCount() {
    return processedCount;
  }

  public void setProcessedCount(ProcessedCount processedCount) {
    this.processedCount = processedCount;
  }

  public DetailsSubmit timeStamp(BigDecimal timeStamp) {
    this.timeStamp = timeStamp;
    return this;
  }

  /**
   * ISO timestamp of the instance of the start of process
   * @return timeStamp
   **/
  @Schema(description = "ISO timestamp of the instance of the start of process")
  
    @Valid
    public BigDecimal getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(BigDecimal timeStamp) {
    this.timeStamp = timeStamp;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DetailsSubmit detailsSubmit = (DetailsSubmit) o;
    return Objects.equals(this.currentRecordIndex, detailsSubmit.currentRecordIndex) &&
        Objects.equals(this.processedCount, detailsSubmit.processedCount) &&
        Objects.equals(this.timeStamp, detailsSubmit.timeStamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currentRecordIndex, processedCount, timeStamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DetailsSubmit {\n");
    
    sb.append("    currentRecordIndex: ").append(toIndentedString(currentRecordIndex)).append("\n");
    sb.append("    processedCount: ").append(toIndentedString(processedCount)).append("\n");
    sb.append("    timeStamp: ").append(toIndentedString(timeStamp)).append("\n");
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
