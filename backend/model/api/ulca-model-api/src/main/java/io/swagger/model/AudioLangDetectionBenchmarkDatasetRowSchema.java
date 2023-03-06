package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AudioBitsPerSample;
import io.swagger.model.AudioChannel;
import io.swagger.model.Source;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the schema defines the column name present in physical file that is being pointed by dataFilename key.
 */
@Schema(description = "the schema defines the column name present in physical file that is being pointed by dataFilename key.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T03:59:56.369839514Z[GMT]")


public class AudioLangDetectionBenchmarkDatasetRowSchema  implements OneOfDatasetDataRowSchemaData {
  @JsonProperty("audioFilename")
  private String audioFilename = null;

  @JsonProperty("langCode")
  private String langCode = null;

  @JsonProperty("speaker")
  private String speaker = null;

  @JsonProperty("duration")
  private BigDecimal duration = null;

  @JsonProperty("collectionSource")
  private Source collectionSource = null;

  @JsonProperty("channel")
  private AudioChannel channel = null;

  @JsonProperty("samplingRate")
  private BigDecimal samplingRate = null;

  @JsonProperty("bitsPerSample")
  private AudioBitsPerSample bitsPerSample = null;

  public AudioLangDetectionBenchmarkDatasetRowSchema audioFilename(String audioFilename) {
    this.audioFilename = audioFilename;
    return this;
  }

  /**
   * filename of the audio file
   * @return audioFilename
   **/
  @Schema(required = true, description = "filename of the audio file")
      @NotNull

    public String getAudioFilename() {
    return audioFilename;
  }

  public void setAudioFilename(String audioFilename) {
    this.audioFilename = audioFilename;
  }

  public AudioLangDetectionBenchmarkDatasetRowSchema langCode(String langCode) {
    this.langCode = langCode;
    return this;
  }

  /**
   * Indic language code, iso-639-1, iso 639-2
   * @return langCode
   **/
  @Schema(required = true, description = "Indic language code, iso-639-1, iso 639-2")
      @NotNull

    public String getLangCode() {
    return langCode;
  }

  public void setLangCode(String langCode) {
    this.langCode = langCode;
  }

  public AudioLangDetectionBenchmarkDatasetRowSchema speaker(String speaker) {
    this.speaker = speaker;
    return this;
  }

  /**
   * speaker name or id for the audio utterance
   * @return speaker
   **/
  @Schema(description = "speaker name or id for the audio utterance")
  
    public String getSpeaker() {
    return speaker;
  }

  public void setSpeaker(String speaker) {
    this.speaker = speaker;
  }

  public AudioLangDetectionBenchmarkDatasetRowSchema duration(BigDecimal duration) {
    this.duration = duration;
    return this;
  }

  /**
   * audio duration in seconds
   * @return duration
   **/
  @Schema(description = "audio duration in seconds")
  
    @Valid
    public BigDecimal getDuration() {
    return duration;
  }

  public void setDuration(BigDecimal duration) {
    this.duration = duration;
  }

  public AudioLangDetectionBenchmarkDatasetRowSchema collectionSource(Source collectionSource) {
    this.collectionSource = collectionSource;
    return this;
  }

  /**
   * Get collectionSource
   * @return collectionSource
   **/
  @Schema(description = "")
  
    @Valid
    public Source getCollectionSource() {
    return collectionSource;
  }

  public void setCollectionSource(Source collectionSource) {
    this.collectionSource = collectionSource;
  }

  public AudioLangDetectionBenchmarkDatasetRowSchema channel(AudioChannel channel) {
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

  public AudioLangDetectionBenchmarkDatasetRowSchema samplingRate(BigDecimal samplingRate) {
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

  public AudioLangDetectionBenchmarkDatasetRowSchema bitsPerSample(AudioBitsPerSample bitsPerSample) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AudioLangDetectionBenchmarkDatasetRowSchema audioLangDetectionBenchmarkDatasetRowSchema = (AudioLangDetectionBenchmarkDatasetRowSchema) o;
    return Objects.equals(this.audioFilename, audioLangDetectionBenchmarkDatasetRowSchema.audioFilename) &&
        Objects.equals(this.langCode, audioLangDetectionBenchmarkDatasetRowSchema.langCode) &&
        Objects.equals(this.speaker, audioLangDetectionBenchmarkDatasetRowSchema.speaker) &&
        Objects.equals(this.duration, audioLangDetectionBenchmarkDatasetRowSchema.duration) &&
        Objects.equals(this.collectionSource, audioLangDetectionBenchmarkDatasetRowSchema.collectionSource) &&
        Objects.equals(this.channel, audioLangDetectionBenchmarkDatasetRowSchema.channel) &&
        Objects.equals(this.samplingRate, audioLangDetectionBenchmarkDatasetRowSchema.samplingRate) &&
        Objects.equals(this.bitsPerSample, audioLangDetectionBenchmarkDatasetRowSchema.bitsPerSample);
  }

  @Override
  public int hashCode() {
    return Objects.hash(audioFilename, langCode, speaker, duration, collectionSource, channel, samplingRate, bitsPerSample);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AudioLangDetectionBenchmarkDatasetRowSchema {\n");
    
    sb.append("    audioFilename: ").append(toIndentedString(audioFilename)).append("\n");
    sb.append("    langCode: ").append(toIndentedString(langCode)).append("\n");
    sb.append("    speaker: ").append(toIndentedString(speaker)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    collectionSource: ").append(toIndentedString(collectionSource)).append("\n");
    sb.append("    channel: ").append(toIndentedString(channel)).append("\n");
    sb.append("    samplingRate: ").append(toIndentedString(samplingRate)).append("\n");
    sb.append("    bitsPerSample: ").append(toIndentedString(bitsPerSample)).append("\n");
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
