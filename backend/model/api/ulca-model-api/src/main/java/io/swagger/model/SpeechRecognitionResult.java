package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Alternatives;
import io.swagger.model.RecognitionDetail;
import io.swagger.model.RecognitionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SpeechRecognitionResult
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:57:01.789Z[GMT]")


public class SpeechRecognitionResult   {
  @JsonProperty("transcript")
  private String transcript = null;

  @JsonProperty("alternatives")
  private Alternatives alternatives = null;

  @JsonProperty("srt")
  private String srt = null;

  @JsonProperty("status")
  private RecognitionStatus status = null;

  @JsonProperty("detail")
  private RecognitionDetail detail = null;

  public SpeechRecognitionResult transcript(String transcript) {
    this.transcript = transcript;
    return this;
  }

  /**
   * Get transcript
   * @return transcript
   **/
  @Schema(example = "This is text", description = "")
  
    public String getTranscript() {
    return transcript;
  }

  public void setTranscript(String transcript) {
    this.transcript = transcript;
  }

  public SpeechRecognitionResult alternatives(Alternatives alternatives) {
    this.alternatives = alternatives;
    return this;
  }

  /**
   * Get alternatives
   * @return alternatives
   **/
  @Schema(description = "")
  
    @Valid
    public Alternatives getAlternatives() {
    return alternatives;
  }

  public void setAlternatives(Alternatives alternatives) {
    this.alternatives = alternatives;
  }

  public SpeechRecognitionResult srt(String srt) {
    this.srt = srt;
    return this;
  }

  /**
   * Get srt
   * @return srt
   **/
  @Schema(example = "--> This is srt text", description = "")
  
    public String getSrt() {
    return srt;
  }

  public void setSrt(String srt) {
    this.srt = srt;
  }

  public SpeechRecognitionResult status(RecognitionStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public RecognitionStatus getStatus() {
    return status;
  }

  public void setStatus(RecognitionStatus status) {
    this.status = status;
  }

  public SpeechRecognitionResult detail(RecognitionDetail detail) {
    this.detail = detail;
    return this;
  }

  /**
   * Get detail
   * @return detail
   **/
  @Schema(description = "")
  
    @Valid
    public RecognitionDetail getDetail() {
    return detail;
  }

  public void setDetail(RecognitionDetail detail) {
    this.detail = detail;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SpeechRecognitionResult speechRecognitionResult = (SpeechRecognitionResult) o;
    return Objects.equals(this.transcript, speechRecognitionResult.transcript) &&
        Objects.equals(this.alternatives, speechRecognitionResult.alternatives) &&
        Objects.equals(this.srt, speechRecognitionResult.srt) &&
        Objects.equals(this.status, speechRecognitionResult.status) &&
        Objects.equals(this.detail, speechRecognitionResult.detail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transcript, alternatives, srt, status, detail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SpeechRecognitionResult {\n");
    
    sb.append("    transcript: ").append(toIndentedString(transcript)).append("\n");
    sb.append("    alternatives: ").append(toIndentedString(alternatives)).append("\n");
    sb.append("    srt: ").append(toIndentedString(srt)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
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
