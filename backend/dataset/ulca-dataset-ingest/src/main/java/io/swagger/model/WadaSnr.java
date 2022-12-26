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
 * evaluation strategy as proposed by example researcher.
 */
@Schema(description = "evaluation strategy as proposed by example researcher.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:41:06.560Z[GMT]")


public class WadaSnr  implements OneOfAudioQualityEvaluationMethodDetails {
  @JsonProperty("snr")
  private BigDecimal snr = null;

  @JsonProperty("snrTool")
  private String snrTool = null;

  @JsonProperty("snrToolVersion")
  private String snrToolVersion = null;

  public WadaSnr snr(BigDecimal snr) {
    this.snr = snr;
    return this;
  }

  /**
   * signal to noise ratio of the audio
   * @return snr
   **/
  @Schema(description = "signal to noise ratio of the audio")
  
    @Valid
    public BigDecimal getSnr() {
    return snr;
  }

  public void setSnr(BigDecimal snr) {
    this.snr = snr;
  }

  public WadaSnr snrTool(String snrTool) {
    this.snrTool = snrTool;
    return this;
  }

  /**
   * snr evaluation tool
   * @return snrTool
   **/
  @Schema(description = "snr evaluation tool")
  
    public String getSnrTool() {
    return snrTool;
  }

  public void setSnrTool(String snrTool) {
    this.snrTool = snrTool;
  }

  public WadaSnr snrToolVersion(String snrToolVersion) {
    this.snrToolVersion = snrToolVersion;
    return this;
  }

  /**
   * snr evaluation tool version
   * @return snrToolVersion
   **/
  @Schema(description = "snr evaluation tool version")
  
    public String getSnrToolVersion() {
    return snrToolVersion;
  }

  public void setSnrToolVersion(String snrToolVersion) {
    this.snrToolVersion = snrToolVersion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WadaSnr wadaSnr = (WadaSnr) o;
    return Objects.equals(this.snr, wadaSnr.snr) &&
        Objects.equals(this.snrTool, wadaSnr.snrTool) &&
        Objects.equals(this.snrToolVersion, wadaSnr.snrToolVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(snr, snrTool, snrToolVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WadaSnr {\n");
    
    sb.append("    snr: ").append(toIndentedString(snr)).append("\n");
    sb.append("    snrTool: ").append(toIndentedString(snrTool)).append("\n");
    sb.append("    snrToolVersion: ").append(toIndentedString(snrToolVersion)).append("\n");
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
