package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PipelineConfig
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T08:00:21.046011704Z[GMT]")


public class PipelineConfig   {
  @JsonProperty("submitter")
  private String submitter = null;

  public PipelineConfig submitter(String submitter) {
    this.submitter = submitter;
    return this;
  }

  /**
   * Submitter Name
   * @return submitter
   **/
  @Schema(example = "AI4Bharat", required = true, description = "Submitter Name")
      @NotNull

    public String getSubmitter() {
    return submitter;
  }

  public void setSubmitter(String submitter) {
    this.submitter = submitter;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PipelineConfig pipelineConfig = (PipelineConfig) o;
    return Objects.equals(this.submitter, pipelineConfig.submitter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(submitter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PipelineConfig {\n");
    
    sb.append("    submitter: ").append(toIndentedString(submitter)).append("\n");
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
