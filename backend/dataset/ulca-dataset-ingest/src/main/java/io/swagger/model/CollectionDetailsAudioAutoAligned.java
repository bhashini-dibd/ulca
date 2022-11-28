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
 * auto-aligned collection details
 */
@Schema(description = "auto-aligned collection details")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-25T09:44:34.039Z[GMT]")


public class CollectionDetailsAudioAutoAligned  implements OneOfCollectionMethodAudioCollectionDetails {
  /**
   * name of the alignment tool
   */
  public enum AlignmentToolEnum {
    AENAES("AENAES");

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

  public CollectionDetailsAudioAutoAligned alignmentTool(AlignmentToolEnum alignmentTool) {
    this.alignmentTool = alignmentTool;
    return this;
  }

  /**
   * name of the alignment tool
   * @return alignmentTool
   **/
  @Schema(required = true, description = "name of the alignment tool")
      @NotNull

    public AlignmentToolEnum getAlignmentTool() {
    return alignmentTool;
  }

  public void setAlignmentTool(AlignmentToolEnum alignmentTool) {
    this.alignmentTool = alignmentTool;
  }

  public CollectionDetailsAudioAutoAligned alignmentToolVersion(String alignmentToolVersion) {
    this.alignmentToolVersion = alignmentToolVersion;
    return this;
  }

  /**
   * alignment tool version
   * @return alignmentToolVersion
   **/
  @Schema(example = "AENAES version 3.0", description = "alignment tool version")
  
    public String getAlignmentToolVersion() {
    return alignmentToolVersion;
  }

  public void setAlignmentToolVersion(String alignmentToolVersion) {
    this.alignmentToolVersion = alignmentToolVersion;
  }

  public CollectionDetailsAudioAutoAligned alignmentScore(BigDecimal alignmentScore) {
    this.alignmentScore = alignmentScore;
    return this;
  }

  /**
   * the alignment score between audio and text
   * @return alignmentScore
   **/
  @Schema(description = "the alignment score between audio and text")
  
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
    CollectionDetailsAudioAutoAligned collectionDetailsAudioAutoAligned = (CollectionDetailsAudioAutoAligned) o;
    return Objects.equals(this.alignmentTool, collectionDetailsAudioAutoAligned.alignmentTool) &&
        Objects.equals(this.alignmentToolVersion, collectionDetailsAudioAutoAligned.alignmentToolVersion) &&
        Objects.equals(this.alignmentScore, collectionDetailsAudioAutoAligned.alignmentScore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alignmentTool, alignmentToolVersion, alignmentScore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionDetailsAudioAutoAligned {\n");
    
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
