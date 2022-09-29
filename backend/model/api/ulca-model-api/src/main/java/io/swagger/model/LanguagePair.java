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
 * language pair, make targetLanguage null to reuse the object to indicate single language
 */
@Schema(description = "language pair, make targetLanguage null to reuse the object to indicate single language")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-07-18T10:57:45.372Z[GMT]")


public class LanguagePair {
  @JsonProperty("sourceLanguageName")
  private String sourceLanguageName = null;

  /**
   * Indic language code, iso-639-1, iso 639-2
   */
  public enum SourceLanguageEnum {
    EN("en"),
    
    HI("hi"),
    
    MR("mr"),
    
    TA("ta"),
    
    TE("te"),
    
    KN("kn"),
    
    GU("gu"),
    
    PA("pa"),
    
    BN("bn"),
    
    ML("ml"),
    
    AS("as"),
    
    BRX("brx"),
    
    DOI("doi"),
    
    KS("ks"),
    
    KOK("kok"),
    
    MAI("mai"),
    
    MNI("mni"),
    
    NE("ne"),
    
    OR("or"),
    
    SD("sd"),
    
    SI("si"),
    
    UR("ur"),
    
    SAT("sat"),
    
    LUS("lus"),
    
    NJZ("njz"),
    
    PNR("pnr"),
    
    KHA("kha"),
    
    GRT("grt"),
    
    SA("sa"),
    
    RAJ("raj"),
    
    BHO("bho"),
    
    GOM("gom"),
    
    AWA("awa"),
    
    HNE("hne"),
    
    MAG("mag"),
    
    MULTI("multi");

    private String value;

    SourceLanguageEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static SourceLanguageEnum fromValue(String text) {
      for (SourceLanguageEnum b : SourceLanguageEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("sourceLanguage")
  private SourceLanguageEnum sourceLanguage = null;

  @JsonProperty("targetLanguageName")
  private String targetLanguageName = null;

  /**
   * Indic language code, iso-639-1, iso 639-2
   */
  public enum TargetLanguageEnum {
    EN("en"),
    
    HI("hi"),
    
    MR("mr"),
    
    TA("ta"),
    
    TE("te"),
    
    KN("kn"),
    
    GU("gu"),
    
    PA("pa"),
    
    BN("bn"),
    
    ML("ml"),
    
    AS("as"),
    
    BRX("brx"),
    
    DOI("doi"),
    
    KS("ks"),
    
    KOK("kok"),
    
    MAI("mai"),
    
    MNI("mni"),
    
    NE("ne"),
    
    OR("or"),
    
    SD("sd"),
    
    SI("si"),
    
    UR("ur"),
    
    SAT("sat"),
    
    LUS("lus"),
    
    NJZ("njz"),
    
    PNR("pnr"),
    
    KHA("kha"),
    
    GRT("grt"),
    
    SA("sa"),
    
    RAJ("raj"),
    
    BHO("bho"),
    
    GOM("gom"),
    
    AWA("awa"),
    
    HNE("hne"),
    
    MAG("mag"),
    
    MULTI("multi");

    private String value;

    TargetLanguageEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TargetLanguageEnum fromValue(String text) {
      for (TargetLanguageEnum b : TargetLanguageEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("targetLanguage")
  private TargetLanguageEnum targetLanguage = null;

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

  public LanguagePair sourceLanguage(SourceLanguageEnum sourceLanguage) {
    this.sourceLanguage = sourceLanguage;
    return this;
  }

  /**
   * Indic language code, iso-639-1, iso 639-2
   * @return sourceLanguage
   **/
  @Schema(required = true, description = "Indic language code, iso-639-1, iso 639-2")
      @NotNull

    public SourceLanguageEnum getSourceLanguage() {
    return sourceLanguage;
  }

  public void setSourceLanguage(SourceLanguageEnum sourceLanguage) {
    this.sourceLanguage = sourceLanguage;
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

  public LanguagePair targetLanguage(TargetLanguageEnum targetLanguage) {
    this.targetLanguage = targetLanguage;
    return this;
  }

  /**
   * Indic language code, iso-639-1, iso 639-2
   * @return targetLanguage
   **/
  @Schema(description = "Indic language code, iso-639-1, iso 639-2")
  
    public TargetLanguageEnum getTargetLanguage() {
    return targetLanguage;
  }

  public void setTargetLanguage(TargetLanguageEnum targetLanguage) {
    this.targetLanguage = targetLanguage;
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
        Objects.equals(this.targetLanguageName, languagePair.targetLanguageName) &&
        Objects.equals(this.targetLanguage, languagePair.targetLanguage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sourceLanguageName, sourceLanguage, targetLanguageName, targetLanguage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LanguagePair {\n");
    
    sb.append("    sourceLanguageName: ").append(toIndentedString(sourceLanguageName)).append("\n");
    sb.append("    sourceLanguage: ").append(toIndentedString(sourceLanguage)).append("\n");
    sb.append("    targetLanguageName: ").append(toIndentedString(targetLanguageName)).append("\n");
    sb.append("    targetLanguage: ").append(toIndentedString(targetLanguage)).append("\n");
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
