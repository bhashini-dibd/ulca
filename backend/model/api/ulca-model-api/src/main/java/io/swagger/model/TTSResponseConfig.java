package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AudioBitsPerSample;
import io.swagger.model.AudioChannel;
import io.swagger.model.AudioFormat;
import io.swagger.model.Encoding;
import io.swagger.model.Gender;
import io.swagger.model.LanguagePair;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TTSResponseConfig
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-10T05:17:35.492966412Z[GMT]")


public class TTSResponseConfig   {
  @JsonProperty("modelId")
  private String modelId = null;

  @JsonProperty("language")
  private LanguagePair language = null;

  @JsonProperty("gender")
  private Gender gender = null;

  @JsonProperty("audioFormat")
  private AudioFormat audioFormat = null;

  @JsonProperty("channel")
  private AudioChannel channel = null;

  @JsonProperty("samplingRate")
  private BigDecimal samplingRate = null;

  @JsonProperty("bitsPerSample")
  private AudioBitsPerSample bitsPerSample = null;

  @JsonProperty("encoding")
  private Encoding encoding = null;

  @JsonProperty("speed")
  private BigDecimal speed = new BigDecimal(1);

  @JsonProperty("duration")
  private BigDecimal duration = null;



  public TTSResponseConfig modelId(String modelId) {
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

  public TTSResponseConfig gender(Gender gender) {
    this.gender = gender;
    return this;
  }

  /**
   * Get gender
   * @return gender
   **/
  @Schema(description = "")
  
    @Valid
    public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public TTSResponseConfig audioFormat(AudioFormat audioFormat) {
    this.audioFormat = audioFormat;
    return this;
  }

  /**
   * Get audioFormat
   * @return audioFormat
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public AudioFormat getAudioFormat() {
    return audioFormat;
  }

  public void setAudioFormat(AudioFormat audioFormat) {
    this.audioFormat = audioFormat;
  }

  public TTSResponseConfig channel(AudioChannel channel) {
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

  public TTSResponseConfig bitsPerSample(AudioBitsPerSample bitsPerSample) {
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

  public TTSResponseConfig encoding(Encoding encoding) {
    this.encoding = encoding;
    return this;
  }

  /**
   * Get encoding
   * @return encoding
   **/
  @Schema(description = "")
  
    @Valid
    public Encoding getEncoding() {
    return encoding;
  }

  public void setEncoding(Encoding encoding) {
    this.encoding = encoding;
  }

  public TTSResponseConfig speed(BigDecimal speed) {
    this.speed = speed;
    return this;
  }

  /**
   * optional field to specify the speed of audio
   * @return speed
   **/
  @Schema(description = "optional field to specify the speed of audio")
  
    @Valid
    public BigDecimal getSpeed() {
    return speed;
  }

  public void setSpeed(BigDecimal speed) {
    this.speed = speed;
  }

  public TTSResponseConfig duration(BigDecimal duration) {
    this.duration = duration;
    return this;
  }

  /**
   * optional field to specify the duration of audio in milliseconds
   * @return duration
   **/
  @Schema(example = "300", description = "optional field to specify the duration of audio in milliseconds")
  
    @Valid
    public BigDecimal getDuration() {
    return duration;
  }

  public void setDuration(BigDecimal duration) {
    this.duration = duration;
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
    return Objects.equals(this.modelId, ttSResponseConfig.modelId) &&
        Objects.equals(this.language, ttSResponseConfig.language) &&
        Objects.equals(this.gender, ttSResponseConfig.gender) &&
        Objects.equals(this.audioFormat, ttSResponseConfig.audioFormat) &&
        Objects.equals(this.channel, ttSResponseConfig.channel) &&
        Objects.equals(this.samplingRate, ttSResponseConfig.samplingRate) &&
        Objects.equals(this.bitsPerSample, ttSResponseConfig.bitsPerSample) &&
        Objects.equals(this.encoding, ttSResponseConfig.encoding) &&
        Objects.equals(this.speed, ttSResponseConfig.speed) &&
        Objects.equals(this.duration, ttSResponseConfig.duration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modelId, language, gender, audioFormat, channel, samplingRate, bitsPerSample, encoding, speed, duration);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TTSResponseConfig {\n");
    
    sb.append("    modelId: ").append(toIndentedString(modelId)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    audioFormat: ").append(toIndentedString(audioFormat)).append("\n");
    sb.append("    channel: ").append(toIndentedString(channel)).append("\n");
    sb.append("    samplingRate: ").append(toIndentedString(samplingRate)).append("\n");
    sb.append("    bitsPerSample: ").append(toIndentedString(bitsPerSample)).append("\n");
    sb.append("    encoding: ").append(toIndentedString(encoding)).append("\n");
    sb.append("    speed: ").append(toIndentedString(speed)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
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
