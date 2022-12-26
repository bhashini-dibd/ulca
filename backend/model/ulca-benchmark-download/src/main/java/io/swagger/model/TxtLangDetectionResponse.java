package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.TxtLangDetectionConfig;
import io.swagger.model.TxtLanguagesList;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the response for translation.  Standard http status codes to be used.
 */
@Schema(description = "the response for translation.  Standard http status codes to be used.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:33:39.764Z[GMT]")


public class TxtLangDetectionResponse   {
  @JsonProperty("output")
  private TxtLanguagesList output = null;

  @JsonProperty("config")
  private TxtLangDetectionConfig config = null;

  public TxtLangDetectionResponse output(TxtLanguagesList output) {
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
    public TxtLanguagesList getOutput() {
    return output;
  }

  public void setOutput(TxtLanguagesList output) {
    this.output = output;
  }

  public TxtLangDetectionResponse config(TxtLangDetectionConfig config) {
    this.config = config;
    return this;
  }

  /**
   * Get config
   * @return config
   **/
  @Schema(description = "")
  
    @Valid
    public TxtLangDetectionConfig getConfig() {
    return config;
  }

  public void setConfig(TxtLangDetectionConfig config) {
    this.config = config;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TxtLangDetectionResponse txtLangDetectionResponse = (TxtLangDetectionResponse) o;
    return Objects.equals(this.output, txtLangDetectionResponse.output) &&
        Objects.equals(this.config, txtLangDetectionResponse.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(output, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TxtLangDetectionResponse {\n");
    
    sb.append("    output: ").append(toIndentedString(output)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
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
