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


public class DetailsDelete   {
  @JsonProperty("count")
  private BigDecimal count = null;

  @JsonProperty("timeStamp")
  private BigDecimal timeStamp = null;

  public DetailsDelete count(BigDecimal count) {
    this.count = count;
    return this;
  }

  /**
   * Count of records deleted
   * @return count
   **/
  @Schema(description = "Count of records deleted")
  
    @Valid
    public BigDecimal getCount() {
    return count;
  }

  public void setCount(BigDecimal count) {
    this.count = count;
  }

  public DetailsDelete timeStamp(BigDecimal timeStamp) {
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
    DetailsDelete detailsDelete = (DetailsDelete) o;
    return Objects.equals(this.count, detailsDelete.count) &&
        Objects.equals(this.timeStamp, detailsDelete.timeStamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, timeStamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DetailsDelete {\n");
    
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
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
