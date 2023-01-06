package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.LanguagePair;
import io.swagger.model.Sentences;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TranslateRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:57:01.789Z[GMT]")


public class TranslateRequest  implements OneOfInferenceAPIEndPointSchema {
  @JsonProperty("languages")
  private LanguagePair languages = null;

  @JsonProperty("sentences")
  private Sentences sentences = null;

  public TranslateRequest languages(LanguagePair languages) {
    this.languages = languages;
    return this;
  }

  /**
   * Get languages
   * @return languages
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public LanguagePair getLanguages() {
    return languages;
  }

  public void setLanguages(LanguagePair languages) {
    this.languages = languages;
  }

  public TranslateRequest sentences(Sentences sentences) {
    this.sentences = sentences;
    return this;
  }

  /**
   * Get sentences
   * @return sentences
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public Sentences getSentences() {
    return sentences;
  }

  public void setSentences(Sentences sentences) {
    this.sentences = sentences;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TranslateRequest translateRequest = (TranslateRequest) o;
    return Objects.equals(this.languages, translateRequest.languages) &&
        Objects.equals(this.sentences, translateRequest.sentences);
  }

  @Override
  public int hashCode() {
    return Objects.hash(languages, sentences);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TranslateRequest {\n");
    
    sb.append("    languages: ").append(toIndentedString(languages)).append("\n");
    sb.append("    sentences: ").append(toIndentedString(sentences)).append("\n");
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
