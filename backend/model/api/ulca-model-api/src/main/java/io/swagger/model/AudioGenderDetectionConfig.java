package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AudioFormat;
import io.swagger.model.Encoding;
import io.swagger.model.LanguagePair;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AudioGenderDetectionConfig
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-06-13T12:41:05.989770988Z[GMT]")


public class AudioGenderDetectionConfig   {
  @JsonProperty("modelId")
  private String modelId = null;

  @JsonProperty("language")
  private LanguagePair language = null;

  @JsonProperty("audioFormat")
  private AudioFormat audioFormat = null;

  @JsonProperty("samplingRate")
  private BigDecimal samplingRate = null;

  @JsonProperty("encoding")
  private Encoding encoding = null;

  @JsonProperty("preProcessors")
  private String preProcessors = null;

  @JsonProperty("postProcessors")
  private String postProcessors = null;

  public AudioGenderDetectionConfig modelId(String modelId) {
    this.modelId = modelId;
    return this;
  }

  /**
   * Unique identifier of model
   * @return modelId
   **/
  @Schema(example = "103", description = "Unique identifier of model")
      @NotNull

    public String getModelId() {
    return modelId;
  }

  public void setModelId(String modelId) {
    this.modelId = modelId;
  }

  public AudioGenderDetectionConfig language(LanguagePair language) {
    this.language = language;
    return this;
  }

  /**
   * Get language
   * @return language
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public LanguagePair getLanguage() {
    return language;
  }

  public void setLanguage(LanguagePair language) {
    this.language = language;
  }

  public AudioGenderDetectionConfig audioFormat(AudioFormat audioFormat) {
    this.audioFormat = audioFormat;
    return this;
  }

  /**
   * Get audioFormat
   * @return audioFormat
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public AudioFormat getAudioFormat() {
    return audioFormat;
  }

  public void setAudioFormat(AudioFormat audioFormat) {
    this.audioFormat = audioFormat;
  }

  public AudioGenderDetectionConfig samplingRate(BigDecimal samplingRate) {
    this.samplingRate = samplingRate;
    return this;
  }

  /**
   * sample rate of the audio file in kHz
   * @return samplingRate
   **/
  @Schema(example = "44", description = "sample rate of the audio file in kHz")
      @NotNull

    @Valid
    public BigDecimal getSamplingRate() {
    return samplingRate;
  }

  public void setSamplingRate(BigDecimal samplingRate) {
    this.samplingRate = samplingRate;
  }

  public AudioGenderDetectionConfig encoding(Encoding encoding) {
    this.encoding = encoding;
    return this;
  }

  /**
   * Get encoding
   * @return encoding
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public Encoding getEncoding() {
    return encoding;
  }

  public void setEncoding(Encoding encoding) {
    this.encoding = encoding;
  }

  public AudioGenderDetectionConfig preProcessors(String preProcessors) {
    this.preProcessors = preProcessors;
    return this;
  }

  /**
   * Get preProcessors
   * @return preProcessors
   **/
  @Schema(description = "")
      @NotNull

    public String getPreProcessors() {
    return preProcessors;
  }

  public void setPreProcessors(String preProcessors) {
    this.preProcessors = preProcessors;
  }

  public AudioGenderDetectionConfig postProcessors(String postProcessors) {
    this.postProcessors = postProcessors;
    return this;
  }

  /**
   * Get postProcessors
   * @return postProcessors
   **/
  @Schema(description = "")
      @NotNull

    public String getPostProcessors() {
    return postProcessors;
  }

  public void setPostProcessors(String postProcessors) {
    this.postProcessors = postProcessors;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AudioGenderDetectionConfig audioGenderDetectionConfig = (AudioGenderDetectionConfig) o;
    return Objects.equals(this.modelId, audioGenderDetectionConfig.modelId) &&
        Objects.equals(this.language, audioGenderDetectionConfig.language) &&
        Objects.equals(this.audioFormat, audioGenderDetectionConfig.audioFormat) &&
        Objects.equals(this.samplingRate, audioGenderDetectionConfig.samplingRate) &&
        Objects.equals(this.encoding, audioGenderDetectionConfig.encoding) &&
        Objects.equals(this.preProcessors, audioGenderDetectionConfig.preProcessors) &&
        Objects.equals(this.postProcessors, audioGenderDetectionConfig.postProcessors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modelId, language, audioFormat, samplingRate, encoding, preProcessors, postProcessors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AudioGenderDetectionConfig {\n");
    
    sb.append("    modelId: ").append(toIndentedString(modelId)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    audioFormat: ").append(toIndentedString(audioFormat)).append("\n");
    sb.append("    samplingRate: ").append(toIndentedString(samplingRate)).append("\n");
    sb.append("    encoding: ").append(toIndentedString(encoding)).append("\n");
    sb.append("    preProcessors: ").append(toIndentedString(preProcessors)).append("\n");
    sb.append("    postProcessors: ").append(toIndentedString(postProcessors)).append("\n");
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
