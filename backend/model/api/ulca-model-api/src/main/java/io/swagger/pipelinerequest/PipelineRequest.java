package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PipelineRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-04T10:09:49.734767395Z[GMT]")


public class PipelineRequest   {
  @JsonProperty("pipelineTasks")
  private PipelineTasks pipelineTasks = null;

  @JsonProperty("pipelineRequestConfig")
  private PipelineConfig pipelineRequestConfig = null;

  public PipelineRequest pipelineTasks(PipelineTasks pipelineTasks) {
    this.pipelineTasks = pipelineTasks;
    return this;
  }

  /**
   * Get pipelineTasks
   * @return pipelineTasks
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public PipelineTasks getPipelineTasks() {
    return pipelineTasks;
  }

  public void setPipelineTasks(PipelineTasks pipelineTasks) {
    this.pipelineTasks = pipelineTasks;
  }

  public PipelineRequest pipelineRequestConfig(PipelineConfig pipelineRequestConfig) {
    this.pipelineRequestConfig = pipelineRequestConfig;
    return this;
  }

  /**
   * Get pipelineRequestConfig
   * @return pipelineRequestConfig
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public PipelineConfig getPipelineRequestConfig() {
    return pipelineRequestConfig;
  }

  public void setPipelineRequestConfig(PipelineConfig pipelineRequestConfig) {
    this.pipelineRequestConfig = pipelineRequestConfig;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PipelineRequest pipelineRequest = (PipelineRequest) o;
    return Objects.equals(this.pipelineTasks, pipelineRequest.pipelineTasks) &&
        Objects.equals(this.pipelineRequestConfig, pipelineRequest.pipelineRequestConfig);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pipelineTasks, pipelineRequestConfig);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PipelineRequest {\n");
    
    sb.append("    pipelineTasks: ").append(toIndentedString(pipelineTasks)).append("\n");
    sb.append("    pipelineRequestConfig: ").append(toIndentedString(pipelineRequestConfig)).append("\n");
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
