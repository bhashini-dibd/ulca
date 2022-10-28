package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * TranslationRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-08-02T06:46:17.068Z[GMT]")


public class TranslationRequest   {
  @JsonProperty("input")
  private Sentences input = null;

  @JsonProperty("config")
  private TranslationConfig config = null;

  public TranslationRequest input(Sentences input) {
    this.input = input;
    return this;
  }

  /**
   * Get input
   * @return input
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public Sentences getInput() {
    return input;
  }

  public void setInput(Sentences input) {
    this.input = input;
  }

  public TranslationRequest config(TranslationConfig config) {
    this.config = config;
    return this;
  }

  /**
   * Get config
   * @return config
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public TranslationConfig getConfig() {
    return config;
  }

  public void setConfig(TranslationConfig config) {
    this.config = config;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TranslationRequest translationRequest = (TranslationRequest) o;
    return Objects.equals(this.input, translationRequest.input) &&
        Objects.equals(this.config, translationRequest.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(input, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TranslationRequest {\n");
    
    sb.append("    input: ").append(toIndentedString(input)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
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
