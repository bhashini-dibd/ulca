package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * TranslationAsyncInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-02-23T11:24:57.354Z[GMT]")


public class TranslationAsyncInference  implements OneOfAsyncApiDetailsAsyncApiSchema {
  @JsonProperty("request")
  private TranslationRequest request = null;

  @JsonProperty("response")
  private PollingRequest response = null;

  public TranslationAsyncInference request(TranslationRequest request) {
    this.request = request;
    return this;
  }

  /**
   * Get request
   * @return request
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public TranslationRequest getRequest() {
    return request;
  }

  public void setRequest(TranslationRequest request) {
    this.request = request;
  }

  public TranslationAsyncInference response(PollingRequest response) {
    this.response = response;
    return this;
  }

  /**
   * Get response
   * @return response
   **/
  @Schema(description = "")
  
    @Valid
    public PollingRequest getResponse() {
    return response;
  }

  public void setResponse(PollingRequest response) {
    this.response = response;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TranslationAsyncInference translationAsyncInference = (TranslationAsyncInference) o;
    return Objects.equals(this.request, translationAsyncInference.request) &&
        Objects.equals(this.response, translationAsyncInference.response);
  }

  @Override
  public int hashCode() {
    return Objects.hash(request, response);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TranslationAsyncInference {\n");
    
    sb.append("    request: ").append(toIndentedString(request)).append("\n");
    sb.append("    response: ").append(toIndentedString(response)).append("\n");
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
