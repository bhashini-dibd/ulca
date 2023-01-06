package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AudioConfig;
import io.swagger.model.AudioFiles;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ASRRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:33:39.764Z[GMT]")


public class ASRRequest   {
  @JsonProperty("audio")
  private AudioFiles audio = null;

  @JsonProperty("config")
  private AudioConfig config = null;

  public ASRRequest audio(AudioFiles audio) {
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

  public ASRRequest config(AudioConfig config) {
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
    public AudioConfig getConfig() {
    return config;
  }

  public void setConfig(AudioConfig config) {
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
    ASRRequest asRRequest = (ASRRequest) o;
    return Objects.equals(this.audio, asRRequest.audio) &&
        Objects.equals(this.config, asRRequest.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(audio, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ASRRequest {\n");
    
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
