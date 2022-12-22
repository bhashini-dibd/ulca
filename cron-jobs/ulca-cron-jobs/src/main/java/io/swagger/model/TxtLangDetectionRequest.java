package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * TxtLangDetectionRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-09-04T16:26:09.493Z[GMT]")


public class TxtLangDetectionRequest   {
  @JsonProperty("input")
  private Sentences input = null;

  @JsonProperty("config")
  private TxtLangDetectionConfig config = null;

  public TxtLangDetectionRequest input(Sentences input) {
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

  public TxtLangDetectionRequest config(TxtLangDetectionConfig config) {
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
    public TxtLangDetectionConfig getConfig() {
    return config;
  }

  public void setConfig(TxtLangDetectionConfig config) {
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
    TxtLangDetectionRequest txtLangDetectionRequest = (TxtLangDetectionRequest) o;
    return Objects.equals(this.input, txtLangDetectionRequest.input) &&
        Objects.equals(this.config, txtLangDetectionRequest.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(input, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TxtLangDetectionRequest {\n");
    
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
