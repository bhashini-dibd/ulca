package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.AudioBitsPerSample;
import io.swagger.model.AudioChannel;
import io.swagger.model.AudioFormat;
import io.swagger.model.AudioPostProcessors;
import io.swagger.model.Domain;
import io.swagger.model.LanguagePair;
import io.swagger.model.TranscriptionFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * RecognitionConfig
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:57:01.789Z[GMT]")


public class RecognitionConfig   {
  @JsonProperty("language")
  private LanguagePair language = null;

  @JsonProperty("audioFormat")
  private AudioFormat audioFormat = null;

  @JsonProperty("channel")
  private AudioChannel channel = null;

  @JsonProperty("samplingRate")
  private BigDecimal samplingRate = null;

  @JsonProperty("bitsPerSample")
  private AudioBitsPerSample bitsPerSample = null;

  @JsonProperty("transcriptionFormat")
  private TranscriptionFormat transcriptionFormat = null;

  @JsonProperty("domain")
  private Domain domain = null;

  @JsonProperty("postProcessors")
  private AudioPostProcessors postProcessors = null;

  @JsonProperty("detailed")
  private Boolean detailed = null;

  @JsonProperty("punctuation")
  private Boolean punctuation = null;

  /**
   * Gets or Sets model
   */
  public enum ModelEnum {
    COMMAND_AND_SEARCH("command_and_search"),
    
    PHONE_CALL("phone_call"),
    
    VIDEO("video"),
    
    DEFAULT("default");

    private String value;

    ModelEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ModelEnum fromValue(String text) {
      for (ModelEnum b : ModelEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("model")
  private ModelEnum model = null;

  public RecognitionConfig language(LanguagePair language) {
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

  public RecognitionConfig audioFormat(AudioFormat audioFormat) {
    this.audioFormat = audioFormat;
    return this;
  }

  /**
   * Get audioFormat
   * @return audioFormat
   **/
  @Schema(description = "")
  
    @Valid
    public AudioFormat getAudioFormat() {
    return audioFormat;
  }

  public void setAudioFormat(AudioFormat audioFormat) {
    this.audioFormat = audioFormat;
  }

  public RecognitionConfig channel(AudioChannel channel) {
    this.channel = channel;
    return this;
  }

  /**
   * Get channel
   * @return channel
   **/
  @Schema(description = "")
  
    @Valid
    public AudioChannel getChannel() {
    return channel;
  }

  public void setChannel(AudioChannel channel) {
    this.channel = channel;
  }

  public RecognitionConfig samplingRate(BigDecimal samplingRate) {
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

  public RecognitionConfig bitsPerSample(AudioBitsPerSample bitsPerSample) {
    this.bitsPerSample = bitsPerSample;
    return this;
  }

  /**
   * Get bitsPerSample
   * @return bitsPerSample
   **/
  @Schema(description = "")
  
    @Valid
    public AudioBitsPerSample getBitsPerSample() {
    return bitsPerSample;
  }

  public void setBitsPerSample(AudioBitsPerSample bitsPerSample) {
    this.bitsPerSample = bitsPerSample;
  }

  public RecognitionConfig transcriptionFormat(TranscriptionFormat transcriptionFormat) {
    this.transcriptionFormat = transcriptionFormat;
    return this;
  }

  /**
   * Get transcriptionFormat
   * @return transcriptionFormat
   **/
  @Schema(description = "")
  
    @Valid
    public TranscriptionFormat getTranscriptionFormat() {
    return transcriptionFormat;
  }

  public void setTranscriptionFormat(TranscriptionFormat transcriptionFormat) {
    this.transcriptionFormat = transcriptionFormat;
  }

  public RecognitionConfig domain(Domain domain) {
    this.domain = domain;
    return this;
  }

  /**
   * Get domain
   * @return domain
   **/
  @Schema(description = "")
  
    @Valid
    public Domain getDomain() {
    return domain;
  }

  public void setDomain(Domain domain) {
    this.domain = domain;
  }

  public RecognitionConfig postProcessors(AudioPostProcessors postProcessors) {
    this.postProcessors = postProcessors;
    return this;
  }

  /**
   * Get postProcessors
   * @return postProcessors
   **/
  @Schema(description = "")
  
    @Valid
    public AudioPostProcessors getPostProcessors() {
    return postProcessors;
  }

  public void setPostProcessors(AudioPostProcessors postProcessors) {
    this.postProcessors = postProcessors;
  }

  public RecognitionConfig detailed(Boolean detailed) {
    this.detailed = detailed;
    return this;
  }

  /**
   * to specify whether details are required in output like SNR, sampling rate
   * @return detailed
   **/
  @Schema(description = "to specify whether details are required in output like SNR, sampling rate")
  
    public Boolean isDetailed() {
    return detailed;
  }

  public void setDetailed(Boolean detailed) {
    this.detailed = detailed;
  }

  public RecognitionConfig punctuation(Boolean punctuation) {
    this.punctuation = punctuation;
    return this;
  }

  /**
   * Get punctuation
   * @return punctuation
   **/
  @Schema(example = "true", description = "")
  
    public Boolean isPunctuation() {
    return punctuation;
  }

  public void setPunctuation(Boolean punctuation) {
    this.punctuation = punctuation;
  }

  public RecognitionConfig model(ModelEnum model) {
    this.model = model;
    return this;
  }

  /**
   * Get model
   * @return model
   **/
  @Schema(description = "")
  
    public ModelEnum getModel() {
    return model;
  }

  public void setModel(ModelEnum model) {
    this.model = model;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecognitionConfig recognitionConfig = (RecognitionConfig) o;
    return Objects.equals(this.language, recognitionConfig.language) &&
        Objects.equals(this.audioFormat, recognitionConfig.audioFormat) &&
        Objects.equals(this.channel, recognitionConfig.channel) &&
        Objects.equals(this.samplingRate, recognitionConfig.samplingRate) &&
        Objects.equals(this.bitsPerSample, recognitionConfig.bitsPerSample) &&
        Objects.equals(this.transcriptionFormat, recognitionConfig.transcriptionFormat) &&
        Objects.equals(this.domain, recognitionConfig.domain) &&
        Objects.equals(this.postProcessors, recognitionConfig.postProcessors) &&
        Objects.equals(this.detailed, recognitionConfig.detailed) &&
        Objects.equals(this.punctuation, recognitionConfig.punctuation) &&
        Objects.equals(this.model, recognitionConfig.model);
  }

  @Override
  public int hashCode() {
    return Objects.hash(language, audioFormat, channel, samplingRate, bitsPerSample, transcriptionFormat, domain, postProcessors, detailed, punctuation, model);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecognitionConfig {\n");
    
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    audioFormat: ").append(toIndentedString(audioFormat)).append("\n");
    sb.append("    channel: ").append(toIndentedString(channel)).append("\n");
    sb.append("    samplingRate: ").append(toIndentedString(samplingRate)).append("\n");
    sb.append("    bitsPerSample: ").append(toIndentedString(bitsPerSample)).append("\n");
    sb.append("    transcriptionFormat: ").append(toIndentedString(transcriptionFormat)).append("\n");
    sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
    sb.append("    postProcessors: ").append(toIndentedString(postProcessors)).append("\n");
    sb.append("    detailed: ").append(toIndentedString(detailed)).append("\n");
    sb.append("    punctuation: ").append(toIndentedString(punctuation)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
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
