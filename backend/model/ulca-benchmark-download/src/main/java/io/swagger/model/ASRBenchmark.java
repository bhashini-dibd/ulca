package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.ASRBenchmarkMetric;
import io.swagger.model.Benchmark;
import io.swagger.model.Domain;
import io.swagger.model.LanguagePair;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Document layout benchmark representation
 */
@Schema(description = "Document layout benchmark representation")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:57:01.789Z[GMT]")


public class ASRBenchmark extends Benchmark implements OneOfBenchmarksItems {
  @JsonProperty("languages")
  private LanguagePair languages = null;

  @JsonProperty("score")
  @Valid
  private List<ASRBenchmarkMetric> score = null;

  public ASRBenchmark languages(LanguagePair languages) {
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

  public ASRBenchmark score(List<ASRBenchmarkMetric> score) {
    this.score = score;
    return this;
  }

  public ASRBenchmark addScoreItem(ASRBenchmarkMetric scoreItem) {
    if (this.score == null) {
      this.score = new ArrayList<ASRBenchmarkMetric>();
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
    public List<ASRBenchmarkMetric> getScore() {
    return score;
  }

  public void setScore(List<ASRBenchmarkMetric> score) {
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
    ASRBenchmark asRBenchmark = (ASRBenchmark) o;
    return Objects.equals(this.languages, asRBenchmark.languages) &&
        Objects.equals(this.score, asRBenchmark.score) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(languages, score, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ASRBenchmark {\n");
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
