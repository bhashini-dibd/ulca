package io.swagger.pipelinemodel;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.SupportedLanguages;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * LanguageSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T06:06:14.793576134Z[GMT]")


public class LanguageSchema   {
  @JsonProperty("sourceLanguage")
  private SupportedLanguages sourceLanguage = null;

  @JsonProperty("targetLanguage")
  private SupportedLanguages targetLanguage = null;

  public LanguageSchema sourceLanguage(SupportedLanguages sourceLanguage) {
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

  public LanguageSchema targetLanguage(SupportedLanguages targetLanguage) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LanguageSchema languageSchema = (LanguageSchema) o;
    return Objects.equals(this.sourceLanguage, languageSchema.sourceLanguage) &&
        Objects.equals(this.targetLanguage, languageSchema.targetLanguage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sourceLanguage, targetLanguage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LanguageSchema {\n");
    
    sb.append("    sourceLanguage: ").append(toIndentedString(sourceLanguage)).append("\n");
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
