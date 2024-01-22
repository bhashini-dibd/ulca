package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Sentences;
import io.swagger.model.TranslationConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the response for translation.  Standard http status codes to be used.
 */
@Schema(description = "the response for translation.  Standard http status codes to be used.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T08:00:21.046011704Z[GMT]")


public class ASRResponse   {
  @JsonProperty("output")
  private Sentences output = null;

  @JsonProperty("config")
  private TranslationConfig config = null;

  public ASRResponse output(Sentences output) {
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

  public ASRResponse config(TranslationConfig config) {
    this.config = config;
    return this;
  }

  /**
   * Get config
   * @return config
   **/
  @Schema(description = "")
  
    @Valid
    public TranslationConfig getConfig() {
    return config;
  }

  public void setConfig(TranslationConfig config) {
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
    ASRResponse asRResponse = (ASRResponse) o;
    return Objects.equals(this.output, asRResponse.output) &&
        Objects.equals(this.config, asRResponse.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(output, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ASRResponse {\n");
    
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
