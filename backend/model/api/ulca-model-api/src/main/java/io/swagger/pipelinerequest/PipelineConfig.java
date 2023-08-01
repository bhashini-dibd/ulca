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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-27T05:34:08.695093421Z[GMT]")


public class PipelineConfig   {
  @JsonProperty("pipelineId")
  private String pipelineId = null;

  public PipelineConfig pipelineId(String pipelineId) {
    this.pipelineId = pipelineId;
    return this;
  }

  /**
   * ID of the pipeline submitter
   * @return pipelineId
   **/
  @Schema(example = "AI4BharatID", required = true, description = "ID of the pipeline submitter")
      @NotNull

    public String getPipelineId() {
    return pipelineId;
  }

  public void setPipelineId(String pipelineId) {
    this.pipelineId = pipelineId;
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
    return Objects.equals(this.pipelineId, pipelineConfig.pipelineId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pipelineId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PipelineConfig {\n");
    
    sb.append("    pipelineId: ").append(toIndentedString(pipelineId)).append("\n");
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
