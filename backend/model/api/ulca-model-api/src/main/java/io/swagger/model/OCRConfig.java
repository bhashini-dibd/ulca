package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.LanguagePair;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OCRConfig
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-08-02T06:46:17.068Z[GMT]")


public class OCRConfig   {
  @JsonProperty("modelId")
  private String modelId = null;

  @JsonProperty("language")
  private LanguagePair language = null;

  public OCRConfig modelId(String modelId) {
    this.modelId = modelId;
    return this;
  }

  /**
   * Unique identifier of model
   * @return modelId
   **/
  @Schema(example = "103", description = "Unique identifier of model")
  
    public String getModelId() {
    return modelId;
  }

  public void setModelId(String modelId) {
    this.modelId = modelId;
  }

  public OCRConfig language(LanguagePair language) {
    this.language = language;
    return this;
  }

  /**
   * Get language
   * @return language
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public LanguagePair getLanguage() {
    return language;
  }

  public void setLanguage(LanguagePair language) {
    this.language = language;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OCRConfig ocRConfig = (OCRConfig) o;
    return Objects.equals(this.modelId, ocRConfig.modelId) &&
        Objects.equals(this.language, ocRConfig.language);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modelId, language);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OCRConfig {\n");
    
    sb.append("    modelId: ").append(toIndentedString(modelId)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
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
