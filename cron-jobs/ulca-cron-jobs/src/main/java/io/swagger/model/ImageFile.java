package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * ImageFile
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-20T06:25:33.393Z[GMT]")


public class ImageFile   {
  @JsonProperty("imageContent")
  private byte[] imageContent = null;

  @JsonProperty("imageUri")
  private String imageUri = null;

  public ImageFile imageContent(byte[] imageContent) {
    this.imageContent = imageContent;
    return this;
  }

  /**
   * image content
   * @return imageContent
   **/
  @Schema(description = "image content")
  
    public byte[] getImageContent() {
    return imageContent;
  }

  public void setImageContent(byte[] imageContent) {
    this.imageContent = imageContent;
  }

  public ImageFile imageUri(String imageUri) {
    this.imageUri = imageUri;
    return this;
  }

  /**
   * path on gcp/s3 bucket or https url
   * @return imageUri
   **/
  @Schema(example = "gs://bucket/testimg.jpeg", description = "path on gcp/s3 bucket or https url")
  
    public String getImageUri() {
    return imageUri;
  }

  public void setImageUri(String imageUri) {
    this.imageUri = imageUri;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImageFile imageFile = (ImageFile) o;
    return Objects.equals(this.imageContent, imageFile.imageContent) &&
        Objects.equals(this.imageUri, imageFile.imageUri);
  }

  @Override
  public int hashCode() {
    return Objects.hash(imageContent, imageUri);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ImageFile {\n");
    
    sb.append("    imageContent: ").append(toIndentedString(imageContent)).append("\n");
    sb.append("    imageUri: ").append(toIndentedString(imageUri)).append("\n");
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
