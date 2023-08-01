package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.DatasetCommonParamsSchema;
import io.swagger.model.DatasetType;
import io.swagger.model.Domain;
import io.swagger.model.ImageDPI;
import io.swagger.model.ImageFormat;
import io.swagger.model.ImageTextType;
import io.swagger.model.LanguagePair;
import io.swagger.model.License;
import io.swagger.model.OcrCollectionMethod;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OcrDatasetParamsSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-02-11T16:18:51.317347392Z[GMT]")


public class OcrDatasetParamsSchema extends DatasetCommonParamsSchema implements OneOfDatasetParamsSchemaParams {
  @JsonProperty("format")
  private ImageFormat format = null;

  @JsonProperty("dpi")
  private ImageDPI dpi = null;

  @JsonProperty("imageTextType")
  private ImageTextType imageTextType = null;

  @JsonProperty("collectionMethod")
  private OcrCollectionMethod collectionMethod = null;

  public OcrDatasetParamsSchema format(ImageFormat format) {
    this.format = format;
    return this;
  }

  /**
   * Get format
   * @return format
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ImageFormat getFormat() {
    return format;
  }

  public void setFormat(ImageFormat format) {
    this.format = format;
  }

  public OcrDatasetParamsSchema dpi(ImageDPI dpi) {
    this.dpi = dpi;
    return this;
  }

  /**
   * Get dpi
   * @return dpi
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ImageDPI getDpi() {
    return dpi;
  }

  public void setDpi(ImageDPI dpi) {
    this.dpi = dpi;
  }

  public OcrDatasetParamsSchema imageTextType(ImageTextType imageTextType) {
    this.imageTextType = imageTextType;
    return this;
  }

  /**
   * Get imageTextType
   * @return imageTextType
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ImageTextType getImageTextType() {
    return imageTextType;
  }

  public void setImageTextType(ImageTextType imageTextType) {
    this.imageTextType = imageTextType;
  }

  public OcrDatasetParamsSchema collectionMethod(OcrCollectionMethod collectionMethod) {
    this.collectionMethod = collectionMethod;
    return this;
  }

  /**
   * Get collectionMethod
   * @return collectionMethod
   **/
  @Schema(description = "")
  
    @Valid
    public OcrCollectionMethod getCollectionMethod() {
    return collectionMethod;
  }

  public void setCollectionMethod(OcrCollectionMethod collectionMethod) {
    this.collectionMethod = collectionMethod;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OcrDatasetParamsSchema ocrDatasetParamsSchema = (OcrDatasetParamsSchema) o;
    return Objects.equals(this.format, ocrDatasetParamsSchema.format) &&
        Objects.equals(this.dpi, ocrDatasetParamsSchema.dpi) &&
        Objects.equals(this.imageTextType, ocrDatasetParamsSchema.imageTextType) &&
        Objects.equals(this.collectionMethod, ocrDatasetParamsSchema.collectionMethod) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(format, dpi, imageTextType, collectionMethod, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OcrDatasetParamsSchema {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    format: ").append(toIndentedString(format)).append("\n");
    sb.append("    dpi: ").append(toIndentedString(dpi)).append("\n");
    sb.append("    imageTextType: ").append(toIndentedString(imageTextType)).append("\n");
    sb.append("    collectionMethod: ").append(toIndentedString(collectionMethod)).append("\n");
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
