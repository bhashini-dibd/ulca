package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.ulca.model.request.OneOfRequest;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Sentences;
import io.swagger.model.TransliterationConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TransliterationRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T03:55:25.562740452Z[GMT]")

@JsonTypeName(value = "TransliterationRequest")
public class TransliterationRequest implements  OneOfRequest {
  @JsonProperty("input")
  private Sentences input = null;

  @JsonProperty("config")
  private TransliterationConfig config = null;

  public TransliterationRequest input(Sentences input) {
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

  public TransliterationRequest config(TransliterationConfig config) {
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
    public TransliterationConfig getConfig() {
    return config;
  }

  public void setConfig(TransliterationConfig config) {
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
    TransliterationRequest transliterationRequest = (TransliterationRequest) o;
    return Objects.equals(this.input, transliterationRequest.input) &&
        Objects.equals(this.config, transliterationRequest.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(input, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransliterationRequest {\n");
    
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
