package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.SentencesList;
import io.swagger.model.SupportedTasks;
import io.swagger.model.TransliterationConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the response for translation.  Standard http status codes to be used.
 */
@Schema(description = "the response for translation.  Standard http status codes to be used.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-21T09:50:48.420331102Z[GMT]")


public class TransliterationResponse   {
  @JsonProperty("output")
  private SentencesList output = null;

  @JsonProperty("config")
  private TransliterationConfig config = null;

  @JsonProperty("taskType")
  private SupportedTasks taskType = null;

  public TransliterationResponse output(SentencesList output) {
    this.output = output;
    return this;
  }

  /**
   * Get output
   * @return output
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public SentencesList getOutput() {
    return output;
  }

  public void setOutput(SentencesList output) {
    this.output = output;
  }

  public TransliterationResponse config(TransliterationConfig config) {
    this.config = config;
    return this;
  }

  /**
   * Get config
   * @return config
   **/
  @Schema(description = "")
  
    @Valid
    public TransliterationConfig getConfig() {
    return config;
  }

  public void setConfig(TransliterationConfig config) {
    this.config = config;
  }

  public TransliterationResponse taskType(SupportedTasks taskType) {
    this.taskType = taskType;
    return this;
  }

  /**
   * Get taskType
   * @return taskType
   **/
  @Schema(description = "")
  
    @Valid
    public SupportedTasks getTaskType() {
    return taskType;
  }

  public void setTaskType(SupportedTasks taskType) {
    this.taskType = taskType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransliterationResponse transliterationResponse = (TransliterationResponse) o;
    return Objects.equals(this.output, transliterationResponse.output) &&
        Objects.equals(this.config, transliterationResponse.config) &&
        Objects.equals(this.taskType, transliterationResponse.taskType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(output, config, taskType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransliterationResponse {\n");
    
    sb.append("    output: ").append(toIndentedString(output)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
    sb.append("    taskType: ").append(toIndentedString(taskType)).append("\n");
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
