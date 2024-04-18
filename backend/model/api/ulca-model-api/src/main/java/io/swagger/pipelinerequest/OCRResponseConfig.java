package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.model.LanguagePair;


/**
 * OCRResponseConfig
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-04-18T12:02:12.925631920Z[GMT]")


public class OCRResponseConfig   {
  @JsonProperty("serviceId")
  private String serviceId = null;

  @JsonProperty("modelId")
  private String modelId = null;

  @JsonProperty("language")
  private LanguagePair language = null;

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

  @JsonProperty("inputFormat")
  private SupportedFormats inputFormat = null;

  @JsonProperty("outputFormat")
  private SupportedFormats outputFormat = null;

  public OCRResponseConfig serviceId(String serviceId) {
    this.serviceId = serviceId;
    return this;
  }

  /**
   * Unique identifier of model
   * @return serviceId
   **/
  @Schema(example = "103", required = true, description = "Unique identifier of model")
      @NotNull

    public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public OCRResponseConfig modelId(String modelId) {
    this.modelId = modelId;
    return this;
  }

  /**
   * Unique identifier of model
   * @return modelId
   **/
  @Schema(example = "63c9586ea0e5e81614ff96a8", required = true, description = "Unique identifier of model")
      @NotNull

    public String getModelId() {
    return modelId;
  }

  public void setModelId(String modelId) {
    this.modelId = modelId;
  }

  public OCRResponseConfig language(LanguagePair language) {
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

  public OCRResponseConfig detectionLevel(DetectionLevelEnum detectionLevel) {
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

  public OCRResponseConfig modality(ModalityEnum modality) {
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

  public OCRResponseConfig inputFormat(SupportedFormats inputFormat) {
    this.inputFormat = inputFormat;
    return this;
  }

  /**
   * Get inputFormat
   * @return inputFormat
   **/
  @Schema(description = "")
  
    @Valid
    public SupportedFormats getInputFormat() {
    return inputFormat;
  }

  public void setInputFormat(SupportedFormats inputFormat) {
    this.inputFormat = inputFormat;
  }

  public OCRResponseConfig outputFormat(SupportedFormats outputFormat) {
    this.outputFormat = outputFormat;
    return this;
  }

  /**
   * Get outputFormat
   * @return outputFormat
   **/
  @Schema(description = "")
  
    @Valid
    public SupportedFormats getOutputFormat() {
    return outputFormat;
  }

  public void setOutputFormat(SupportedFormats outputFormat) {
    this.outputFormat = outputFormat;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OCRResponseConfig ocRResponseConfig = (OCRResponseConfig) o;
    return Objects.equals(this.serviceId, ocRResponseConfig.serviceId) &&
        Objects.equals(this.modelId, ocRResponseConfig.modelId) &&
        Objects.equals(this.language, ocRResponseConfig.language) &&
        Objects.equals(this.detectionLevel, ocRResponseConfig.detectionLevel) &&
        Objects.equals(this.modality, ocRResponseConfig.modality) &&
        Objects.equals(this.inputFormat, ocRResponseConfig.inputFormat) &&
        Objects.equals(this.outputFormat, ocRResponseConfig.outputFormat);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceId, modelId, language, detectionLevel, modality, inputFormat, outputFormat);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OCRResponseConfig {\n");
    
    sb.append("    serviceId: ").append(toIndentedString(serviceId)).append("\n");
    sb.append("    modelId: ").append(toIndentedString(modelId)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    detectionLevel: ").append(toIndentedString(detectionLevel)).append("\n");
    sb.append("    modality: ").append(toIndentedString(modality)).append("\n");
    sb.append("    inputFormat: ").append(toIndentedString(inputFormat)).append("\n");
    sb.append("    outputFormat: ").append(toIndentedString(outputFormat)).append("\n");
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
