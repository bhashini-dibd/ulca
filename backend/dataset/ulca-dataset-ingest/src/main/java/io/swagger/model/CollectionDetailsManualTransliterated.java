package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Contributor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * manual transliteration collection details
 */
@Schema(description = "manual transliteration collection details")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:41:06.560Z[GMT]")


public class CollectionDetailsManualTransliterated  implements OneOfTransliterationDatasetCollectionMethodCollectionDetails {
  @JsonProperty("editingTool")
  private String editingTool = null;

  @JsonProperty("editingToolVersion")
  private String editingToolVersion = null;

  @JsonProperty("contributor")
  private Contributor contributor = null;

  @JsonProperty("timeSpentInSeconds")
  private BigDecimal timeSpentInSeconds = null;

  @JsonProperty("numOfKeysPressed")
  private BigDecimal numOfKeysPressed = null;

  public CollectionDetailsManualTransliterated editingTool(String editingTool) {
    this.editingTool = editingTool;
    return this;
  }

  /**
   * name of the post editing tool
   * @return editingTool
   **/
  @Schema(example = "google transliteration", description = "name of the post editing tool")
  
    public String getEditingTool() {
    return editingTool;
  }

  public void setEditingTool(String editingTool) {
    this.editingTool = editingTool;
  }

  public CollectionDetailsManualTransliterated editingToolVersion(String editingToolVersion) {
    this.editingToolVersion = editingToolVersion;
    return this;
  }

  /**
   * post editing tool version
   * @return editingToolVersion
   **/
  @Schema(description = "post editing tool version")
  
    public String getEditingToolVersion() {
    return editingToolVersion;
  }

  public void setEditingToolVersion(String editingToolVersion) {
    this.editingToolVersion = editingToolVersion;
  }

  public CollectionDetailsManualTransliterated contributor(Contributor contributor) {
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

  public CollectionDetailsManualTransliterated timeSpentInSeconds(BigDecimal timeSpentInSeconds) {
    this.timeSpentInSeconds = timeSpentInSeconds;
    return this;
  }

  /**
   * time spent by user to transliterate the sentence.
   * @return timeSpentInSeconds
   **/
  @Schema(description = "time spent by user to transliterate the sentence.")
  
    @Valid
    public BigDecimal getTimeSpentInSeconds() {
    return timeSpentInSeconds;
  }

  public void setTimeSpentInSeconds(BigDecimal timeSpentInSeconds) {
    this.timeSpentInSeconds = timeSpentInSeconds;
  }

  public CollectionDetailsManualTransliterated numOfKeysPressed(BigDecimal numOfKeysPressed) {
    this.numOfKeysPressed = numOfKeysPressed;
    return this;
  }

  /**
   * number of keyboard keys pressed
   * @return numOfKeysPressed
   **/
  @Schema(description = "number of keyboard keys pressed")
  
    @Valid
    public BigDecimal getNumOfKeysPressed() {
    return numOfKeysPressed;
  }

  public void setNumOfKeysPressed(BigDecimal numOfKeysPressed) {
    this.numOfKeysPressed = numOfKeysPressed;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CollectionDetailsManualTransliterated collectionDetailsManualTransliterated = (CollectionDetailsManualTransliterated) o;
    return Objects.equals(this.editingTool, collectionDetailsManualTransliterated.editingTool) &&
        Objects.equals(this.editingToolVersion, collectionDetailsManualTransliterated.editingToolVersion) &&
        Objects.equals(this.contributor, collectionDetailsManualTransliterated.contributor) &&
        Objects.equals(this.timeSpentInSeconds, collectionDetailsManualTransliterated.timeSpentInSeconds) &&
        Objects.equals(this.numOfKeysPressed, collectionDetailsManualTransliterated.numOfKeysPressed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(editingTool, editingToolVersion, contributor, timeSpentInSeconds, numOfKeysPressed);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionDetailsManualTransliterated {\n");
    
    sb.append("    editingTool: ").append(toIndentedString(editingTool)).append("\n");
    sb.append("    editingToolVersion: ").append(toIndentedString(editingToolVersion)).append("\n");
    sb.append("    contributor: ").append(toIndentedString(contributor)).append("\n");
    sb.append("    timeSpentInSeconds: ").append(toIndentedString(timeSpentInSeconds)).append("\n");
    sb.append("    numOfKeysPressed: ").append(toIndentedString(numOfKeysPressed)).append("\n");
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
