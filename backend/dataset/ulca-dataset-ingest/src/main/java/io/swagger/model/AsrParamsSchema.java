package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.AudioBitsPerSample;
import io.swagger.model.AudioChannel;
import io.swagger.model.AudioFormat;
import io.swagger.model.AudioQualityEvaluation;
import io.swagger.model.CollectionMethodAudio;
import io.swagger.model.DatasetCommonParamsSchema;
import io.swagger.model.DatasetType;
import io.swagger.model.Domain;
import io.swagger.model.Gender;
import io.swagger.model.LanguagePair;
import io.swagger.model.License;
import io.swagger.model.MixedDataSource;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AsrParamsSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:41:06.560Z[GMT]")


public class AsrParamsSchema extends DatasetCommonParamsSchema implements OneOfDatasetParamsSchemaParams {
  @JsonProperty("mixedDataSource")
  private MixedDataSource mixedDataSource = null;

  @JsonProperty("format")
  private AudioFormat format = null;

  @JsonProperty("channel")
  private AudioChannel channel = null;

  @JsonProperty("samplingRate")
  private BigDecimal samplingRate = null;

  @JsonProperty("bitsPerSample")
  private AudioBitsPerSample bitsPerSample = null;

  @JsonProperty("numberOfSpeakers")
  private BigDecimal numberOfSpeakers = null;

  @JsonProperty("gender")
  private Gender gender = null;

  /**
   * age range
   */
  public enum AgeEnum {
    _1_10("1-10"),
    
    _11_20("11-20"),
    
    _21_60("21-60"),
    
    _61_100("61-100");

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

  @JsonProperty("collectionMethod")
  private CollectionMethodAudio collectionMethod = null;

  public AsrParamsSchema mixedDataSource(MixedDataSource mixedDataSource) {
    this.mixedDataSource = mixedDataSource;
    return this;
  }

  /**
   * Get mixedDataSource
   * @return mixedDataSource
   **/
  @Schema(description = "")
  
    @Valid
    public MixedDataSource getMixedDataSource() {
    return mixedDataSource;
  }

  public void setMixedDataSource(MixedDataSource mixedDataSource) {
    this.mixedDataSource = mixedDataSource;
  }

  public AsrParamsSchema format(AudioFormat format) {
    this.format = format;
    return this;
  }

  /**
   * Get format
   * @return format
   **/
  @Schema(description = "")
  
    @Valid
    public AudioFormat getFormat() {
    return format;
  }

  public void setFormat(AudioFormat format) {
    this.format = format;
  }

  public AsrParamsSchema channel(AudioChannel channel) {
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

  public AsrParamsSchema samplingRate(BigDecimal samplingRate) {
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

  public AsrParamsSchema bitsPerSample(AudioBitsPerSample bitsPerSample) {
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

  public AsrParamsSchema numberOfSpeakers(BigDecimal numberOfSpeakers) {
    this.numberOfSpeakers = numberOfSpeakers;
    return this;
  }

  /**
   * number of speakers in the recorded audio file
   * minimum: 1
   * @return numberOfSpeakers
   **/
  @Schema(example = "3", description = "number of speakers in the recorded audio file")
  
    @Valid
  @DecimalMin("1")  public BigDecimal getNumberOfSpeakers() {
    return numberOfSpeakers;
  }

  public void setNumberOfSpeakers(BigDecimal numberOfSpeakers) {
    this.numberOfSpeakers = numberOfSpeakers;
  }

  public AsrParamsSchema gender(Gender gender) {
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

  public AsrParamsSchema age(AgeEnum age) {
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

  public AsrParamsSchema dialect(DialectEnum dialect) {
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

  public AsrParamsSchema snr(AudioQualityEvaluation snr) {
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

  public AsrParamsSchema collectionMethod(CollectionMethodAudio collectionMethod) {
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
    AsrParamsSchema asrParamsSchema = (AsrParamsSchema) o;
    return Objects.equals(this.mixedDataSource, asrParamsSchema.mixedDataSource) &&
        Objects.equals(this.format, asrParamsSchema.format) &&
        Objects.equals(this.channel, asrParamsSchema.channel) &&
        Objects.equals(this.samplingRate, asrParamsSchema.samplingRate) &&
        Objects.equals(this.bitsPerSample, asrParamsSchema.bitsPerSample) &&
        Objects.equals(this.numberOfSpeakers, asrParamsSchema.numberOfSpeakers) &&
        Objects.equals(this.gender, asrParamsSchema.gender) &&
        Objects.equals(this.age, asrParamsSchema.age) &&
        Objects.equals(this.dialect, asrParamsSchema.dialect) &&
        Objects.equals(this.snr, asrParamsSchema.snr) &&
        Objects.equals(this.collectionMethod, asrParamsSchema.collectionMethod) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mixedDataSource, format, channel, samplingRate, bitsPerSample, numberOfSpeakers, gender, age, dialect, snr, collectionMethod, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AsrParamsSchema {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    mixedDataSource: ").append(toIndentedString(mixedDataSource)).append("\n");
    sb.append("    format: ").append(toIndentedString(format)).append("\n");
    sb.append("    channel: ").append(toIndentedString(channel)).append("\n");
    sb.append("    samplingRate: ").append(toIndentedString(samplingRate)).append("\n");
    sb.append("    bitsPerSample: ").append(toIndentedString(bitsPerSample)).append("\n");
    sb.append("    numberOfSpeakers: ").append(toIndentedString(numberOfSpeakers)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    age: ").append(toIndentedString(age)).append("\n");
    sb.append("    dialect: ").append(toIndentedString(dialect)).append("\n");
    sb.append("    snr: ").append(toIndentedString(snr)).append("\n");
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
