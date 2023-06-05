package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.LanguagePair;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OCRConfig
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-11T12:21:45.383626560Z[GMT]")


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

  @JsonProperty("languages")
  @Valid
  private List<LanguagePair> languages = new ArrayList<LanguagePair>();

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

  public OCRConfig languages(List<LanguagePair> languages) {
    this.languages = languages;
    return this;
  }

  public OCRConfig addLanguagesItem(LanguagePair languagesItem) {
    this.languages.add(languagesItem);
    return this;
  }

  /**
   * list of
   * @return languages
   **/
  @Schema(required = true, description = "list of")
      @NotNull
    @Valid
    public List<LanguagePair> getLanguages() {
    return languages;
  }

  public void setLanguages(List<LanguagePair> languages) {
    this.languages = languages;
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
        Objects.equals(this.languages, ocRConfig.languages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modelId, detectionLevel, modality, languages);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OCRConfig {\n");
    
    sb.append("    modelId: ").append(toIndentedString(modelId)).append("\n");
    sb.append("    detectionLevel: ").append(toIndentedString(detectionLevel)).append("\n");
    sb.append("    modality: ").append(toIndentedString(modality)).append("\n");
    sb.append("    languages: ").append(toIndentedString(languages)).append("\n");
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
