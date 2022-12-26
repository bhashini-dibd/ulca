package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Sentence;
import io.swagger.model.TxtLangDetectionBenchmarkMetric;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TxtLangDetectionIndividualBenchmarkResult
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:57:01.789Z[GMT]")


public class TxtLangDetectionIndividualBenchmarkResult extends Sentence  {
  @JsonProperty("scores")
  @Valid
  private List<TxtLangDetectionBenchmarkMetric> scores = null;

  public TxtLangDetectionIndividualBenchmarkResult scores(List<TxtLangDetectionBenchmarkMetric> scores) {
    this.scores = scores;
    return this;
  }

  public TxtLangDetectionIndividualBenchmarkResult addScoresItem(TxtLangDetectionBenchmarkMetric scoresItem) {
    if (this.scores == null) {
      this.scores = new ArrayList<TxtLangDetectionBenchmarkMetric>();
    }
    this.scores.add(scoresItem);
    return this;
  }

  /**
   * score calculated for each metric mentioned in selected benchmark
   * @return scores
   **/
  @Schema(description = "score calculated for each metric mentioned in selected benchmark")
      @Valid
    public List<TxtLangDetectionBenchmarkMetric> getScores() {
    return scores;
  }

  public void setScores(List<TxtLangDetectionBenchmarkMetric> scores) {
    this.scores = scores;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TxtLangDetectionIndividualBenchmarkResult txtLangDetectionIndividualBenchmarkResult = (TxtLangDetectionIndividualBenchmarkResult) o;
    return Objects.equals(this.scores, txtLangDetectionIndividualBenchmarkResult.scores) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scores, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TxtLangDetectionIndividualBenchmarkResult {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    scores: ").append(toIndentedString(scores)).append("\n");
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
