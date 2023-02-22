package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AsyncApiDetails;
import io.swagger.model.InferenceAPIEndPointInferenceApiKey;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * hosted location defines the end point of the model inference. specify a taskType along with Inference type
 */
@Schema(description = "hosted location defines the end point of the model inference. specify a taskType along with Inference type")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-02-10T10:51:37.068454836Z[GMT]")


public class InferenceAPIEndPoint   {
  @JsonProperty("callbackUrl")
  private String callbackUrl = null;

  @JsonProperty("inferenceApiKey")
  private InferenceAPIEndPointInferenceApiKey inferenceApiKey = null;

  @JsonProperty("schema")
  private OneOfInferenceAPIEndPointSchema schema = null;

  @JsonProperty("isSyncApi")
  private Boolean isSyncApi = true;

  @JsonProperty("asyncApiDetails")
  private AsyncApiDetails asyncApiDetails = null;

  public InferenceAPIEndPoint callbackUrl(String callbackUrl) {
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

  public InferenceAPIEndPoint inferenceApiKey(InferenceAPIEndPointInferenceApiKey inferenceApiKey) {
    this.inferenceApiKey = inferenceApiKey;
    return this;
  }

  /**
   * Get inferenceApiKey
   * @return inferenceApiKey
   **/
  @Schema(description = "")
  
    @Valid
    public InferenceAPIEndPointInferenceApiKey getInferenceApiKey() {
    return inferenceApiKey;
  }

  public void setInferenceApiKey(InferenceAPIEndPointInferenceApiKey inferenceApiKey) {
    this.inferenceApiKey = inferenceApiKey;
  }

  public InferenceAPIEndPoint schema(OneOfInferenceAPIEndPointSchema schema) {
    this.schema = schema;
    return this;
  }

  /**
   * Get schema
   * @return schema
   **/
  @Schema(required = true, description = "")
      @NotNull

    public OneOfInferenceAPIEndPointSchema getSchema() {
    return schema;
  }

  public void setSchema(OneOfInferenceAPIEndPointSchema schema) {
    this.schema = schema;
  }

  public InferenceAPIEndPoint isSyncApi(Boolean isSyncApi) {
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

  public InferenceAPIEndPoint asyncApiDetails(AsyncApiDetails asyncApiDetails) {
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
    InferenceAPIEndPoint inferenceAPIEndPoint = (InferenceAPIEndPoint) o;
    return Objects.equals(this.callbackUrl, inferenceAPIEndPoint.callbackUrl) &&
        Objects.equals(this.inferenceApiKey, inferenceAPIEndPoint.inferenceApiKey) &&
        Objects.equals(this.schema, inferenceAPIEndPoint.schema) &&
        Objects.equals(this.isSyncApi, inferenceAPIEndPoint.isSyncApi) &&
        Objects.equals(this.asyncApiDetails, inferenceAPIEndPoint.asyncApiDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(callbackUrl, inferenceApiKey, schema, isSyncApi, asyncApiDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InferenceAPIEndPoint {\n");
    
    sb.append("    callbackUrl: ").append(toIndentedString(callbackUrl)).append("\n");
    sb.append("    inferenceApiKey: ").append(toIndentedString(inferenceApiKey)).append("\n");
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
