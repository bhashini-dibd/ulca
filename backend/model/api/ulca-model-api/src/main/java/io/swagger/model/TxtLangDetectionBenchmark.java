package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Benchmark;
import io.swagger.model.Domain;
import io.swagger.model.TxtLangDetectionBenchmarkMetric;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Language detection benchmark representation
 */
@Schema(description = "Language detection benchmark representation")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:57:01.789Z[GMT]")


public class TxtLangDetectionBenchmark extends Benchmark implements OneOfBenchmarksItems {
  @JsonProperty("score")
  @Valid
  private List<TxtLangDetectionBenchmarkMetric> score = null;

  public TxtLangDetectionBenchmark score(List<TxtLangDetectionBenchmarkMetric> score) {
    this.score = score;
    return this;
  }

  public TxtLangDetectionBenchmark addScoreItem(TxtLangDetectionBenchmarkMetric scoreItem) {
    if (this.score == null) {
      this.score = new ArrayList<TxtLangDetectionBenchmarkMetric>();
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
    public List<TxtLangDetectionBenchmarkMetric> getScore() {
    return score;
  }

  public void setScore(List<TxtLangDetectionBenchmarkMetric> score) {
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
    TxtLangDetectionBenchmark txtLangDetectionBenchmark = (TxtLangDetectionBenchmark) o;
    return Objects.equals(this.score, txtLangDetectionBenchmark.score) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(score, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TxtLangDetectionBenchmark {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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
