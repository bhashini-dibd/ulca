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
 * TransliterationTaskInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-06-21T11:26:25.131163956Z[GMT]")


public class TransliterationTaskInference  implements TaskSchema {
  @JsonProperty("callbackUrl")
  private String callbackUrl = null;

  @JsonProperty("inferenceApiKey")
  private TranslationTaskInferenceInferenceApiKey inferenceApiKey = null;

  @JsonProperty("taskType")
  private String taskType = "transliteration";

  @JsonProperty("config")
  @Valid
  private List<TransliterationResponseConfig> config = new ArrayList<TransliterationResponseConfig>();

  public TransliterationTaskInference callbackUrl(String callbackUrl) {
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

  public TransliterationTaskInference inferenceApiKey(TranslationTaskInferenceInferenceApiKey inferenceApiKey) {
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

  public TransliterationTaskInference taskType(String taskType) {
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
    public String getTaskType() {
    return taskType;
  }

  public void setTaskType(String taskType) {
    this.taskType = taskType;
  }

  public TransliterationTaskInference config(List<TransliterationResponseConfig> config) {
    this.config = config;
    return this;
  }

  public TransliterationTaskInference addConfigItem(TransliterationResponseConfig configItem) {
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
    public List<TransliterationResponseConfig> getConfig() {
    return config;
  }

  public void setConfig(List<TransliterationResponseConfig> config) {
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
    TransliterationTaskInference transliterationTaskInference = (TransliterationTaskInference) o;
    return Objects.equals(this.callbackUrl, transliterationTaskInference.callbackUrl) &&
        Objects.equals(this.inferenceApiKey, transliterationTaskInference.inferenceApiKey) &&
        Objects.equals(this.taskType, transliterationTaskInference.taskType) &&
        Objects.equals(this.config, transliterationTaskInference.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(callbackUrl, inferenceApiKey, taskType, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransliterationTaskInference {\n");
    
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
