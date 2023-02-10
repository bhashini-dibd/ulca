package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * if endpoint needs authorization in headers to fetch output
 */
@Schema(description = "if endpoint needs authorization in headers to fetch output")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-02-10T10:51:37.068454836Z[GMT]")


public class InferenceAPIEndPointInferenceApiKey   {
  @JsonProperty("name")
  private String name = "apiKey";

  @JsonProperty("value")
  private String value = null;

  public InferenceAPIEndPointInferenceApiKey name(String name) {
    this.name = name;
    return this;
  }

  /**
   * expected if the callbackurl requires an API key with a particular name. `apiKey` will be considered as default name if value is provided without a name
   * @return name
   **/
  @Schema(example = "apiKey", description = "expected if the callbackurl requires an API key with a particular name. `apiKey` will be considered as default name if value is provided without a name")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public InferenceAPIEndPointInferenceApiKey value(String value) {
    this.value = value;
    return this;
  }

  /**
   * expected if the callbackurl requires an API key so as to fetch output
   * @return value
   **/
  @Schema(example = "dde19858-b354-4e24-8e92-a7a4b320c066", required = true, description = "expected if the callbackurl requires an API key so as to fetch output")
      @NotNull

    public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InferenceAPIEndPointInferenceApiKey inferenceAPIEndPointInferenceApiKey = (InferenceAPIEndPointInferenceApiKey) o;
    return Objects.equals(this.name, inferenceAPIEndPointInferenceApiKey.name) &&
        Objects.equals(this.value, inferenceAPIEndPointInferenceApiKey.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InferenceAPIEndPointInferenceApiKey {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
