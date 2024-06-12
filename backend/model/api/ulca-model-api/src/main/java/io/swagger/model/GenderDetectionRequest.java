package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AudioFiles;
import io.swagger.model.GenderDetectionConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * GenderDetectionRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-06-12T11:10:30.623150359Z[GMT]")


public class GenderDetectionRequest   {
  @JsonProperty("audio")
  private AudioFiles audio = null;

  @JsonProperty("config")
  private GenderDetectionConfig config = null;

  public GenderDetectionRequest audio(AudioFiles audio) {
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

  public GenderDetectionRequest config(GenderDetectionConfig config) {
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
    public GenderDetectionConfig getConfig() {
    return config;
  }

  public void setConfig(GenderDetectionConfig config) {
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
    GenderDetectionRequest genderDetectionRequest = (GenderDetectionRequest) o;
    return Objects.equals(this.audio, genderDetectionRequest.audio) &&
        Objects.equals(this.config, genderDetectionRequest.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(audio, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GenderDetectionRequest {\n");
    
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
