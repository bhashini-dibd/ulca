package com.ulca.dataset.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * Task Tracker entity
 */
@Schema(description = "Task Tracker entity")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-08T12:36:29.236Z[GMT]")


public class Error   {
  @JsonProperty("code")
  private String code = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("cause")
  private Object cause = null;

  @JsonProperty("report")
  private String report = null;

  public Error code(String code) {
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

  public Error message(String message) {
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

  public Error cause(Object cause) {
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

  public void setCause(Object cause) {
    this.cause = cause;
  }

  public Error report(String report) {
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Error error = (Error) o;
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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
