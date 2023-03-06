package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.NerList;
import io.swagger.model.TranslationConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the response for NER.  Standard http status codes to be used.
 */
@Schema(description = "the response for NER.  Standard http status codes to be used.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T08:00:21.046011704Z[GMT]")


public class NerResponse   {
  @JsonProperty("output")
  private NerList output = null;

  @JsonProperty("config")
  private TranslationConfig config = null;

  public NerResponse output(NerList output) {
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
    public NerList getOutput() {
    return output;
  }

  public void setOutput(NerList output) {
    this.output = output;
  }

  public NerResponse config(TranslationConfig config) {
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
    NerResponse nerResponse = (NerResponse) o;
    return Objects.equals(this.output, nerResponse.output) &&
        Objects.equals(this.config, nerResponse.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(output, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NerResponse {\n");
    
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
