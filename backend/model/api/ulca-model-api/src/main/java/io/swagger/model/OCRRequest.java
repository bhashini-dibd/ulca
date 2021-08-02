package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.OCRConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OCRRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-08-02T06:46:17.068Z[GMT]")


public class OCRRequest   {
  @JsonProperty("imageUri")
  @Valid
  private List<String> imageUri = new ArrayList<String>();

  @JsonProperty("config")
  private OCRConfig config = null;

  public OCRRequest imageUri(List<String> imageUri) {
    this.imageUri = imageUri;
    return this;
  }

  public OCRRequest addImageUriItem(String imageUriItem) {
    this.imageUri.add(imageUriItem);
    return this;
  }

  /**
   * list of paths on gcp/s3 bucket or https url
   * @return imageUri
   **/
  @Schema(example = "gs://bucket/testimg.jpeg", required = true, description = "list of paths on gcp/s3 bucket or https url")
      @NotNull

    public List<String> getImageUri() {
    return imageUri;
  }

  public void setImageUri(List<String> imageUri) {
    this.imageUri = imageUri;
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
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OCRRequest ocRRequest = (OCRRequest) o;
    return Objects.equals(this.imageUri, ocRRequest.imageUri) &&
        Objects.equals(this.config, ocRRequest.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(imageUri, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OCRRequest {\n");
    
    sb.append("    imageUri: ").append(toIndentedString(imageUri)).append("\n");
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
