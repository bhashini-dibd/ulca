package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.model.SupportedTasks;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TranslationTaskInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T11:59:30.120199210Z[GMT]")


public class TranslationTaskInference  implements TaskSchema {
  @JsonProperty("callbackUrl")
  private String callbackUrl = null;

  @JsonProperty("inferenceApiKey")
  private TranslationTaskInferenceInferenceApiKey inferenceApiKey = null;

  @JsonProperty("taskType")
  private String taskType = "translation";

  @JsonProperty("config")
  @Valid
  private List<TranslationResponseConfig> config = new ArrayList<TranslationResponseConfig>();

  public TranslationTaskInference callbackUrl(String callbackUrl) {
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

  public TranslationTaskInference inferenceApiKey(TranslationTaskInferenceInferenceApiKey inferenceApiKey) {
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

	
	 public TranslationTaskInference taskType(String taskType) {
	 this.taskType = taskType; return this; }
	 

  // /**
  //  * Get taskType
  //  * @return taskType
  //  **/
	
	 @Schema(required = true, description = "")
	 
	 @NotNull
	 
	 @Valid public String getTaskType() { return taskType; }
	 
	 public void setTaskType(String taskType) { this.taskType = taskType;
	 }
	 
  public TranslationTaskInference config(List<TranslationResponseConfig> config) {
    this.config = config;
    return this;
  }

  public TranslationTaskInference addConfigItem(TranslationResponseConfig configItem) {
    this.config.add(configItem);
    return this;
  }

  /**
   * list of
   * @return config
   **/
  @Schema(required = true, description = "list of")
      @NotNull
    @Valid
    public List<TranslationResponseConfig> getConfig() {
    return config;
  }

  public void setConfig(List<TranslationResponseConfig> config) {
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
    TranslationTaskInference translationTaskInference = (TranslationTaskInference) o;
    return Objects.equals(this.callbackUrl, translationTaskInference.callbackUrl) &&
        Objects.equals(this.inferenceApiKey, translationTaskInference.inferenceApiKey) &&
       // Objects.equals(this.taskType, translationTaskInference.taskType) &&
        Objects.equals(this.config, translationTaskInference.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(callbackUrl, inferenceApiKey, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TranslationTaskInference {\n");
    
    sb.append("    callbackUrl: ").append(toIndentedString(callbackUrl)).append("\n");
    sb.append("    inferenceApiKey: ").append(toIndentedString(inferenceApiKey)).append("\n");
   // sb.append("    taskType: ").append(toIndentedString(taskType)).append("\n");
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
