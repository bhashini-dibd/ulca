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
 * machine translated and post edited collection details
 */
@Schema(description = "machine translated and post edited collection details")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-02-11T16:18:51.317347392Z[GMT]")


public class CollectionDetailsMachineTranslatedPostEdited  implements OneOfParallelDatasetCollectionMethodCollectionDetails {
  @JsonProperty("translationModel")
  private String translationModel = null;

  @JsonProperty("translationModelVersion")
  private String translationModelVersion = null;

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

  public CollectionDetailsMachineTranslatedPostEdited translationModel(String translationModel) {
    this.translationModel = translationModel;
    return this;
  }

  /**
   * name of the translation model/engine used
   * @return translationModel
   **/
  @Schema(example = "google translation", description = "name of the translation model/engine used")
  
    public String getTranslationModel() {
    return translationModel;
  }

  public void setTranslationModel(String translationModel) {
    this.translationModel = translationModel;
  }

  public CollectionDetailsMachineTranslatedPostEdited translationModelVersion(String translationModelVersion) {
    this.translationModelVersion = translationModelVersion;
    return this;
  }

  /**
   * translation model/engine version
   * @return translationModelVersion
   **/
  @Schema(description = "translation model/engine version")
  
    public String getTranslationModelVersion() {
    return translationModelVersion;
  }

  public void setTranslationModelVersion(String translationModelVersion) {
    this.translationModelVersion = translationModelVersion;
  }

  public CollectionDetailsMachineTranslatedPostEdited editingTool(String editingTool) {
    this.editingTool = editingTool;
    return this;
  }

  /**
   * name of the post editing tool
   * @return editingTool
   **/
  @Schema(example = "google translation", description = "name of the post editing tool")
  
    public String getEditingTool() {
    return editingTool;
  }

  public void setEditingTool(String editingTool) {
    this.editingTool = editingTool;
  }

  public CollectionDetailsMachineTranslatedPostEdited editingToolVersion(String editingToolVersion) {
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

  public CollectionDetailsMachineTranslatedPostEdited contributor(Contributor contributor) {
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

  public CollectionDetailsMachineTranslatedPostEdited timeSpentInSeconds(BigDecimal timeSpentInSeconds) {
    this.timeSpentInSeconds = timeSpentInSeconds;
    return this;
  }

  /**
   * time spent by user to translated the sentence.
   * @return timeSpentInSeconds
   **/
  @Schema(description = "time spent by user to translated the sentence.")
  
    @Valid
    public BigDecimal getTimeSpentInSeconds() {
    return timeSpentInSeconds;
  }

  public void setTimeSpentInSeconds(BigDecimal timeSpentInSeconds) {
    this.timeSpentInSeconds = timeSpentInSeconds;
  }

  public CollectionDetailsMachineTranslatedPostEdited numOfKeysPressed(BigDecimal numOfKeysPressed) {
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
    CollectionDetailsMachineTranslatedPostEdited collectionDetailsMachineTranslatedPostEdited = (CollectionDetailsMachineTranslatedPostEdited) o;
    return Objects.equals(this.translationModel, collectionDetailsMachineTranslatedPostEdited.translationModel) &&
        Objects.equals(this.translationModelVersion, collectionDetailsMachineTranslatedPostEdited.translationModelVersion) &&
        Objects.equals(this.editingTool, collectionDetailsMachineTranslatedPostEdited.editingTool) &&
        Objects.equals(this.editingToolVersion, collectionDetailsMachineTranslatedPostEdited.editingToolVersion) &&
        Objects.equals(this.contributor, collectionDetailsMachineTranslatedPostEdited.contributor) &&
        Objects.equals(this.timeSpentInSeconds, collectionDetailsMachineTranslatedPostEdited.timeSpentInSeconds) &&
        Objects.equals(this.numOfKeysPressed, collectionDetailsMachineTranslatedPostEdited.numOfKeysPressed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(translationModel, translationModelVersion, editingTool, editingToolVersion, contributor, timeSpentInSeconds, numOfKeysPressed);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionDetailsMachineTranslatedPostEdited {\n");
    
    sb.append("    translationModel: ").append(toIndentedString(translationModel)).append("\n");
    sb.append("    translationModelVersion: ").append(toIndentedString(translationModelVersion)).append("\n");
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
