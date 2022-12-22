package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * RecognitionDetail
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:57:01.789Z[GMT]")


public class RecognitionDetail   {
  @JsonProperty("channelTag")
  private Integer channelTag = null;

  @JsonProperty("languageCode")
  private String languageCode = null;

  @JsonProperty("snr")
  private Integer snr = null;

  @JsonProperty("samplingRate")
  private Integer samplingRate = null;

  @JsonProperty("bitsPerSample")
  private Integer bitsPerSample = null;

  public RecognitionDetail channelTag(Integer channelTag) {
    this.channelTag = channelTag;
    return this;
  }

  /**
   * For multi-channel audio, this is the channel number corresponding to the recognized result for the audio from that channel. For audioChannelCount = N, its output values can range from '1' to 'N'
   * @return channelTag
   **/
  @Schema(description = "For multi-channel audio, this is the channel number corresponding to the recognized result for the audio from that channel. For audioChannelCount = N, its output values can range from '1' to 'N'")
  
    public Integer getChannelTag() {
    return channelTag;
  }

  public void setChannelTag(Integer channelTag) {
    this.channelTag = channelTag;
  }

  public RecognitionDetail languageCode(String languageCode) {
    this.languageCode = languageCode;
    return this;
  }

  /**
   * This language code was detected to have the most likelihood of being spoken in the audio
   * @return languageCode
   **/
  @Schema(description = "This language code was detected to have the most likelihood of being spoken in the audio")
  
    public String getLanguageCode() {
    return languageCode;
  }

  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  public RecognitionDetail snr(Integer snr) {
    this.snr = snr;
    return this;
  }

  /**
   * sound to noise ratio of audio
   * @return snr
   **/
  @Schema(description = "sound to noise ratio of audio")
  
    public Integer getSnr() {
    return snr;
  }

  public void setSnr(Integer snr) {
    this.snr = snr;
  }

  public RecognitionDetail samplingRate(Integer samplingRate) {
    this.samplingRate = samplingRate;
    return this;
  }

  /**
   * sampling rate of audio
   * @return samplingRate
   **/
  @Schema(description = "sampling rate of audio")
  
    public Integer getSamplingRate() {
    return samplingRate;
  }

  public void setSamplingRate(Integer samplingRate) {
    this.samplingRate = samplingRate;
  }

  public RecognitionDetail bitsPerSample(Integer bitsPerSample) {
    this.bitsPerSample = bitsPerSample;
    return this;
  }

  /**
   * bitsPerSample rate of audio
   * @return bitsPerSample
   **/
  @Schema(description = "bitsPerSample rate of audio")
  
    public Integer getBitsPerSample() {
    return bitsPerSample;
  }

  public void setBitsPerSample(Integer bitsPerSample) {
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
    RecognitionDetail recognitionDetail = (RecognitionDetail) o;
    return Objects.equals(this.channelTag, recognitionDetail.channelTag) &&
        Objects.equals(this.languageCode, recognitionDetail.languageCode) &&
        Objects.equals(this.snr, recognitionDetail.snr) &&
        Objects.equals(this.samplingRate, recognitionDetail.samplingRate) &&
        Objects.equals(this.bitsPerSample, recognitionDetail.bitsPerSample);
  }

  @Override
  public int hashCode() {
    return Objects.hash(channelTag, languageCode, snr, samplingRate, bitsPerSample);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecognitionDetail {\n");
    
    sb.append("    channelTag: ").append(toIndentedString(channelTag)).append("\n");
    sb.append("    languageCode: ").append(toIndentedString(languageCode)).append("\n");
    sb.append("    snr: ").append(toIndentedString(snr)).append("\n");
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
