package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * formatting type used in the transcription text
 */
@Schema(description = "formatting type used in the transcription text")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:41:06.560Z[GMT]")


public class TranscriptFormat   {
  /**
   * standard or popular format used for specifying the transcription text
   */
  public enum FormatTypeEnum {
    TRANSCRIPTTEXTFORMATTYPE1("TranscriptTextFormatType1");

    private String value;

    FormatTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static FormatTypeEnum fromValue(String text) {
      for (FormatTypeEnum b : FormatTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("formatType")
  private FormatTypeEnum formatType = null;

  @JsonProperty("formatDetails")
  private OneOfTranscriptFormatFormatDetails formatDetails = null;

  public TranscriptFormat formatType(FormatTypeEnum formatType) {
    this.formatType = formatType;
    return this;
  }

  /**
   * standard or popular format used for specifying the transcription text
   * @return formatType
   **/
  @Schema(description = "standard or popular format used for specifying the transcription text")
  
    public FormatTypeEnum getFormatType() {
    return formatType;
  }

  public void setFormatType(FormatTypeEnum formatType) {
    this.formatType = formatType;
  }

  public TranscriptFormat formatDetails(OneOfTranscriptFormatFormatDetails formatDetails) {
    this.formatDetails = formatDetails;
    return this;
  }

  /**
   * Get formatDetails
   * @return formatDetails
   **/
  @Schema(description = "")
  
    public OneOfTranscriptFormatFormatDetails getFormatDetails() {
    return formatDetails;
  }

  public void setFormatDetails(OneOfTranscriptFormatFormatDetails formatDetails) {
    this.formatDetails = formatDetails;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TranscriptFormat transcriptFormat = (TranscriptFormat) o;
    return Objects.equals(this.formatType, transcriptFormat.formatType) &&
        Objects.equals(this.formatDetails, transcriptFormat.formatDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(formatType, formatDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TranscriptFormat {\n");
    
    sb.append("    formatType: ").append(toIndentedString(formatType)).append("\n");
    sb.append("    formatDetails: ").append(toIndentedString(formatDetails)).append("\n");
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
