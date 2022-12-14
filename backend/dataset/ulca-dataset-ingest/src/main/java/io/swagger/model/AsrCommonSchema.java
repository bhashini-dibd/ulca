package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.AsrlanguagesSpoken;
import io.swagger.model.AudioBitsPerSample;
import io.swagger.model.AudioChannel;
import io.swagger.model.AudioQualityEvaluation;
import io.swagger.model.CollectionMethodAudio;
import io.swagger.model.Gender;
import io.swagger.model.Source;
import io.swagger.model.SupportedLanguages;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AsrCommonSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-25T09:44:34.039Z[GMT]")


public class AsrCommonSchema   {
  @JsonProperty("audioFilename")
  private String audioFilename = null;

  @JsonProperty("imageFilename")
  private String imageFilename = null;

  @JsonProperty("speaker")
  private String speaker = null;

  @JsonProperty("gender")
  private Gender gender = null;

  @JsonProperty("exactAge")
  private BigDecimal exactAge = null;

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

  @JsonProperty("assertLanguage")
  private SupportedLanguages assertLanguage = null;

  @JsonProperty("languagesSpoken")
  private AsrlanguagesSpoken languagesSpoken = null;

  @JsonProperty("state")
  private String state = null;

  @JsonProperty("district")
  private String district = null;

  @JsonProperty("pinCode")
  private BigDecimal pinCode = null;

  @JsonProperty("stayYears")
  private BigDecimal stayYears = null;

  @JsonProperty("education")
  private String education = null;

  @JsonProperty("socioEconomic")
  private String socioEconomic = null;

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

  public AsrCommonSchema audioFilename(String audioFilename) {
    this.audioFilename = audioFilename;
    return this;
  }

  /**
   * filename of the audio file
   * @return audioFilename
   **/
  @Schema(description = "filename of the audio file")
  
    public String getAudioFilename() {
    return audioFilename;
  }

  public void setAudioFilename(String audioFilename) {
    this.audioFilename = audioFilename;
  }

  public AsrCommonSchema imageFilename(String imageFilename) {
    this.imageFilename = imageFilename;
    return this;
  }

  /**
   * filename of the image file described in audio
   * @return imageFilename
   **/
  @Schema(description = "filename of the image file described in audio")
  
    public String getImageFilename() {
    return imageFilename;
  }

  public void setImageFilename(String imageFilename) {
    this.imageFilename = imageFilename;
  }

  public AsrCommonSchema speaker(String speaker) {
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

  public AsrCommonSchema gender(Gender gender) {
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

  public AsrCommonSchema exactAge(BigDecimal exactAge) {
    this.exactAge = exactAge;
    return this;
  }

  /**
   * exact age of the speaker
   * @return exactAge
   **/
  @Schema(description = "exact age of the speaker")
  
    @Valid
    public BigDecimal getExactAge() {
    return exactAge;
  }

  public void setExactAge(BigDecimal exactAge) {
    this.exactAge = exactAge;
  }

  public AsrCommonSchema age(AgeEnum age) {
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

  public AsrCommonSchema assertLanguage(SupportedLanguages assertLanguage) {
    this.assertLanguage = assertLanguage;
    return this;
  }

  /**
   * Get assertLanguage
   * @return assertLanguage
   **/
  @Schema(description = "")
  
    @Valid
    public SupportedLanguages getAssertLanguage() {
    return assertLanguage;
  }

  public void setAssertLanguage(SupportedLanguages assertLanguage) {
    this.assertLanguage = assertLanguage;
  }

  public AsrCommonSchema languagesSpoken(AsrlanguagesSpoken languagesSpoken) {
    this.languagesSpoken = languagesSpoken;
    return this;
  }

  /**
   * Get languagesSpoken
   * @return languagesSpoken
   **/
  @Schema(description = "")
  
    @Valid
    public AsrlanguagesSpoken getLanguagesSpoken() {
    return languagesSpoken;
  }

  public void setLanguagesSpoken(AsrlanguagesSpoken languagesSpoken) {
    this.languagesSpoken = languagesSpoken;
  }

  public AsrCommonSchema state(String state) {
    this.state = state;
    return this;
  }

  /**
   * state in which the speaker belongs to
   * @return state
   **/
  @Schema(description = "state in which the speaker belongs to")
  
    public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public AsrCommonSchema district(String district) {
    this.district = district;
    return this;
  }

  /**
   * district where the recording of the audio was done
   * @return district
   **/
  @Schema(description = "district where the recording of the audio was done")
  
    public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public AsrCommonSchema pinCode(BigDecimal pinCode) {
    this.pinCode = pinCode;
    return this;
  }

  /**
   * 6 digit pincode of the recording location
   * @return pinCode
   **/
  @Schema(description = "6 digit pincode of the recording location")
  
    @Valid
    public BigDecimal getPinCode() {
    return pinCode;
  }

  public void setPinCode(BigDecimal pinCode) {
    this.pinCode = pinCode;
  }

  public AsrCommonSchema stayYears(BigDecimal stayYears) {
    this.stayYears = stayYears;
    return this;
  }

  /**
   * number of years the speaker claims to live in the district
   * @return stayYears
   **/
  @Schema(description = "number of years the speaker claims to live in the district")
  
    @Valid
    public BigDecimal getStayYears() {
    return stayYears;
  }

  public void setStayYears(BigDecimal stayYears) {
    this.stayYears = stayYears;
  }

  public AsrCommonSchema education(String education) {
    this.education = education;
    return this;
  }

  /**
   * level of eduction of the speaker
   * @return education
   **/
  @Schema(description = "level of eduction of the speaker")
  
    public String getEducation() {
    return education;
  }

  public void setEducation(String education) {
    this.education = education;
  }

  public AsrCommonSchema socioEconomic(String socioEconomic) {
    this.socioEconomic = socioEconomic;
    return this;
  }

  /**
   * Economic condition of the speaker
   * @return socioEconomic
   **/
  @Schema(description = "Economic condition of the speaker")
  
    public String getSocioEconomic() {
    return socioEconomic;
  }

  public void setSocioEconomic(String socioEconomic) {
    this.socioEconomic = socioEconomic;
  }

  public AsrCommonSchema duration(BigDecimal duration) {
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

  public AsrCommonSchema collectionSource(Source collectionSource) {
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

  public AsrCommonSchema channel(AudioChannel channel) {
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

  public AsrCommonSchema samplingRate(BigDecimal samplingRate) {
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

  public AsrCommonSchema bitsPerSample(AudioBitsPerSample bitsPerSample) {
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

  public AsrCommonSchema dialect(DialectEnum dialect) {
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

  public AsrCommonSchema snr(AudioQualityEvaluation snr) {
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

  public AsrCommonSchema startTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * Time of the audio from where the processing starts. Format : hh:mm:ss
   * @return startTime
   **/
  @Schema(description = "Time of the audio from where the processing starts. Format : hh:mm:ss")
  
    public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public AsrCommonSchema endTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

  /**
   * Time of the audio where the processing end. Format : hh:mm:ss
   * @return endTime
   **/
  @Schema(description = "Time of the audio where the processing end. Format : hh:mm:ss")
  
    public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public AsrCommonSchema collectionMethod(CollectionMethodAudio collectionMethod) {
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
    AsrCommonSchema asrCommonSchema = (AsrCommonSchema) o;
    return Objects.equals(this.audioFilename, asrCommonSchema.audioFilename) &&
        Objects.equals(this.imageFilename, asrCommonSchema.imageFilename) &&
        Objects.equals(this.speaker, asrCommonSchema.speaker) &&
        Objects.equals(this.gender, asrCommonSchema.gender) &&
        Objects.equals(this.exactAge, asrCommonSchema.exactAge) &&
        Objects.equals(this.age, asrCommonSchema.age) &&
        Objects.equals(this.assertLanguage, asrCommonSchema.assertLanguage) &&
        Objects.equals(this.languagesSpoken, asrCommonSchema.languagesSpoken) &&
        Objects.equals(this.state, asrCommonSchema.state) &&
        Objects.equals(this.district, asrCommonSchema.district) &&
        Objects.equals(this.pinCode, asrCommonSchema.pinCode) &&
        Objects.equals(this.stayYears, asrCommonSchema.stayYears) &&
        Objects.equals(this.education, asrCommonSchema.education) &&
        Objects.equals(this.socioEconomic, asrCommonSchema.socioEconomic) &&
        Objects.equals(this.duration, asrCommonSchema.duration) &&
        Objects.equals(this.collectionSource, asrCommonSchema.collectionSource) &&
        Objects.equals(this.channel, asrCommonSchema.channel) &&
        Objects.equals(this.samplingRate, asrCommonSchema.samplingRate) &&
        Objects.equals(this.bitsPerSample, asrCommonSchema.bitsPerSample) &&
        Objects.equals(this.dialect, asrCommonSchema.dialect) &&
        Objects.equals(this.snr, asrCommonSchema.snr) &&
        Objects.equals(this.startTime, asrCommonSchema.startTime) &&
        Objects.equals(this.endTime, asrCommonSchema.endTime) &&
        Objects.equals(this.collectionMethod, asrCommonSchema.collectionMethod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(audioFilename, imageFilename, speaker, gender, exactAge, age, assertLanguage, languagesSpoken, state, district, pinCode, stayYears, education, socioEconomic, duration, collectionSource, channel, samplingRate, bitsPerSample, dialect, snr, startTime, endTime, collectionMethod);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AsrCommonSchema {\n");
    
    sb.append("    audioFilename: ").append(toIndentedString(audioFilename)).append("\n");
    sb.append("    imageFilename: ").append(toIndentedString(imageFilename)).append("\n");
    sb.append("    speaker: ").append(toIndentedString(speaker)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    exactAge: ").append(toIndentedString(exactAge)).append("\n");
    sb.append("    age: ").append(toIndentedString(age)).append("\n");
    sb.append("    assertLanguage: ").append(toIndentedString(assertLanguage)).append("\n");
    sb.append("    languagesSpoken: ").append(toIndentedString(languagesSpoken)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    district: ").append(toIndentedString(district)).append("\n");
    sb.append("    pinCode: ").append(toIndentedString(pinCode)).append("\n");
    sb.append("    stayYears: ").append(toIndentedString(stayYears)).append("\n");
    sb.append("    education: ").append(toIndentedString(education)).append("\n");
    sb.append("    socioEconomic: ").append(toIndentedString(socioEconomic)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    collectionSource: ").append(toIndentedString(collectionSource)).append("\n");
    sb.append("    channel: ").append(toIndentedString(channel)).append("\n");
    sb.append("    samplingRate: ").append(toIndentedString(samplingRate)).append("\n");
    sb.append("    bitsPerSample: ").append(toIndentedString(bitsPerSample)).append("\n");
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
