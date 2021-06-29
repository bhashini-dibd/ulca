package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.AudioBitsPerSample;
import io.swagger.model.AudioChannel;
import io.swagger.model.AudioQualityEvaluation;
import io.swagger.model.CollectionMethodAudio;
import io.swagger.model.Gender;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-29T11:44:46.135Z[GMT]")


public class AsrRowSchema  implements OneOfDatasetDataRowSchemaData {
  @JsonProperty("audioFilename")
  private String audioFilename = null;

  @JsonProperty("text")
  private String text = null;

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

  @JsonProperty("gender")
  private Gender gender = null;

  /**
   * age range
   */
  public enum AgeEnum {
    _0_9("0-9"),
    
    _10_20("10-20"),
    
    _21_60("21-60"),
    
    _60_100("60-100");

    private String value;

    AgeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AgeEnum fromValue(String text) {
      for (AgeEnum b : AgeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("age")
  private AgeEnum age = null;

  /**
   * valid dialact of the submitted language.
   */
  public enum DialectEnum {
    HI_THETHI("hi-thethi"),
    
    HI_MAGAHI("hi-magahi"),
    
    TA_MADURAI("ta-madurai");

    private String value;

    DialectEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DialectEnum fromValue(String text) {
      for (DialectEnum b : DialectEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("dialect")
  private DialectEnum dialect = null;

  @JsonProperty("snr")
  private AudioQualityEvaluation snr = null;

  @JsonProperty("startTime")
  private String startTime = null;

  @JsonProperty("endTime")
  private String endTime = null;

  @JsonProperty("collectionMethod")
  private CollectionMethodAudio collectionMethod = null;

  public AsrRowSchema audioFilename(String audioFilename) {
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

  public AsrRowSchema text(String text) {
    this.text = text;
    return this;
  }

  /**
   * textual output of the audio
   * @return text
   **/
  @Schema(required = true, description = "textual output of the audio")
      @NotNull

    public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public AsrRowSchema speaker(String speaker) {
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

  public AsrRowSchema duration(BigDecimal duration) {
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

  public AsrRowSchema collectionSource(Source collectionSource) {
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

  public AsrRowSchema channel(AudioChannel channel) {
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

  public AsrRowSchema samplingRate(BigDecimal samplingRate) {
    this.samplingRate = samplingRate;
    return this;
  }

  /**
   * Get samplingRate
   * @return samplingRate
   **/
  @Schema(description = "")
  
    @Valid
    public BigDecimal getSamplingRate() {
    return samplingRate;
  }

  public void setSamplingRate(BigDecimal samplingRate) {
    this.samplingRate = samplingRate;
  }

  public AsrRowSchema bitsPerSample(AudioBitsPerSample bitsPerSample) {
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

  public AsrRowSchema gender(Gender gender) {
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

  public AsrRowSchema age(AgeEnum age) {
    this.age = age;
    return this;
  }

  /**
   * age range
   * @return age
   **/
  @Schema(description = "age range")
  
    public AgeEnum getAge() {
    return age;
  }

  public void setAge(AgeEnum age) {
    this.age = age;
  }

  public AsrRowSchema dialect(DialectEnum dialect) {
    this.dialect = dialect;
    return this;
  }

  /**
   * valid dialact of the submitted language.
   * @return dialect
   **/
  @Schema(example = "langaugeCode-<location or dialect name>", description = "valid dialact of the submitted language.")
  
    public DialectEnum getDialect() {
    return dialect;
  }

  public void setDialect(DialectEnum dialect) {
    this.dialect = dialect;
  }

  public AsrRowSchema snr(AudioQualityEvaluation snr) {
    this.snr = snr;
    return this;
  }

  /**
   * Get snr
   * @return snr
   **/
  @Schema(description = "")
  
    @Valid
    public AudioQualityEvaluation getSnr() {
    return snr;
  }

  public void setSnr(AudioQualityEvaluation snr) {
    this.snr = snr;
  }

  public AsrRowSchema startTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * Time of the audio from where the processing starts. Format : hh-mm-ss
   * @return startTime
   **/
  @Schema(description = "Time of the audio from where the processing starts. Format : hh-mm-ss")
  
    public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public AsrRowSchema endTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

  /**
   * Time of the audio where the processing end. Format : hh-mm-ss
   * @return endTime
   **/
  @Schema(description = "Time of the audio where the processing end. Format : hh-mm-ss")
  
    public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public AsrRowSchema collectionMethod(CollectionMethodAudio collectionMethod) {
    this.collectionMethod = collectionMethod;
    return this;
  }

  /**
   * Get collectionMethod
   * @return collectionMethod
   **/
  @Schema(description = "")
  
    @Valid
    public CollectionMethodAudio getCollectionMethod() {
    return collectionMethod;
  }

  public void setCollectionMethod(CollectionMethodAudio collectionMethod) {
    this.collectionMethod = collectionMethod;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AsrRowSchema asrRowSchema = (AsrRowSchema) o;
    return Objects.equals(this.audioFilename, asrRowSchema.audioFilename) &&
        Objects.equals(this.text, asrRowSchema.text) &&
        Objects.equals(this.speaker, asrRowSchema.speaker) &&
        Objects.equals(this.duration, asrRowSchema.duration) &&
        Objects.equals(this.collectionSource, asrRowSchema.collectionSource) &&
        Objects.equals(this.channel, asrRowSchema.channel) &&
        Objects.equals(this.samplingRate, asrRowSchema.samplingRate) &&
        Objects.equals(this.bitsPerSample, asrRowSchema.bitsPerSample) &&
        Objects.equals(this.gender, asrRowSchema.gender) &&
        Objects.equals(this.age, asrRowSchema.age) &&
        Objects.equals(this.dialect, asrRowSchema.dialect) &&
        Objects.equals(this.snr, asrRowSchema.snr) &&
        Objects.equals(this.startTime, asrRowSchema.startTime) &&
        Objects.equals(this.endTime, asrRowSchema.endTime) &&
        Objects.equals(this.collectionMethod, asrRowSchema.collectionMethod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(audioFilename, text, speaker, duration, collectionSource, channel, samplingRate, bitsPerSample, gender, age, dialect, snr, startTime, endTime, collectionMethod);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AsrRowSchema {\n");
    
    sb.append("    audioFilename: ").append(toIndentedString(audioFilename)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    speaker: ").append(toIndentedString(speaker)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    collectionSource: ").append(toIndentedString(collectionSource)).append("\n");
    sb.append("    channel: ").append(toIndentedString(channel)).append("\n");
    sb.append("    samplingRate: ").append(toIndentedString(samplingRate)).append("\n");
    sb.append("    bitsPerSample: ").append(toIndentedString(bitsPerSample)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    age: ").append(toIndentedString(age)).append("\n");
    sb.append("    dialect: ").append(toIndentedString(dialect)).append("\n");
    sb.append("    snr: ").append(toIndentedString(snr)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    collectionMethod: ").append(toIndentedString(collectionMethod)).append("\n");
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
