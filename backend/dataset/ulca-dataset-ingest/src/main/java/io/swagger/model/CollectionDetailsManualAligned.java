package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Contributor;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * manual aligned collection details. The pair was mapped manually from existing parallel documents.
 */
@Schema(description = "manual aligned collection details. The pair was mapped manually from existing parallel documents.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-25T09:44:34.039Z[GMT]")


public class CollectionDetailsManualAligned  implements OneOfParallelDatasetCollectionMethodCollectionDetails {
  @JsonProperty("editingTool")
  private String editingTool = null;

  @JsonProperty("editingToolVersion")
  private String editingToolVersion = null;

  @JsonProperty("contributor")
  private Contributor contributor = null;

  public CollectionDetailsManualAligned editingTool(String editingTool) {
    this.editingTool = editingTool;
    return this;
  }

  /**
   * name of the editing tool
   * @return editingTool
   **/
  @Schema(example = "microsoft excel", description = "name of the editing tool")
  
    public String getEditingTool() {
    return editingTool;
  }

  public void setEditingTool(String editingTool) {
    this.editingTool = editingTool;
  }

  public CollectionDetailsManualAligned editingToolVersion(String editingToolVersion) {
    this.editingToolVersion = editingToolVersion;
    return this;
  }

  /**
   * editing tool version
   * @return editingToolVersion
   **/
  @Schema(description = "editing tool version")
  
    public String getEditingToolVersion() {
    return editingToolVersion;
  }

  public void setEditingToolVersion(String editingToolVersion) {
    this.editingToolVersion = editingToolVersion;
  }

  public CollectionDetailsManualAligned contributor(Contributor contributor) {
    this.contributor = contributor;
    return this;
  }

  /**
   * Get contributor
   * @return contributor
   **/
  @Schema(description = "")
  
    @Valid
    public Contributor getContributor() {
    return contributor;
  }

  public void setContributor(Contributor contributor) {
    this.contributor = contributor;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CollectionDetailsManualAligned collectionDetailsManualAligned = (CollectionDetailsManualAligned) o;
    return Objects.equals(this.editingTool, collectionDetailsManualAligned.editingTool) &&
        Objects.equals(this.editingToolVersion, collectionDetailsManualAligned.editingToolVersion) &&
        Objects.equals(this.contributor, collectionDetailsManualAligned.contributor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(editingTool, editingToolVersion, contributor);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionDetailsManualAligned {\n");
    
    sb.append("    editingTool: ").append(toIndentedString(editingTool)).append("\n");
    sb.append("    editingToolVersion: ").append(toIndentedString(editingToolVersion)).append("\n");
    sb.append("    contributor: ").append(toIndentedString(contributor)).append("\n");
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
