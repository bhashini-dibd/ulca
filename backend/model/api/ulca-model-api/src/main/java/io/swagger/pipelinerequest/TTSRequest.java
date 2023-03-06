package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Sentences;
import io.swagger.model.TTSConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TTSRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T08:00:21.046011704Z[GMT]")


public class TTSRequest   {
  @JsonProperty("input")
  private Sentences input = null;

  @JsonProperty("config")
  private TTSConfig config = null;

  public TTSRequest input(Sentences input) {
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

  public TTSRequest config(TTSConfig config) {
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
    public TTSConfig getConfig() {
    return config;
  }

  public void setConfig(TTSConfig config) {
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
    TTSRequest ttSRequest = (TTSRequest) o;
    return Objects.equals(this.input, ttSRequest.input) &&
        Objects.equals(this.config, ttSRequest.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(input, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TTSRequest {\n");
    
    sb.append("    input: ").append(toIndentedString(input)).append("\n");
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
