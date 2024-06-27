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
 * LangDetectionPrediction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T03:55:25.562740452Z[GMT]")


public class LangDetectionPrediction   {
  @JsonProperty("langCode")
  private String langCode = null;

  @JsonProperty("scriptCode")
  private String scriptCode = null;

  @JsonProperty("langScore")
  private BigDecimal langScore = null;

  public LangDetectionPrediction langCode(String langCode) {
    this.langCode = langCode;
    return this;
  }

  /**
   * Indic language code, iso-639-1, iso 639-2
   * @return langCode
   **/
  @Schema(required = true, description = "Indic language code, iso-639-1, iso 639-2")
      @NotNull

    public String getLangCode() {
    return langCode;
  }

  public void setLangCode(String langCode) {
    this.langCode = langCode;
  }

  public LangDetectionPrediction scriptCode(String scriptCode) {
    this.scriptCode = scriptCode;
    return this;
  }

  /**
   * Script code, iso-15924
   * @return scriptCode
   **/
  @Schema(description = "Script code, iso-15924")
  
    public String getScriptCode() {
    return scriptCode;
  }

  public void setScriptCode(String scriptCode) {
    this.scriptCode = scriptCode;
  }

  public LangDetectionPrediction langScore(BigDecimal langScore) {
    this.langScore = langScore;
    return this;
  }

  /**
   * the measure of accuracy of language prediction
   * @return langScore
   **/
  @Schema(description = "the measure of accuracy of language prediction")
  
    @Valid
    public BigDecimal getLangScore() {
    return langScore;
  }

  public void setLangScore(BigDecimal langScore) {
    this.langScore = langScore;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LangDetectionPrediction langDetectionPrediction = (LangDetectionPrediction) o;
    return Objects.equals(this.langCode, langDetectionPrediction.langCode) &&
        Objects.equals(this.scriptCode, langDetectionPrediction.scriptCode) &&
        Objects.equals(this.langScore, langDetectionPrediction.langScore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(langCode, scriptCode, langScore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LangDetectionPrediction {\n");
    
    sb.append("    langCode: ").append(toIndentedString(langCode)).append("\n");
    sb.append("    scriptCode: ").append(toIndentedString(scriptCode)).append("\n");
    sb.append("    langScore: ").append(toIndentedString(langScore)).append("\n");
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
