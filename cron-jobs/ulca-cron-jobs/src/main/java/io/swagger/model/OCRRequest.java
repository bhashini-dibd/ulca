package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * OCRRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-09-28T22:14:05.003Z[GMT]")


public class OCRRequest   {
  @JsonProperty("image")
  private ImageFiles image = null;

  @JsonProperty("config")
  private OCRConfig config = null;

  public OCRRequest image(ImageFiles image) {
    this.image = image;
    return this;
  }

  /**
   * Get image
   * @return image
   **/
  @Schema(required = true, description = "")
  @NotNull

  @Valid
  public ImageFiles getImage() {
    return image;
  }

  public void setImage(ImageFiles image) {
    this.image = image;
  }

  public OCRRequest config(OCRConfig config) {
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
    OCRRequest ocRRequest = (OCRRequest) o;
    return Objects.equals(this.image, ocRRequest.image) &&
            Objects.equals(this.config, ocRRequest.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(image, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OCRRequest {\n");

    sb.append("    image: ").append(toIndentedString(image)).append("\n");
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
