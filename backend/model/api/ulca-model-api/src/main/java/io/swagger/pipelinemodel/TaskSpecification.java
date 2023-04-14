package io.swagger.pipelinemodel;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.SupportedTasks;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TaskSpecification
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-16T05:12:20.169133522Z[GMT]")


public class TaskSpecification   {
  @JsonProperty("taskType")
  private SupportedTasks taskType = null;

  @JsonProperty("taskConfig")
  private ConfigList taskConfig = null;

  public TaskSpecification taskType(SupportedTasks taskType) {
    this.taskType = taskType;
    return this;
  }

  /**
   * Get taskType
   * @return taskType
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public SupportedTasks getTaskType() {
    return taskType;
  }

  public void setTaskType(SupportedTasks taskType) {
    this.taskType = taskType;
  }

  public TaskSpecification taskConfig(ConfigList taskConfig) {
    this.taskConfig = taskConfig;
    return this;
  }

  /**
   * Get taskConfig
   * @return taskConfig
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ConfigList getTaskConfig() {
    return taskConfig;
  }

  public void setTaskConfig(ConfigList taskConfig) {
    this.taskConfig = taskConfig;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskSpecification taskSpecification = (TaskSpecification) o;
    return Objects.equals(this.taskType, taskSpecification.taskType) &&
        Objects.equals(this.taskConfig, taskSpecification.taskConfig);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskType, taskConfig);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskSpecification {\n");
    
    sb.append("    taskType: ").append(toIndentedString(taskType)).append("\n");
    sb.append("    taskConfig: ").append(toIndentedString(taskConfig)).append("\n");
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
