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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-23T09:19:31.692607590Z[GMT]")


public class ApiEndPoint   {
  @JsonProperty("apiKeyUrl")
  private String apiKeyUrl = null;

  public ApiEndPoint apiKeyUrl(String apiKeyUrl) {
    this.apiKeyUrl = apiKeyUrl;
    return this;
  }

  /**
   * URL to generate api keys (using POST Method) and delete api keys (using DELETE method).
   * @return apiKeyUrl
   **/
  @Schema(required = true, description = "URL to generate api keys (using POST Method) and delete api keys (using DELETE method).")
      @NotNull

    public String getApiKeyUrl() {
    return apiKeyUrl;
  }

  public void setApiKeyUrl(String apiKeyUrl) {
    this.apiKeyUrl = apiKeyUrl;
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
    return Objects.equals(this.apiKeyUrl, apiEndPoint.apiKeyUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(apiKeyUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiEndPoint {\n");
    
    sb.append("    apiKeyUrl: ").append(toIndentedString(apiKeyUrl)).append("\n");
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
