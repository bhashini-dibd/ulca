package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * the response for translation.  Standard http status codes to be used.
 */
@Schema(description = "the response for translation.  Standard http status codes to be used.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-09-28T22:14:05.003Z[GMT]")


public class OCRResponse   {
  @JsonProperty("output")
  private Sentences output = null;

  @JsonProperty("config")
  private OCRConfig config = null;

  public OCRResponse output(Sentences output) {
    this.output = output;
    return this;
  }

  /**
   * Get output
   * @return output
   **/
  @Schema(required = true, description = "")
  @NotNull

  @Valid
  public Sentences getOutput() {
    return output;
  }

  public void setOutput(Sentences output) {
    this.output = output;
  }

  public OCRResponse config(OCRConfig config) {
    this.config = config;
    return this;
  }

  /**
   * Get config
   * @return config
   **/
  @Schema(description = "")

  @Valid
  public OCRConfig getConfig() {
    return config;
  }

  public void setConfig(OCRConfig config) {
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
    OCRResponse ocRResponse = (OCRResponse) o;
    return Objects.equals(this.output, ocRResponse.output) &&
            Objects.equals(this.config, ocRResponse.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(output, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OCRResponse {\n");

    sb.append("    output: ").append(toIndentedString(output)).append("\n");
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
