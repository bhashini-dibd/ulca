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
 * machine generated and post edited collection details
 */
@Schema(description = "machine generated and post edited collection details")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-25T09:44:34.039Z[GMT]")


public class CollectionDetailsMachineGeneratedPostEdited  implements OneOfGlossaryDatasetCollectionMethodCollectionDetails, OneOfNerDatasetCollectionMethodCollectionDetails {
  @JsonProperty("model")
  private String model = null;

  @JsonProperty("modelVersion")
  private String modelVersion = null;

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

  public CollectionDetailsMachineGeneratedPostEdited model(String model) {
    this.model = model;
    return this;
  }

  /**
   * name of the  model/engine used
   * @return model
   **/
  @Schema(example = "google translation", description = "name of the  model/engine used")
  
    public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public CollectionDetailsMachineGeneratedPostEdited modelVersion(String modelVersion) {
    this.modelVersion = modelVersion;
    return this;
  }

  /**
   * model/engine version
   * @return modelVersion
   **/
  @Schema(description = "model/engine version")
  
    public String getModelVersion() {
    return modelVersion;
  }

  public void setModelVersion(String modelVersion) {
    this.modelVersion = modelVersion;
  }

  public CollectionDetailsMachineGeneratedPostEdited editingTool(String editingTool) {
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

  public CollectionDetailsMachineGeneratedPostEdited editingToolVersion(String editingToolVersion) {
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

  public CollectionDetailsMachineGeneratedPostEdited contributor(Contributor contributor) {
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

  public CollectionDetailsMachineGeneratedPostEdited timeSpentInSeconds(BigDecimal timeSpentInSeconds) {
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

  public CollectionDetailsMachineGeneratedPostEdited numOfKeysPressed(BigDecimal numOfKeysPressed) {
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
    CollectionDetailsMachineGeneratedPostEdited collectionDetailsMachineGeneratedPostEdited = (CollectionDetailsMachineGeneratedPostEdited) o;
    return Objects.equals(this.model, collectionDetailsMachineGeneratedPostEdited.model) &&
        Objects.equals(this.modelVersion, collectionDetailsMachineGeneratedPostEdited.modelVersion) &&
        Objects.equals(this.editingTool, collectionDetailsMachineGeneratedPostEdited.editingTool) &&
        Objects.equals(this.editingToolVersion, collectionDetailsMachineGeneratedPostEdited.editingToolVersion) &&
        Objects.equals(this.contributor, collectionDetailsMachineGeneratedPostEdited.contributor) &&
        Objects.equals(this.timeSpentInSeconds, collectionDetailsMachineGeneratedPostEdited.timeSpentInSeconds) &&
        Objects.equals(this.numOfKeysPressed, collectionDetailsMachineGeneratedPostEdited.numOfKeysPressed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(model, modelVersion, editingTool, editingToolVersion, contributor, timeSpentInSeconds, numOfKeysPressed);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionDetailsMachineGeneratedPostEdited {\n");
    
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    modelVersion: ").append(toIndentedString(modelVersion)).append("\n");
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
