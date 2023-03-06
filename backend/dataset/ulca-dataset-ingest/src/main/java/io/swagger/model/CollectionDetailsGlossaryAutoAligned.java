package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * auto-aligned collection details
 */
@Schema(description = "auto-aligned collection details")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:41:06.560Z[GMT]")


public class CollectionDetailsGlossaryAutoAligned  implements OneOfGlossaryDatasetCollectionMethodCollectionDetails {
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

  public CollectionDetailsGlossaryAutoAligned alignmentTool(AlignmentToolEnum alignmentTool) {
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

  public CollectionDetailsGlossaryAutoAligned alignmentToolVersion(String alignmentToolVersion) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CollectionDetailsGlossaryAutoAligned collectionDetailsGlossaryAutoAligned = (CollectionDetailsGlossaryAutoAligned) o;
    return Objects.equals(this.alignmentTool, collectionDetailsGlossaryAutoAligned.alignmentTool) &&
        Objects.equals(this.alignmentToolVersion, collectionDetailsGlossaryAutoAligned.alignmentToolVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alignmentTool, alignmentToolVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionDetailsGlossaryAutoAligned {\n");
    
    sb.append("    alignmentTool: ").append(toIndentedString(alignmentTool)).append("\n");
    sb.append("    alignmentToolVersion: ").append(toIndentedString(alignmentToolVersion)).append("\n");
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
