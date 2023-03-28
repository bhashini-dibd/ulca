package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.BoundingBox;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the schema defines the column name present in physical file that is being pointed by dataFilename key.
 */
@Schema(description = "the schema defines the column name present in physical file that is being pointed by dataFilename key.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T03:59:56.369839514Z[GMT]")


public class OcrBenchmarkDatasetRowSchema  implements OneOfDatasetDataRowSchemaData {
  @JsonProperty("imageFilename")
  private String imageFilename = null;

  @JsonProperty("groundTruth")
  private String groundTruth = null;

  @JsonProperty("boundingBox")
  private BoundingBox boundingBox = null;

  public OcrBenchmarkDatasetRowSchema imageFilename(String imageFilename) {
    this.imageFilename = imageFilename;
    return this;
  }

  /**
   * filename of the image file
   * @return imageFilename
   **/
  @Schema(required = true, description = "filename of the image file")
      @NotNull

    public String getImageFilename() {
    return imageFilename;
  }

  public void setImageFilename(String imageFilename) {
    this.imageFilename = imageFilename;
  }

  public OcrBenchmarkDatasetRowSchema groundTruth(String groundTruth) {
    this.groundTruth = groundTruth;
    return this;
  }

  /**
   * text data present in boundingbox
   * @return groundTruth
   **/
  @Schema(required = true, description = "text data present in boundingbox")
      @NotNull

    public String getGroundTruth() {
    return groundTruth;
  }

  public void setGroundTruth(String groundTruth) {
    this.groundTruth = groundTruth;
  }

  public OcrBenchmarkDatasetRowSchema boundingBox(BoundingBox boundingBox) {
    this.boundingBox = boundingBox;
    return this;
  }

  /**
   * Get boundingBox
   * @return boundingBox
   **/
  @Schema(description = "")
  
    @Valid
    public BoundingBox getBoundingBox() {
    return boundingBox;
  }

  public void setBoundingBox(BoundingBox boundingBox) {
    this.boundingBox = boundingBox;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OcrBenchmarkDatasetRowSchema ocrBenchmarkDatasetRowSchema = (OcrBenchmarkDatasetRowSchema) o;
    return Objects.equals(this.imageFilename, ocrBenchmarkDatasetRowSchema.imageFilename) &&
        Objects.equals(this.groundTruth, ocrBenchmarkDatasetRowSchema.groundTruth) &&
        Objects.equals(this.boundingBox, ocrBenchmarkDatasetRowSchema.boundingBox);
  }

  @Override
  public int hashCode() {
    return Objects.hash(imageFilename, groundTruth, boundingBox);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OcrBenchmarkDatasetRowSchema {\n");
    
    sb.append("    imageFilename: ").append(toIndentedString(imageFilename)).append("\n");
    sb.append("    groundTruth: ").append(toIndentedString(groundTruth)).append("\n");
    sb.append("    boundingBox: ").append(toIndentedString(boundingBox)).append("\n");
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
