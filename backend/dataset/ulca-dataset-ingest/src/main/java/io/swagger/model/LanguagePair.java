package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.SupportedLanguages;
import io.swagger.model.SupportedScripts;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * language pair, make targetLanguage null to reuse the object to indicate single language
 */
@Schema(description = "language pair, make targetLanguage null to reuse the object to indicate single language")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-20T09:24:02.077792125Z[GMT]")


public class LanguagePair  implements OneOfSearchParamsParams {
  @JsonProperty("sourceLanguageName")
  private String sourceLanguageName = null;

  @JsonProperty("sourceLanguage")
  private SupportedLanguages sourceLanguage = null;

  @JsonProperty("sourceScriptCode")
  private SupportedScripts sourceScriptCode = null;

  @JsonProperty("targetLanguageName")
  private String targetLanguageName = null;

  @JsonProperty("targetLanguage")
  private SupportedLanguages targetLanguage = null;

  @JsonProperty("targetScriptCode")
  private SupportedScripts targetScriptCode = null;

  public LanguagePair sourceLanguageName(String sourceLanguageName) {
    this.sourceLanguageName = sourceLanguageName;
    return this;
  }

  /**
   * human name associated with the language code
   * @return sourceLanguageName
   **/
  @Schema(description = "human name associated with the language code")
  
    public String getSourceLanguageName() {
    return sourceLanguageName;
  }

  public void setSourceLanguageName(String sourceLanguageName) {
    this.sourceLanguageName = sourceLanguageName;
  }

  public LanguagePair sourceLanguage(SupportedLanguages sourceLanguage) {
    this.sourceLanguage = sourceLanguage;
    return this;
  }

  /**
   * Get sourceLanguage
   * @return sourceLanguage
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public SupportedLanguages getSourceLanguage() {
    return sourceLanguage;
  }

  public void setSourceLanguage(SupportedLanguages sourceLanguage) {
    this.sourceLanguage = sourceLanguage;
  }

  public LanguagePair sourceScriptCode(SupportedScripts sourceScriptCode) {
    this.sourceScriptCode = sourceScriptCode;
    return this;
  }

  /**
   * Get sourceScriptCode
   * @return sourceScriptCode
   **/
  @Schema(description = "")
  
    @Valid
    public SupportedScripts getSourceScriptCode() {
    return sourceScriptCode;
  }

  public void setSourceScriptCode(SupportedScripts sourceScriptCode) {
    this.sourceScriptCode = sourceScriptCode;
  }

  public LanguagePair targetLanguageName(String targetLanguageName) {
    this.targetLanguageName = targetLanguageName;
    return this;
  }

  /**
   * human name associated with the language code
   * @return targetLanguageName
   **/
  @Schema(description = "human name associated with the language code")
  
    public String getTargetLanguageName() {
    return targetLanguageName;
  }

  public void setTargetLanguageName(String targetLanguageName) {
    this.targetLanguageName = targetLanguageName;
  }

  public LanguagePair targetLanguage(SupportedLanguages targetLanguage) {
    this.targetLanguage = targetLanguage;
    return this;
  }

  /**
   * Get targetLanguage
   * @return targetLanguage
   **/
  @Schema(description = "")
  
    @Valid
    public SupportedLanguages getTargetLanguage() {
    return targetLanguage;
  }

  public void setTargetLanguage(SupportedLanguages targetLanguage) {
    this.targetLanguage = targetLanguage;
  }

  public LanguagePair targetScriptCode(SupportedScripts targetScriptCode) {
    this.targetScriptCode = targetScriptCode;
    return this;
  }

  /**
   * Get targetScriptCode
   * @return targetScriptCode
   **/
  @Schema(description = "")
  
    @Valid
    public SupportedScripts getTargetScriptCode() {
    return targetScriptCode;
  }

  public void setTargetScriptCode(SupportedScripts targetScriptCode) {
    this.targetScriptCode = targetScriptCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LanguagePair languagePair = (LanguagePair) o;
    return Objects.equals(this.sourceLanguageName, languagePair.sourceLanguageName) &&
        Objects.equals(this.sourceLanguage, languagePair.sourceLanguage) &&
        Objects.equals(this.sourceScriptCode, languagePair.sourceScriptCode) &&
        Objects.equals(this.targetLanguageName, languagePair.targetLanguageName) &&
        Objects.equals(this.targetLanguage, languagePair.targetLanguage) &&
        Objects.equals(this.targetScriptCode, languagePair.targetScriptCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sourceLanguageName, sourceLanguage, sourceScriptCode, targetLanguageName, targetLanguage, targetScriptCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LanguagePair {\n");
    
    sb.append("    sourceLanguageName: ").append(toIndentedString(sourceLanguageName)).append("\n");
    sb.append("    sourceLanguage: ").append(toIndentedString(sourceLanguage)).append("\n");
    sb.append("    sourceScriptCode: ").append(toIndentedString(sourceScriptCode)).append("\n");
    sb.append("    targetLanguageName: ").append(toIndentedString(targetLanguageName)).append("\n");
    sb.append("    targetLanguage: ").append(toIndentedString(targetLanguage)).append("\n");
    sb.append("    targetScriptCode: ").append(toIndentedString(targetScriptCode)).append("\n");
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
