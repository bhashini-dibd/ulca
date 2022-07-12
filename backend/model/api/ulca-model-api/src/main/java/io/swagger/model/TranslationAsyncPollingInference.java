package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.PollingRequest;
import io.swagger.model.TranslationResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TranslationAsyncPollingInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-02-23T11:24:57.354Z[GMT]")


public class TranslationAsyncPollingInference  implements OneOfAsyncApiDetailsAsyncApiPollingSchema {
  @JsonProperty("request")
  private PollingRequest request = null;

  @JsonProperty("response")
  private TranslationResponse response = null;

  public TranslationAsyncPollingInference request(PollingRequest request) {
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
    public PollingRequest getRequest() {
    return request;
  }

  public void setRequest(PollingRequest request) {
    this.request = request;
  }

  public TranslationAsyncPollingInference response(TranslationResponse response) {
    this.response = response;
    return this;
  }

  /**
   * Get response
   * @return response
   **/
  @Schema(description = "")
  
    @Valid
    public TranslationResponse getResponse() {
    return response;
  }

  public void setResponse(TranslationResponse response) {
    this.response = response;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TranslationAsyncPollingInference translationAsyncPollingInference = (TranslationAsyncPollingInference) o;
    return Objects.equals(this.request, translationAsyncPollingInference.request) &&
        Objects.equals(this.response, translationAsyncPollingInference.response);
  }

  @Override
  public int hashCode() {
    return Objects.hash(request, response);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TranslationAsyncPollingInference {\n");
    
    sb.append("    request: ").append(toIndentedString(request)).append("\n");
    sb.append("    response: ").append(toIndentedString(response)).append("\n");
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
