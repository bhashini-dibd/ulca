package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TxtLangDetectionConfig
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:33:39.764Z[GMT]")


public class TxtLangDetectionConfig   {
  @JsonProperty("modelId")
  private Integer modelId = null;

  @JsonProperty("isSentence")
  private Boolean isSentence = false;

  @JsonProperty("numSuggestions")
  private Integer numSuggestions = null;

  public TxtLangDetectionConfig modelId(Integer modelId) {
    this.modelId = modelId;
    return this;
  }

  /**
   * Unique identifier of model
   * @return modelId
   **/
  @Schema(example = "103", description = "Unique identifier of model")
  
    public Integer getModelId() {
    return modelId;
  }

  public void setModelId(Integer modelId) {
    this.modelId = modelId;
  }

  public TxtLangDetectionConfig isSentence(Boolean isSentence) {
    this.isSentence = isSentence;
    return this;
  }

  /**
   * Expects sentence or words
   * @return isSentence
   **/
  @Schema(example = "false", description = "Expects sentence or words")
  
    public Boolean isIsSentence() {
    return isSentence;
  }

  public void setIsSentence(Boolean isSentence) {
    this.isSentence = isSentence;
  }

  public TxtLangDetectionConfig numSuggestions(Integer numSuggestions) {
    this.numSuggestions = numSuggestions;
    return this;
  }

  /**
   * expected number of predictions
   * @return numSuggestions
   **/
  @Schema(example = "4", description = "expected number of predictions")
  
    public Integer getNumSuggestions() {
    return numSuggestions;
  }

  public void setNumSuggestions(Integer numSuggestions) {
    this.numSuggestions = numSuggestions;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TxtLangDetectionConfig txtLangDetectionConfig = (TxtLangDetectionConfig) o;
    return Objects.equals(this.modelId, txtLangDetectionConfig.modelId) &&
        Objects.equals(this.isSentence, txtLangDetectionConfig.isSentence) &&
        Objects.equals(this.numSuggestions, txtLangDetectionConfig.numSuggestions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modelId, isSentence, numSuggestions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TxtLangDetectionConfig {\n");
    
    sb.append("    modelId: ").append(toIndentedString(modelId)).append("\n");
    sb.append("    isSentence: ").append(toIndentedString(isSentence)).append("\n");
    sb.append("    numSuggestions: ").append(toIndentedString(numSuggestions)).append("\n");
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
