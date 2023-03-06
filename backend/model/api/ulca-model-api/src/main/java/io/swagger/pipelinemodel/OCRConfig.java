package io.swagger.pipelinemodel;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.LanguagePair;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OCRConfig
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T06:06:14.793576134Z[GMT]")


public class OCRConfig   {
  @JsonProperty("modelId")
  private String modelId = null;

  /**
   * on what level of text does the model works best
   */
  public enum DetectionLevelEnum {
    WORD("word"),
    
    LINE("line"),
    
    PARAGRAPH("paragraph"),
    
    PAGE("page");

    private String value;

    DetectionLevelEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DetectionLevelEnum fromValue(String text) {
      for (DetectionLevelEnum b : DetectionLevelEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("detectionLevel")
  private DetectionLevelEnum detectionLevel = DetectionLevelEnum.WORD;

  /**
   * on what type of image does the model works best
   */
  public enum ModalityEnum {
    PRINT("print"),
    
    HANDWRITTEN("handwritten"),
    
    SCENETEXT("scenetext");

    private String value;

    ModalityEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ModalityEnum fromValue(String text) {
      for (ModalityEnum b : ModalityEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("modality")
  private ModalityEnum modality = ModalityEnum.PRINT;

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

  public OCRConfig detectionLevel(DetectionLevelEnum detectionLevel) {
    this.detectionLevel = detectionLevel;
    return this;
  }

  /**
   * on what level of text does the model works best
   * @return detectionLevel
   **/
  @Schema(description = "on what level of text does the model works best")
  
    public DetectionLevelEnum getDetectionLevel() {
    return detectionLevel;
  }

  public void setDetectionLevel(DetectionLevelEnum detectionLevel) {
    this.detectionLevel = detectionLevel;
  }

  public OCRConfig modality(ModalityEnum modality) {
    this.modality = modality;
    return this;
  }

  /**
   * on what type of image does the model works best
   * @return modality
   **/
  @Schema(description = "on what type of image does the model works best")
  
    public ModalityEnum getModality() {
    return modality;
  }

  public void setModality(ModalityEnum modality) {
    this.modality = modality;
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
        Objects.equals(this.detectionLevel, ocRConfig.detectionLevel) &&
        Objects.equals(this.modality, ocRConfig.modality) &&
        Objects.equals(this.language, ocRConfig.language);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modelId, detectionLevel, modality, language);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OCRConfig {\n");
    
    sb.append("    modelId: ").append(toIndentedString(modelId)).append("\n");
    sb.append("    detectionLevel: ").append(toIndentedString(detectionLevel)).append("\n");
    sb.append("    modality: ").append(toIndentedString(modality)).append("\n");
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
