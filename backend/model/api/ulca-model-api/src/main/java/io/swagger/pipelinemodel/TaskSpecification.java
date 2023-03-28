package io.swagger.pipelinemodel;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.ModelTask;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TaskSpecification
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T06:06:14.793576134Z[GMT]")


public class TaskSpecification   {
  @JsonProperty("task")
  private ModelTask task = null;

  @JsonProperty("languages")
  private LanguagesList languages = null;

  public TaskSpecification task(ModelTask task) {
    this.task = task;
    return this;
  }

  /**
   * Get task
   * @return task
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ModelTask getTask() {
    return task;
  }

  public void setTask(ModelTask task) {
    this.task = task;
  }

  public TaskSpecification languages(LanguagesList languages) {
    this.languages = languages;
    return this;
  }

  /**
   * Get languages
   * @return languages
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public LanguagesList getLanguages() {
    return languages;
  }

  public void setLanguages(LanguagesList languages) {
    this.languages = languages;
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
    return Objects.equals(this.task, taskSpecification.task) &&
        Objects.equals(this.languages, taskSpecification.languages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(task, languages);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskSpecification {\n");
    
    sb.append("    task: ").append(toIndentedString(task)).append("\n");
    sb.append("    languages: ").append(toIndentedString(languages)).append("\n");
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
