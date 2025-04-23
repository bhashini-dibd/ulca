package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.ImgLangDetectionList;
import io.swagger.model.SupportedTasks;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the response for translation.  Standard http status codes to be used.
 */
@Schema(description = "the response for translation.  Standard http status codes to be used.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-04-16T09:37:42.477810161Z[GMT]")


public class ImgLangDetectionResponse   {
  @JsonProperty("output")

  private ImgLangDetectionList output = null;

  @JsonProperty("taskType")
  private SupportedTasks taskType = null;


  public ImgLangDetectionResponse output(ImgLangDetectionList output) { 

    this.output = output;
    return this;
  }

  /**
   * Get output
   * @return output
   **/
  
  @Schema(required = true, description = "")
  
@Valid
  @NotNull
  public ImgLangDetectionList getOutput() {  
    return output;
  }



  public void setOutput(ImgLangDetectionList output) { 

    this.output = output;
  }

  public ImgLangDetectionResponse taskType(SupportedTasks taskType) { 

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
    ImgLangDetectionResponse imgLangDetectionResponse = (ImgLangDetectionResponse) o;
    return Objects.equals(this.output, imgLangDetectionResponse.output) &&
        Objects.equals(this.taskType, imgLangDetectionResponse.taskType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(output, taskType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ImgLangDetectionResponse {\n");
    
    sb.append("    output: ").append(toIndentedString(output)).append("\n");
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
