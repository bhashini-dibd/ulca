package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AudioFile;
import io.swagger.model.AudioGenderPredictions;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AudioGenderPrediction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-06-13T12:41:05.989770988Z[GMT]")


public class AudioGenderPrediction   {
  @JsonProperty("audio")
  private AudioFile audio = null;

  @JsonProperty("genderPrediction")
  private AudioGenderPredictions genderPrediction = null;

  public AudioGenderPrediction audio(AudioFile audio) {
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
    public AudioFile getAudio() {
    return audio;
  }

  public void setAudio(AudioFile audio) {
    this.audio = audio;
  }

  public AudioGenderPrediction genderPrediction(AudioGenderPredictions genderPrediction) {
    this.genderPrediction = genderPrediction;
    return this;
  }

  /**
   * Get genderPrediction
   * @return genderPrediction
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public AudioGenderPredictions getGenderPrediction() {
    return genderPrediction;
  }

  public void setGenderPrediction(AudioGenderPredictions genderPrediction) {
    this.genderPrediction = genderPrediction;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AudioGenderPrediction audioGenderPrediction = (AudioGenderPrediction) o;
    return Objects.equals(this.audio, audioGenderPrediction.audio) &&
        Objects.equals(this.genderPrediction, audioGenderPrediction.genderPrediction);
  }

  @Override
  public int hashCode() {
    return Objects.hash(audio, genderPrediction);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AudioGenderPrediction {\n");
    
    sb.append("    audio: ").append(toIndentedString(audio)).append("\n");
    sb.append("    genderPrediction: ").append(toIndentedString(genderPrediction)).append("\n");
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
