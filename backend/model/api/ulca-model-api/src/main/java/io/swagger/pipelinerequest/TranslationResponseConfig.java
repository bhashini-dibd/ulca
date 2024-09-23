package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.model.LanguagePair;


/**
 * TranslationResponseConfig
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-09-10T06:09:20.888267836Z[GMT]")


public class TranslationResponseConfig   {
  @JsonProperty("serviceId")

  private String serviceId = null;

  @JsonProperty("modelId")

  private String modelId = null;

  @JsonProperty("language")

  private LanguagePair language = null;

  @JsonProperty("inputFormat")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private SupportedFormats inputFormat = null;

  @JsonProperty("outputFormat")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private SupportedFormats outputFormat = null;

  @JsonProperty("defaultModel")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private Boolean defaultModel = null;


  public TranslationResponseConfig serviceId(String serviceId) { 

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

  public TranslationResponseConfig modelId(String modelId) { 

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

  public TranslationResponseConfig language(LanguagePair language) { 

    this.language = language;
    return this;
  }

  /**
   * Get language
   * @return language
   **/
  
  @Schema(required = true, description = "")
  
@Valid
  @NotNull
  public LanguagePair getLanguage() {  
    return language;
  }



  public void setLanguage(LanguagePair language) { 

    this.language = language;
  }

  public TranslationResponseConfig inputFormat(SupportedFormats inputFormat) { 

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

  public TranslationResponseConfig outputFormat(SupportedFormats outputFormat) { 

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

  public TranslationResponseConfig defaultModel(Boolean defaultModel) { 

    this.defaultModel = defaultModel;
    return this;
  }

  /**
   * Get defaultModel
   * @return defaultModel
   **/
  
  @Schema(description = "")
  
  public Boolean isDefaultModel() {  
    return defaultModel;
  }



  public void setDefaultModel(Boolean defaultModel) { 
    this.defaultModel = defaultModel;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TranslationResponseConfig translationResponseConfig = (TranslationResponseConfig) o;
    return Objects.equals(this.serviceId, translationResponseConfig.serviceId) &&
        Objects.equals(this.modelId, translationResponseConfig.modelId) &&
        Objects.equals(this.language, translationResponseConfig.language) &&
        Objects.equals(this.inputFormat, translationResponseConfig.inputFormat) &&
        Objects.equals(this.outputFormat, translationResponseConfig.outputFormat) &&
        Objects.equals(this.defaultModel, translationResponseConfig.defaultModel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceId, modelId, language, inputFormat, outputFormat, defaultModel);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TranslationResponseConfig {\n");
    
    sb.append("    serviceId: ").append(toIndentedString(serviceId)).append("\n");
    sb.append("    modelId: ").append(toIndentedString(modelId)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    inputFormat: ").append(toIndentedString(inputFormat)).append("\n");
    sb.append("    outputFormat: ").append(toIndentedString(outputFormat)).append("\n");
    sb.append("    defaultModel: ").append(toIndentedString(defaultModel)).append("\n");
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
