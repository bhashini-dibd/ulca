package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.model.SupportedLanguages;
import io.swagger.model.LanguagePair;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * LanguageSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T08:00:21.046011704Z[GMT]")



public class LanguageSchema   {
  @JsonProperty("sourceLanguage")
  private LanguagePair sourceLanguage = null;

  //sourceScriptCode

  @JsonProperty("targetLanguageList")
  @Valid
  private List<LanguagePair> targetLanguageList = null;
  //List of Language Pairs

  public LanguageSchema sourceLanguage(LanguagePair sourceLanguage) {
    this.sourceLanguage = sourceLanguage;
    return this;
  }

  /**
   * Get sourceLanguage
   * @return sourceLanguage
   **/
  @Schema(description = "")
  
    @Valid
    public LanguagePair getSourceLanguage() {
    return sourceLanguage;
  }

  public void setSourceLanguage(LanguagePair sourceLanguage) {
    this.sourceLanguage = sourceLanguage;
  }

  public LanguageSchema targetLanguageList(List<LanguagePair> targetLanguageList) {
    this.targetLanguageList = targetLanguageList;
    return this;
  }

  public LanguageSchema addTargetLanguageListItem(LanguagePair targetLanguageListItem) {
    if (this.targetLanguageList == null) {
      this.targetLanguageList = new ArrayList<LanguagePair>();
    }
    this.targetLanguageList.add(targetLanguageListItem);
    return this;
  }

  /**
   * list of
   * @return targetLanguageList
   **/
  @Schema(description = "list of")
      @Valid
    public List<LanguagePair> getTargetLanguageList() {
    return targetLanguageList;
  }

  public void setTargetLanguageList(List<LanguagePair> targetLanguageList) {
    this.targetLanguageList = targetLanguageList;
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
        Objects.equals(this.targetLanguageList, languageSchema.targetLanguageList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sourceLanguage, targetLanguageList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LanguageSchema {\n");
    
    sb.append("    sourceLanguage: ").append(toIndentedString(sourceLanguage)).append("\n");
    sb.append("    targetLanguageList: ").append(toIndentedString(targetLanguageList)).append("\n");
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
