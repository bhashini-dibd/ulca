package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Benchmark;
import io.swagger.model.Domain;
import io.swagger.model.LanguagePair;
import io.swagger.model.TranslationBenchmarkMetric;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Translation benchmark representation
 */
@Schema(description = "Translation benchmark representation")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:57:01.789Z[GMT]")


public class TranslationBenchmark extends Benchmark implements OneOfBenchmarksItems {
  @JsonProperty("languages")
  private LanguagePair languages = null;

  @JsonProperty("score")
  @Valid
  private List<TranslationBenchmarkMetric> score = null;

  public TranslationBenchmark languages(LanguagePair languages) {
    this.languages = languages;
    return this;
  }

  /**
   * Get languages
   * @return languages
   **/
  @Schema(description = "")
  
    @Valid
    public LanguagePair getLanguages() {
    return languages;
  }

  public void setLanguages(LanguagePair languages) {
    this.languages = languages;
  }

  public TranslationBenchmark score(List<TranslationBenchmarkMetric> score) {
    this.score = score;
    return this;
  }

  public TranslationBenchmark addScoreItem(TranslationBenchmarkMetric scoreItem) {
    if (this.score == null) {
      this.score = new ArrayList<TranslationBenchmarkMetric>();
    }
    this.score.add(scoreItem);
    return this;
  }

  /**
   * Get score
   * @return score
   **/
  @Schema(description = "")
      @Valid
    public List<TranslationBenchmarkMetric> getScore() {
    return score;
  }

  public void setScore(List<TranslationBenchmarkMetric> score) {
    this.score = score;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TranslationBenchmark translationBenchmark = (TranslationBenchmark) o;
    return Objects.equals(this.languages, translationBenchmark.languages) &&
        Objects.equals(this.score, translationBenchmark.score) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(languages, score, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TranslationBenchmark {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    languages: ").append(toIndentedString(languages)).append("\n");
    sb.append("    score: ").append(toIndentedString(score)).append("\n");
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
