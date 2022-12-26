package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.RecognitionAudio;
import io.swagger.model.RecognitionConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SpeechRecognitionRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:57:01.789Z[GMT]")


public class SpeechRecognitionRequest  implements OneOfInferenceAPIEndPointSchema {
  @JsonProperty("config")
  private RecognitionConfig config = null;

  @JsonProperty("audio")
  private RecognitionAudio audio = null;

  public SpeechRecognitionRequest config(RecognitionConfig config) {
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
    public RecognitionConfig getConfig() {
    return config;
  }

  public void setConfig(RecognitionConfig config) {
    this.config = config;
  }

  public SpeechRecognitionRequest audio(RecognitionAudio audio) {
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
    public RecognitionAudio getAudio() {
    return audio;
  }

  public void setAudio(RecognitionAudio audio) {
    this.audio = audio;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SpeechRecognitionRequest speechRecognitionRequest = (SpeechRecognitionRequest) o;
    return Objects.equals(this.config, speechRecognitionRequest.config) &&
        Objects.equals(this.audio, speechRecognitionRequest.audio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(config, audio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SpeechRecognitionRequest {\n");
    
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
    sb.append("    audio: ").append(toIndentedString(audio)).append("\n");
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
