package io.swagger.pipelinemodel;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ApiEndPoint
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-23T08:33:27.472254079Z[GMT]")


public class ApiEndPoint   {
  @JsonProperty("apiKeyUrl")
  private String apiKeyUrl = null;

  @JsonProperty("feedbackUrl")
  private String feedbackUrl = null;

  public ApiEndPoint apiKeyUrl(String apiKeyUrl) {
    this.apiKeyUrl = apiKeyUrl;
    return this;
  }

  /**
   * URL to generate api keys (using POST Method) , delete api keys (using DELETE method) and toggle data logging( using PATCH Method).
   * @return apiKeyUrl
   **/
  @Schema(required = true, description = "URL to generate api keys (using POST Method) , delete api keys (using DELETE method) and toggle data logging( using PATCH Method).")
      @NotNull

    public String getApiKeyUrl() {
    return apiKeyUrl;
  }

  public void setApiKeyUrl(String apiKeyUrl) {
    this.apiKeyUrl = apiKeyUrl;
  }

  public ApiEndPoint feedbackUrl(String feedbackUrl) {
    this.feedbackUrl = feedbackUrl;
    return this;
  }

  /**
   * URL to give feedback regarding pipeline (using POST Method)
   * @return feedbackUrl
   **/
  @Schema(required = true, description = "URL to give feedback regarding pipeline (using POST Method)")
      @NotNull

    public String getFeedbackUrl() {
    return feedbackUrl;
  }

  public void setFeedbackUrl(String feedbackUrl) {
    this.feedbackUrl = feedbackUrl;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiEndPoint apiEndPoint = (ApiEndPoint) o;
    return Objects.equals(this.apiKeyUrl, apiEndPoint.apiKeyUrl) &&
        Objects.equals(this.feedbackUrl, apiEndPoint.feedbackUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(apiKeyUrl, feedbackUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiEndPoint {\n");
    
    sb.append("    apiKeyUrl: ").append(toIndentedString(apiKeyUrl)).append("\n");
    sb.append("    feedbackUrl: ").append(toIndentedString(feedbackUrl)).append("\n");
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
