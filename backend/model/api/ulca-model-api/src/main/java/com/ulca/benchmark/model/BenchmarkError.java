package com.ulca.benchmark.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Task Tracker entity
 */
@Schema(description = "Benchmark Task Tracker Error entity")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-08T12:36:29.236Z[GMT]")


public class BenchmarkError   {
  @JsonProperty("code")
  private String code = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("cause")
  private String cause = null;

  @JsonProperty("report")
  private String report = null;

  public BenchmarkError code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Code of the error
   * @return code
   **/
  @Schema(description = "Code of the error")
  
    public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public BenchmarkError message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Description of the error
   * @return message
   **/
  @Schema(description = "Description of the error")
  
    public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public BenchmarkError cause(String cause) {
    this.cause = cause;
    return this;
  }

  /**
   * Additional details related to the cause of the error
   * @return cause
   **/
  @Schema(description = "Additional details related to the cause of the error")
  
    public Object getCause() {
    return cause;
  }

  public void setCause(String cause) {
    this.cause = cause;
  }

  public BenchmarkError report(String report) {
    this.report = report;
    return this;
  }

  /**
   * Link to the error report file
   * @return report
   **/
  @Schema(description = "Link to the error report file")
  
    public String getReport() {
    return report;
  }

  public void setReport(String report) {
    this.report = report;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BenchmarkError error = (BenchmarkError) o;
    return Objects.equals(this.code, error.code) &&
        Objects.equals(this.message, error.message) &&
        Objects.equals(this.cause, error.cause) &&
        Objects.equals(this.report, error.report);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message, cause, report);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    cause: ").append(toIndentedString(cause)).append("\n");
    sb.append("    report: ").append(toIndentedString(report)).append("\n");
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
