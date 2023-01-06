package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * transcription evalution method description
 */
@Schema(description = "transcription evalution method description")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:41:06.560Z[GMT]")


public class TranscriptionEvaluationMethod1  implements OneOfCollectionDetailsMachineGeneratedTranscriptEvaluationMethod {
  @JsonProperty("wer")
  private BigDecimal wer = null;

  public TranscriptionEvaluationMethod1 wer(BigDecimal wer) {
    this.wer = wer;
    return this;
  }

  /**
   * model generated vs human transcribed output comp
   * @return wer
   **/
  @Schema(description = "model generated vs human transcribed output comp")
  
    @Valid
    public BigDecimal getWer() {
    return wer;
  }

  public void setWer(BigDecimal wer) {
    this.wer = wer;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TranscriptionEvaluationMethod1 transcriptionEvaluationMethod1 = (TranscriptionEvaluationMethod1) o;
    return Objects.equals(this.wer, transcriptionEvaluationMethod1.wer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(wer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TranscriptionEvaluationMethod1 {\n");
    
    sb.append("    wer: ").append(toIndentedString(wer)).append("\n");
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
