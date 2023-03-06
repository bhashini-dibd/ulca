package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AudioFormat;
import io.swagger.model.Gender;
import io.swagger.model.LanguagePair;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TTSConfig
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T08:00:21.046011704Z[GMT]")


public class TTSConfig   {
  @JsonProperty("modelId")
  private String modelId = null;

  @JsonProperty("language")
  private LanguagePair language = null;

  @JsonProperty("gender")
  private Gender gender = null;

  @JsonProperty("inputFormat")
  private AudioConfigTranscriptionFormat inputFormat = null;

  @JsonProperty("audioFormat")
  private AudioFormat audioFormat = null;

  public TTSConfig modelId(String modelId) {
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

  public TTSConfig language(LanguagePair language) {
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

  public TTSConfig gender(Gender gender) {
    this.gender = gender;
    return this;
  }

  /**
   * Get gender
   * @return gender
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public TTSConfig inputFormat(AudioConfigTranscriptionFormat inputFormat) {
    this.inputFormat = inputFormat;
    return this;
  }

  /**
   * Get inputFormat
   * @return inputFormat
   **/
  @Schema(description = "")
  
    @Valid
    public AudioConfigTranscriptionFormat getInputFormat() {
    return inputFormat;
  }

  public void setInputFormat(AudioConfigTranscriptionFormat inputFormat) {
    this.inputFormat = inputFormat;
  }

  public TTSConfig audioFormat(AudioFormat audioFormat) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TTSConfig ttSConfig = (TTSConfig) o;
    return Objects.equals(this.modelId, ttSConfig.modelId) &&
        Objects.equals(this.language, ttSConfig.language) &&
        Objects.equals(this.gender, ttSConfig.gender) &&
        Objects.equals(this.inputFormat, ttSConfig.inputFormat) &&
        Objects.equals(this.audioFormat, ttSConfig.audioFormat);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modelId, language, gender, inputFormat, audioFormat);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TTSConfig {\n");
    
    sb.append("    modelId: ").append(toIndentedString(modelId)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    inputFormat: ").append(toIndentedString(inputFormat)).append("\n");
    sb.append("    audioFormat: ").append(toIndentedString(audioFormat)).append("\n");
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
