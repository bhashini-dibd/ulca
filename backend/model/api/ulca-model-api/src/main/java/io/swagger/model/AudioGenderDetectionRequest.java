package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AudioFiles;
import io.swagger.model.AudioGenderDetectionConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AudioGenderDetectionRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-06-13T12:41:05.989770988Z[GMT]")


public class AudioGenderDetectionRequest   {
  @JsonProperty("audio")
  private AudioFiles audio = null;

  @JsonProperty("config")
  private AudioGenderDetectionConfig config = null;

  public AudioGenderDetectionRequest audio(AudioFiles audio) {
    this.audio = audio;
    return this;
  }

  /**
   * Get audio
   * @return audio
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public AudioFiles getAudio() {
    return audio;
  }

  public void setAudio(AudioFiles audio) {
    this.audio = audio;
  }

  public AudioGenderDetectionRequest config(AudioGenderDetectionConfig config) {
    this.config = config;
    return this;
  }

  /**
   * Get config
   * @return config
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public AudioGenderDetectionConfig getConfig() {
    return config;
  }

  public void setConfig(AudioGenderDetectionConfig config) {
    this.config = config;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AudioGenderDetectionRequest audioGenderDetectionRequest = (AudioGenderDetectionRequest) o;
    return Objects.equals(this.audio, audioGenderDetectionRequest.audio) &&
        Objects.equals(this.config, audioGenderDetectionRequest.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(audio, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AudioGenderDetectionRequest {\n");
    
    sb.append("    audio: ").append(toIndentedString(audio)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
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
