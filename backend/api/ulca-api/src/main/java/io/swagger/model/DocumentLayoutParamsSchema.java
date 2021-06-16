package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.DatasetType;
import io.swagger.model.ImageDPI;
import io.swagger.model.ImageFormat;
import io.swagger.model.License;
import io.swagger.model.OcrCollectionMethod;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DocumentLayoutParamsSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-09T08:20:20.072Z[GMT]")


public class DocumentLayoutParamsSchema  implements OneOfDatasetParamsSchemaParams {
  @JsonProperty("datasetType")
  private DatasetType datasetType = null;

  @JsonProperty("collectionSource")
  private Source collectionSource = null;

  @JsonProperty("license")
  private License license = null;

  @JsonProperty("submitter")
  private Submitter submitter = null;

  @JsonProperty("format")
  private ImageFormat format = null;

  @JsonProperty("dpi")
  private ImageDPI dpi = null;

  @JsonProperty("collectionMethod")
  private OcrCollectionMethod collectionMethod = null;

  public DocumentLayoutParamsSchema datasetType(DatasetType datasetType) {
    this.datasetType = datasetType;
    return this;
  }

  /**
   * Get datasetType
   * @return datasetType
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public DatasetType getDatasetType() {
    return datasetType;
  }

  public void setDatasetType(DatasetType datasetType) {
    this.datasetType = datasetType;
  }

  public DocumentLayoutParamsSchema collectionSource(Source collectionSource) {
    this.collectionSource = collectionSource;
    return this;
  }

  /**
   * Get collectionSource
   * @return collectionSource
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public Source getCollectionSource() {
    return collectionSource;
  }

  public void setCollectionSource(Source collectionSource) {
    this.collectionSource = collectionSource;
  }

  public DocumentLayoutParamsSchema license(License license) {
    this.license = license;
    return this;
  }

  /**
   * Get license
   * @return license
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public License getLicense() {
    return license;
  }

  public void setLicense(License license) {
    this.license = license;
  }

  public DocumentLayoutParamsSchema submitter(Submitter submitter) {
    this.submitter = submitter;
    return this;
  }

  /**
   * Get submitter
   * @return submitter
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public Submitter getSubmitter() {
    return submitter;
  }

  public void setSubmitter(Submitter submitter) {
    this.submitter = submitter;
  }

  public DocumentLayoutParamsSchema format(ImageFormat format) {
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

  public DocumentLayoutParamsSchema dpi(ImageDPI dpi) {
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

  public DocumentLayoutParamsSchema collectionMethod(OcrCollectionMethod collectionMethod) {
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
    DocumentLayoutParamsSchema documentLayoutParamsSchema = (DocumentLayoutParamsSchema) o;
    return Objects.equals(this.datasetType, documentLayoutParamsSchema.datasetType) &&
        Objects.equals(this.collectionSource, documentLayoutParamsSchema.collectionSource) &&
        Objects.equals(this.license, documentLayoutParamsSchema.license) &&
        Objects.equals(this.submitter, documentLayoutParamsSchema.submitter) &&
        Objects.equals(this.format, documentLayoutParamsSchema.format) &&
        Objects.equals(this.dpi, documentLayoutParamsSchema.dpi) &&
        Objects.equals(this.collectionMethod, documentLayoutParamsSchema.collectionMethod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datasetType, collectionSource, license, submitter, format, dpi, collectionMethod);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentLayoutParamsSchema {\n");
    
    sb.append("    datasetType: ").append(toIndentedString(datasetType)).append("\n");
    sb.append("    collectionSource: ").append(toIndentedString(collectionSource)).append("\n");
    sb.append("    license: ").append(toIndentedString(license)).append("\n");
    sb.append("    submitter: ").append(toIndentedString(submitter)).append("\n");
    sb.append("    format: ").append(toIndentedString(format)).append("\n");
    sb.append("    dpi: ").append(toIndentedString(dpi)).append("\n");
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
