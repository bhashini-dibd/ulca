package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.SupportedTasks;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NERTask
 */
@Validated

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-11-19T09:12:35.226263225Z[GMT]")


public class NERTask  implements PipelineTask {
  @JsonProperty("taskType")

  private String taskType = "ner";

  @JsonProperty("config")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private TranslationRequestConfig config = null;


  public NERTask taskType(String taskType) { 

    this.taskType = taskType;
    return this;
  }

  /**
   * Get taskType
   * @return taskType
   **/
  
  @Schema(required = true, description = "")
  
@Valid
  @NotNull
  public String getTaskType() {  
    return taskType;
  }



  public void setTaskType(String taskType) { 

    this.taskType = taskType;
  }

  public NERTask config(TranslationRequestConfig config) { 

    this.config = config;
    return this;
  }

  /**
   * Get config
   * @return config
   **/
  
  @Schema(description = "")
  
@Valid
  public TranslationRequestConfig getConfig() {  
    return config;
  }



  public void setConfig(TranslationRequestConfig config) { 
    this.config = config;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NERTask neRTask = (NERTask) o;
    return Objects.equals(this.taskType, neRTask.taskType) &&
        Objects.equals(this.config, neRTask.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskType, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NERTask {\n");
    
    sb.append("    taskType: ").append(toIndentedString(taskType)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
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
