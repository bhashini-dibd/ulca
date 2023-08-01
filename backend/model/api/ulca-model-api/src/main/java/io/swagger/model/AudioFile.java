package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AudioFile
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T03:55:25.562740452Z[GMT]")


public class AudioFile   {
  @JsonProperty("audioContent")
  private byte[] audioContent = null;

  @JsonProperty("audioUri")
  private String audioUri = null;

  public AudioFile audioContent(byte[] audioContent) {
    this.audioContent = audioContent;
    return this;
  }

  /**
   * audio content with audio duration <= 1min
   * @return audioContent
   **/
  @Schema(description = "audio content with audio duration <= 1min")
  
    public byte[] getAudioContent() {
    return audioContent;
  }

  public void setAudioContent(byte[] audioContent) {
    this.audioContent = audioContent;
  }

  public AudioFile audioUri(String audioUri) {
    this.audioUri = audioUri;
    return this;
  }

  /**
   * path on gcp/s3 bucket or https url
   * @return audioUri
   **/
  @Schema(example = "gs://bucket/audio.wav", description = "path on gcp/s3 bucket or https url")
  
    public String getAudioUri() {
    return audioUri;
  }

  public void setAudioUri(String audioUri) {
    this.audioUri = audioUri;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AudioFile audioFile = (AudioFile) o;
    return Objects.equals(this.audioContent, audioFile.audioContent) &&
        Objects.equals(this.audioUri, audioFile.audioUri);
  }

  @Override
  public int hashCode() {
    return Objects.hash(audioContent, audioUri);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AudioFile {\n");
    
    sb.append("    audioContent: ").append(toIndentedString(audioContent)).append("\n");
    sb.append("    audioUri: ").append(toIndentedString(audioUri)).append("\n");
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
