package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * RecognitionAudio
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:57:01.789Z[GMT]")


public class RecognitionAudio   {
  @JsonProperty("audioContent")
  private byte[] audioContent = null;

  @JsonProperty("audioUri")
  private String audioUri = null;

  @JsonProperty("fileId")
  private String fileId = null;

  public RecognitionAudio audioContent(byte[] audioContent) {
    this.audioContent = audioContent;
    return this;
  }

  /**
   * audio content with audio duration <= 1min
   * @return audioContent
   **/
  @Schema(example = "[B@4b63c5c6", description = "audio content with audio duration <= 1min")
  
    public byte[] getAudioContent() {
    return audioContent;
  }

  public void setAudioContent(byte[] audioContent) {
    this.audioContent = audioContent;
  }

  public RecognitionAudio audioUri(String audioUri) {
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

  public RecognitionAudio fileId(String fileId) {
    this.fileId = fileId;
    return this;
  }

  /**
   * fileId should be same as returned in the response of file-upload
   * @return fileId
   **/
  @Schema(description = "fileId should be same as returned in the response of file-upload")
  
    public String getFileId() {
    return fileId;
  }

  public void setFileId(String fileId) {
    this.fileId = fileId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecognitionAudio recognitionAudio = (RecognitionAudio) o;
    return Objects.equals(this.audioContent, recognitionAudio.audioContent) &&
        Objects.equals(this.audioUri, recognitionAudio.audioUri) &&
        Objects.equals(this.fileId, recognitionAudio.fileId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(audioContent, audioUri, fileId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecognitionAudio {\n");
    
    sb.append("    audioContent: ").append(toIndentedString(audioContent)).append("\n");
    sb.append("    audioUri: ").append(toIndentedString(audioUri)).append("\n");
    sb.append("    fileId: ").append(toIndentedString(fileId)).append("\n");
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
