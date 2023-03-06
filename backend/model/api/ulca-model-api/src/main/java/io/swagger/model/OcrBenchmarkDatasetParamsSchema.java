package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.BenchmarkDatasetCommonParamsSchema;
import io.swagger.model.Domain;
import io.swagger.model.ImageDPI;
import io.swagger.model.ImageFormat;
import io.swagger.model.ImageTextType;
import io.swagger.model.License;
import io.swagger.model.ModelTask;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OcrBenchmarkDatasetParamsSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T03:59:56.369839514Z[GMT]")


public class OcrBenchmarkDatasetParamsSchema extends BenchmarkDatasetCommonParamsSchema implements OneOfDatasetParamsSchemaParams {
  @JsonProperty("format")
  private ImageFormat format = null;

  @JsonProperty("dpi")
  private ImageDPI dpi = null;

  @JsonProperty("imageTextType")
  private ImageTextType imageTextType = null;

  public OcrBenchmarkDatasetParamsSchema format(ImageFormat format) {
    this.format = format;
    return this;
  }

  /**
   * Get format
   * @return format
   **/
  @Schema(description = "")
  
    @Valid
    public ImageFormat getFormat() {
    return format;
  }

  public void setFormat(ImageFormat format) {
    this.format = format;
  }

  public OcrBenchmarkDatasetParamsSchema dpi(ImageDPI dpi) {
    this.dpi = dpi;
    return this;
  }

  /**
   * Get dpi
   * @return dpi
   **/
  @Schema(description = "")
  
    @Valid
    public ImageDPI getDpi() {
    return dpi;
  }

  public void setDpi(ImageDPI dpi) {
    this.dpi = dpi;
  }

  public OcrBenchmarkDatasetParamsSchema imageTextType(ImageTextType imageTextType) {
    this.imageTextType = imageTextType;
    return this;
  }

  /**
   * Get imageTextType
   * @return imageTextType
   **/
  @Schema(description = "")
  
    @Valid
    public ImageTextType getImageTextType() {
    return imageTextType;
  }

  public void setImageTextType(ImageTextType imageTextType) {
    this.imageTextType = imageTextType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OcrBenchmarkDatasetParamsSchema ocrBenchmarkDatasetParamsSchema = (OcrBenchmarkDatasetParamsSchema) o;
    return Objects.equals(this.format, ocrBenchmarkDatasetParamsSchema.format) &&
        Objects.equals(this.dpi, ocrBenchmarkDatasetParamsSchema.dpi) &&
        Objects.equals(this.imageTextType, ocrBenchmarkDatasetParamsSchema.imageTextType) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(format, dpi, imageTextType, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OcrBenchmarkDatasetParamsSchema {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    format: ").append(toIndentedString(format)).append("\n");
    sb.append("    dpi: ").append(toIndentedString(dpi)).append("\n");
    sb.append("    imageTextType: ").append(toIndentedString(imageTextType)).append("\n");
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
