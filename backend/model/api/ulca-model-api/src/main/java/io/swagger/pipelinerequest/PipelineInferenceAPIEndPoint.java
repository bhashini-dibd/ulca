package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.model.AsyncApiDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * hosted location defines the end point of the model inference. specify a taskType along with Inference type
 */
@Schema(description = "hosted location defines the end point of the model inference. specify a taskType along with Inference type")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T08:00:21.046011704Z[GMT]")



public class PipelineInferenceAPIEndPoint   {
  @JsonProperty("callbackUrl")
  private String callbackUrl = null;

  @JsonProperty("inferenceApiKey")
  private TranslationTaskInferenceInferenceApiKey inferenceApiKey = null;

  @JsonProperty("masterApiKey")
  private TranslationTaskInferenceInferenceApiKey masterApiKey = null;

  @JsonProperty("isMultilingualEnabled")
  private Boolean isMultilingualEnabled = false;

  @JsonProperty("schema")
  private AnyOfPipelineInferenceAPIEndPointSchema schema = null;

  @JsonProperty("isSyncApi")
  private Boolean isSyncApi = null;

  @JsonProperty("asyncApiDetails")
  private AsyncApiDetails asyncApiDetails = null;

  public PipelineInferenceAPIEndPoint callbackUrl(String callbackUrl) {
    this.callbackUrl = callbackUrl;
    return this;
  }

  /**
   * Get callbackUrl
   * @return callbackUrl
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getCallbackUrl() {
    return callbackUrl;
  }

  public void setCallbackUrl(String callbackUrl) {
    this.callbackUrl = callbackUrl;
  }

  public PipelineInferenceAPIEndPoint inferenceApiKey(TranslationTaskInferenceInferenceApiKey inferenceApiKey) {
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

  public PipelineInferenceAPIEndPoint masterApiKey(TranslationTaskInferenceInferenceApiKey masterApiKey) {
    this.masterApiKey = masterApiKey;
    return this;
  }

  /**
   * Get masterApiKey
   * @return masterApiKey
   **/
  @Schema(description = "")
  
    @Valid
    public TranslationTaskInferenceInferenceApiKey getMasterApiKey() {
    return masterApiKey;
  }

  public void setMasterApiKey(TranslationTaskInferenceInferenceApiKey masterApiKey) {
    this.masterApiKey = masterApiKey;
  }

  public PipelineInferenceAPIEndPoint isMultilingualEnabled(Boolean isMultilingualEnabled) {
    this.isMultilingualEnabled = isMultilingualEnabled;
    return this;
  }

  /**
   * specify true if the same callbackUrl is capable of handling multiple languages
   * @return isMultilingualEnabled
   **/
  @Schema(description = "specify true if the same callbackUrl is capable of handling multiple languages")
  
    public Boolean isIsMultilingualEnabled() {
    return isMultilingualEnabled;
  }

  public void setIsMultilingualEnabled(Boolean isMultilingualEnabled) {
    this.isMultilingualEnabled = isMultilingualEnabled;
  }

  public PipelineInferenceAPIEndPoint schema(AnyOfPipelineInferenceAPIEndPointSchema schema) {
    this.schema = schema;
    return this;
  }

  /**
   * Get schema
   * @return schema
   **/
  @Schema(description = "")
  
    public AnyOfPipelineInferenceAPIEndPointSchema getSchema() {
    return schema;
  }

  public void setSchema(AnyOfPipelineInferenceAPIEndPointSchema schema) {
    this.schema = schema;
  }

  public PipelineInferenceAPIEndPoint isSyncApi(Boolean isSyncApi) {
    this.isSyncApi = isSyncApi;
    return this;
  }

  /**
   * specify true if the inference is a sync api, false otherwise. when false, specify the polling url and related properties
   * @return isSyncApi
   **/
  @Schema(description = "specify true if the inference is a sync api, false otherwise. when false, specify the polling url and related properties")
  
    public Boolean isIsSyncApi() {
    return isSyncApi;
  }

  public void setIsSyncApi(Boolean isSyncApi) {
    this.isSyncApi = isSyncApi;
  }

  public PipelineInferenceAPIEndPoint asyncApiDetails(AsyncApiDetails asyncApiDetails) {
    this.asyncApiDetails = asyncApiDetails;
    return this;
  }

  /**
   * Get asyncApiDetails
   * @return asyncApiDetails
   **/
  @Schema(description = "")
  
    @Valid
    public AsyncApiDetails getAsyncApiDetails() {
    return asyncApiDetails;
  }

  public void setAsyncApiDetails(AsyncApiDetails asyncApiDetails) {
    this.asyncApiDetails = asyncApiDetails;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PipelineInferenceAPIEndPoint pipelineInferenceAPIEndPoint = (PipelineInferenceAPIEndPoint) o;
    return Objects.equals(this.callbackUrl, pipelineInferenceAPIEndPoint.callbackUrl) &&
        Objects.equals(this.inferenceApiKey, pipelineInferenceAPIEndPoint.inferenceApiKey) &&
        Objects.equals(this.masterApiKey, pipelineInferenceAPIEndPoint.masterApiKey) &&
        Objects.equals(this.isMultilingualEnabled, pipelineInferenceAPIEndPoint.isMultilingualEnabled) &&
        Objects.equals(this.schema, pipelineInferenceAPIEndPoint.schema) &&
        Objects.equals(this.isSyncApi, pipelineInferenceAPIEndPoint.isSyncApi) &&
        Objects.equals(this.asyncApiDetails, pipelineInferenceAPIEndPoint.asyncApiDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(callbackUrl, inferenceApiKey, masterApiKey, isMultilingualEnabled, schema, isSyncApi, asyncApiDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PipelineInferenceAPIEndPoint {\n");
    
    sb.append("    callbackUrl: ").append(toIndentedString(callbackUrl)).append("\n");
    sb.append("    inferenceApiKey: ").append(toIndentedString(inferenceApiKey)).append("\n");
    sb.append("    masterApiKey: ").append(toIndentedString(masterApiKey)).append("\n");
    sb.append("    isMultilingualEnabled: ").append(toIndentedString(isMultilingualEnabled)).append("\n");
    sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
    sb.append("    isSyncApi: ").append(toIndentedString(isSyncApi)).append("\n");
    sb.append("    asyncApiDetails: ").append(toIndentedString(asyncApiDetails)).append("\n");
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
