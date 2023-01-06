package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * auto-aligned-from-parallel-docs collection details
 */
@Schema(description = "auto-aligned-from-parallel-docs collection details")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:41:06.560Z[GMT]")


public class CollectionDetailsAutoAlignedFromParallelDocs  implements OneOfParallelDatasetCollectionMethodCollectionDetails {
  /**
   * name of the alignment tool
   */
  public enum AlignmentToolEnum {
    LABSE("LaBSE"),
    
    LASER("LASER");

    private String value;

    AlignmentToolEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AlignmentToolEnum fromValue(String text) {
      for (AlignmentToolEnum b : AlignmentToolEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("alignmentTool")
  private AlignmentToolEnum alignmentTool = null;

  @JsonProperty("alignmentToolVersion")
  private String alignmentToolVersion = null;

  @JsonProperty("alignmentScore")
  private BigDecimal alignmentScore = null;

  public CollectionDetailsAutoAlignedFromParallelDocs alignmentTool(AlignmentToolEnum alignmentTool) {
    this.alignmentTool = alignmentTool;
    return this;
  }

  /**
   * name of the alignment tool
   * @return alignmentTool
   **/
  @Schema(example = "LaBSE", required = true, description = "name of the alignment tool")
      @NotNull

    public AlignmentToolEnum getAlignmentTool() {
    return alignmentTool;
  }

  public void setAlignmentTool(AlignmentToolEnum alignmentTool) {
    this.alignmentTool = alignmentTool;
  }

  public CollectionDetailsAutoAlignedFromParallelDocs alignmentToolVersion(String alignmentToolVersion) {
    this.alignmentToolVersion = alignmentToolVersion;
    return this;
  }

  /**
   * alignment tool version
   * @return alignmentToolVersion
   **/
  @Schema(example = "LaBSE version 3.0", description = "alignment tool version")
  
    public String getAlignmentToolVersion() {
    return alignmentToolVersion;
  }

  public void setAlignmentToolVersion(String alignmentToolVersion) {
    this.alignmentToolVersion = alignmentToolVersion;
  }

  public CollectionDetailsAutoAlignedFromParallelDocs alignmentScore(BigDecimal alignmentScore) {
    this.alignmentScore = alignmentScore;
    return this;
  }

  /**
   * the alignment score between sourceText and targetText
   * @return alignmentScore
   **/
  @Schema(description = "the alignment score between sourceText and targetText")
  
    @Valid
    public BigDecimal getAlignmentScore() {
    return alignmentScore;
  }

  public void setAlignmentScore(BigDecimal alignmentScore) {
    this.alignmentScore = alignmentScore;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CollectionDetailsAutoAlignedFromParallelDocs collectionDetailsAutoAlignedFromParallelDocs = (CollectionDetailsAutoAlignedFromParallelDocs) o;
    return Objects.equals(this.alignmentTool, collectionDetailsAutoAlignedFromParallelDocs.alignmentTool) &&
        Objects.equals(this.alignmentToolVersion, collectionDetailsAutoAlignedFromParallelDocs.alignmentToolVersion) &&
        Objects.equals(this.alignmentScore, collectionDetailsAutoAlignedFromParallelDocs.alignmentScore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alignmentTool, alignmentToolVersion, alignmentScore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionDetailsAutoAlignedFromParallelDocs {\n");
    
    sb.append("    alignmentTool: ").append(toIndentedString(alignmentTool)).append("\n");
    sb.append("    alignmentToolVersion: ").append(toIndentedString(alignmentToolVersion)).append("\n");
    sb.append("    alignmentScore: ").append(toIndentedString(alignmentScore)).append("\n");
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
