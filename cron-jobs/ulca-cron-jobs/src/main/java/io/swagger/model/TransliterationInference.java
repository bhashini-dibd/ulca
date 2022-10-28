package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * TransliterationInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-20T10:51:52.599Z[GMT]")


public class TransliterationInference  implements OneOfInferenceAPIEndPointSchema {
  @JsonProperty("request")
  private TransliterationRequest request = null;

  @JsonProperty("response")
  private TransliterationResponse response = null;

  public TransliterationInference request(TransliterationRequest request) {
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
    public TransliterationRequest getRequest() {
    return request;
  }

  public void setRequest(TransliterationRequest request) {
    this.request = request;
  }

  public TransliterationInference response(TransliterationResponse response) {
    this.response = response;
    return this;
  }

  /**
   * Get response
   * @return response
   **/
  @Schema(description = "")
  
    @Valid
    public TransliterationResponse getResponse() {
    return response;
  }

  public void setResponse(TransliterationResponse response) {
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
    TransliterationInference transliterationInference = (TransliterationInference) o;
    return Objects.equals(this.request, transliterationInference.request) &&
        Objects.equals(this.response, transliterationInference.response);
  }

  @Override
  public int hashCode() {
    return Objects.hash(request, response);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransliterationInference {\n");
    
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
