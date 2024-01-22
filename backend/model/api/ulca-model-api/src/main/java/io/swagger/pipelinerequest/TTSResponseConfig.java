package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.LanguagePair;
import io.swagger.model.VoiceTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TTSResponseConfig
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-24T05:43:51.218317839Z[GMT]")


public class TTSResponseConfig   {
  @JsonProperty("serviceId")
  private String serviceId = null;

  @JsonProperty("modelId")
  private String modelId = null;

  @JsonProperty("language")
  private LanguagePair language = null;

  @JsonProperty("supportedVoices")
  @Valid
  private List<VoiceTypes> supportedVoices = null;

  @JsonProperty("samplingRate")
  private BigDecimal samplingRate = null;

  @JsonProperty("inputFormat")
  private SupportedFormats inputFormat = null;

  @JsonProperty("outputFormat")
  private SupportedFormats outputFormat = null;

  public TTSResponseConfig serviceId(String serviceId) {
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

  public TTSResponseConfig modelId(String modelId) {
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

  public TTSResponseConfig language(LanguagePair language) {
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

  public TTSResponseConfig supportedVoices(List<VoiceTypes> supportedVoices) {
    this.supportedVoices = supportedVoices;
    return this;
  }

  public TTSResponseConfig addSupportedVoicesItem(VoiceTypes supportedVoicesItem) {
    if (this.supportedVoices == null) {
      this.supportedVoices = new ArrayList<VoiceTypes>();
    }
    this.supportedVoices.add(supportedVoicesItem);
    return this;
  }

  /**
   * list of
   * @return supportedVoices
   **/
  @Schema(example = "[\"male\",\"female\"]", description = "list of")
      @Valid
    public List<VoiceTypes> getSupportedVoices() {
    return supportedVoices;
  }

  public void setSupportedVoices(List<VoiceTypes> supportedVoices) {
    this.supportedVoices = supportedVoices;
  }

  public TTSResponseConfig samplingRate(BigDecimal samplingRate) {
    this.samplingRate = samplingRate;
    return this;
  }

  /**
   * sample rate of the audio file in kHz
   * @return samplingRate
   **/
  @Schema(example = "44", description = "sample rate of the audio file in kHz")
  
    @Valid
    public BigDecimal getSamplingRate() {
    return samplingRate;
  }

  public void setSamplingRate(BigDecimal samplingRate) {
    this.samplingRate = samplingRate;
  }

  public TTSResponseConfig inputFormat(SupportedFormats inputFormat) {
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

  public TTSResponseConfig outputFormat(SupportedFormats outputFormat) {
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
    TTSResponseConfig ttSResponseConfig = (TTSResponseConfig) o;
    return Objects.equals(this.serviceId, ttSResponseConfig.serviceId) &&
        Objects.equals(this.modelId, ttSResponseConfig.modelId) &&
        Objects.equals(this.language, ttSResponseConfig.language) &&
        Objects.equals(this.supportedVoices, ttSResponseConfig.supportedVoices) &&
        Objects.equals(this.samplingRate, ttSResponseConfig.samplingRate) &&
        Objects.equals(this.inputFormat, ttSResponseConfig.inputFormat) &&
        Objects.equals(this.outputFormat, ttSResponseConfig.outputFormat);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceId, modelId, language, supportedVoices, samplingRate, inputFormat, outputFormat);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TTSResponseConfig {\n");
    
    sb.append("    serviceId: ").append(toIndentedString(serviceId)).append("\n");
    sb.append("    modelId: ").append(toIndentedString(modelId)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    supportedVoices: ").append(toIndentedString(supportedVoices)).append("\n");
    sb.append("    samplingRate: ").append(toIndentedString(samplingRate)).append("\n");
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
