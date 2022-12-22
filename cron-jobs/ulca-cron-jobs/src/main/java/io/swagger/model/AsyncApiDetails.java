package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * async api details
 */
@Schema(description = "async api details")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-02-23T11:24:57.354Z[GMT]")


public class AsyncApiDetails   {
  @JsonProperty("pollingUrl")
  private String pollingUrl = null;

  @JsonProperty("pollInterval")
  private Integer pollInterval = null;

  @JsonProperty("asyncApiSchema")
  private OneOfAsyncApiDetailsAsyncApiSchema asyncApiSchema = null;

  @JsonProperty("asyncApiPollingSchema")
  private OneOfAsyncApiDetailsAsyncApiPollingSchema asyncApiPollingSchema = null;

  public AsyncApiDetails pollingUrl(String pollingUrl) {
    this.pollingUrl = pollingUrl;
    return this;
  }

  /**
   * endpoint specified for polling in async calls
   * @return pollingUrl
   **/
  @Schema(required = true, description = "endpoint specified for polling in async calls")
      @NotNull

    public String getPollingUrl() {
    return pollingUrl;
  }

  public void setPollingUrl(String pollingUrl) {
    this.pollingUrl = pollingUrl;
  }

  public AsyncApiDetails pollInterval(Integer pollInterval) {
    this.pollInterval = pollInterval;
    return this;
  }

  /**
   * polling interval in millisec to check for status
   * @return pollInterval
   **/
  @Schema(required = true, description = "polling interval in millisec to check for status")
      @NotNull

    public Integer getPollInterval() {
    return pollInterval;
  }

  public void setPollInterval(Integer pollInterval) {
    this.pollInterval = pollInterval;
  }

  public AsyncApiDetails asyncApiSchema(OneOfAsyncApiDetailsAsyncApiSchema asyncApiSchema) {
    this.asyncApiSchema = asyncApiSchema;
    return this;
  }

  /**
   * Get asyncApiSchema
   * @return asyncApiSchema
   **/
  @Schema(description = "")
  
    public OneOfAsyncApiDetailsAsyncApiSchema getAsyncApiSchema() {
    return asyncApiSchema;
  }

  public void setAsyncApiSchema(OneOfAsyncApiDetailsAsyncApiSchema asyncApiSchema) {
    this.asyncApiSchema = asyncApiSchema;
  }

  public AsyncApiDetails asyncApiPollingSchema(OneOfAsyncApiDetailsAsyncApiPollingSchema asyncApiPollingSchema) {
    this.asyncApiPollingSchema = asyncApiPollingSchema;
    return this;
  }

  /**
   * Get asyncApiPollingSchema
   * @return asyncApiPollingSchema
   **/
  @Schema(description = "")
  
    public OneOfAsyncApiDetailsAsyncApiPollingSchema getAsyncApiPollingSchema() {
    return asyncApiPollingSchema;
  }

  public void setAsyncApiPollingSchema(OneOfAsyncApiDetailsAsyncApiPollingSchema asyncApiPollingSchema) {
    this.asyncApiPollingSchema = asyncApiPollingSchema;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AsyncApiDetails asyncApiDetails = (AsyncApiDetails) o;
    return Objects.equals(this.pollingUrl, asyncApiDetails.pollingUrl) &&
        Objects.equals(this.pollInterval, asyncApiDetails.pollInterval) &&
        Objects.equals(this.asyncApiSchema, asyncApiDetails.asyncApiSchema) &&
        Objects.equals(this.asyncApiPollingSchema, asyncApiDetails.asyncApiPollingSchema);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pollingUrl, pollInterval, asyncApiSchema, asyncApiPollingSchema);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AsyncApiDetails {\n");
    
    sb.append("    pollingUrl: ").append(toIndentedString(pollingUrl)).append("\n");
    sb.append("    pollInterval: ").append(toIndentedString(pollInterval)).append("\n");
    sb.append("    asyncApiSchema: ").append(toIndentedString(asyncApiSchema)).append("\n");
    sb.append("    asyncApiPollingSchema: ").append(toIndentedString(asyncApiPollingSchema)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
