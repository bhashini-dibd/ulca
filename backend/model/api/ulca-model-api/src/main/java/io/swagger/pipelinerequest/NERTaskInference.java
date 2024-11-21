package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.SupportedTasks;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NERTaskInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-11-19T09:12:35.226263225Z[GMT]")


public class NERTaskInference  implements TaskSchema {
  @JsonProperty("callbackUrl")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String callbackUrl = null;

  @JsonProperty("inferenceApiKey")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private TranslationTaskInferenceInferenceApiKey inferenceApiKey = null;

  @JsonProperty("taskType")

  private String taskType = "ner";

  @JsonProperty("config")
  @Valid
  private List<NERResponseConfig> config = new ArrayList<NERResponseConfig>();

  public NERTaskInference callbackUrl(String callbackUrl) { 

    this.callbackUrl = callbackUrl;
    return this;
  }

  /**
   * Get callbackUrl
   * @return callbackUrl
   **/
  
  @Schema(description = "")
  
  public String getCallbackUrl() {  
    return callbackUrl;
  }



  public void setCallbackUrl(String callbackUrl) { 
    this.callbackUrl = callbackUrl;
  }

  public NERTaskInference inferenceApiKey(TranslationTaskInferenceInferenceApiKey inferenceApiKey) { 

    this.inferenceApiKey = inferenceApiKey;
    return this;
  }

  /**
   * Get inferenceApiKey
   * @return inferenceApiKey
   **/
  
  @Schema(description = "")
  
@Valid
  public TranslationTaskInferenceInferenceApiKey getInferenceApiKey() {  
    return inferenceApiKey;
  }



  public void setInferenceApiKey(TranslationTaskInferenceInferenceApiKey inferenceApiKey) { 
    this.inferenceApiKey = inferenceApiKey;
  }

  public NERTaskInference taskType(String taskType) { 

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

  public NERTaskInference config(List<NERResponseConfig> config) { 

    this.config = config;
    return this;
  }

  public NERTaskInference addConfigItem(NERResponseConfig configItem) {
    this.config.add(configItem);
    return this;
  }

  /**
   * list of
   * @return config
   **/
  
  @Schema(required = true, description = "list of")
  
@Valid
  @NotNull
  public List<NERResponseConfig> getConfig() {  
    return config;
  }



  public void setConfig(List<NERResponseConfig> config) { 

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
    NERTaskInference neRTaskInference = (NERTaskInference) o;
    return Objects.equals(this.callbackUrl, neRTaskInference.callbackUrl) &&
        Objects.equals(this.inferenceApiKey, neRTaskInference.inferenceApiKey) &&
        Objects.equals(this.taskType, neRTaskInference.taskType) &&
        Objects.equals(this.config, neRTaskInference.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(callbackUrl, inferenceApiKey, taskType, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NERTaskInference {\n");
    
    sb.append("    callbackUrl: ").append(toIndentedString(callbackUrl)).append("\n");
    sb.append("    inferenceApiKey: ").append(toIndentedString(inferenceApiKey)).append("\n");
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
