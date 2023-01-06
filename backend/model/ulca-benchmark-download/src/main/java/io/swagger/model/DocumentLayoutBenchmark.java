package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Benchmark;
import io.swagger.model.DocumentLayoutBenchmarkMetric;
import io.swagger.model.Domain;
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


public class DocumentLayoutBenchmark extends Benchmark implements OneOfBenchmarksItems {
  @JsonProperty("score")
  @Valid
  private List<DocumentLayoutBenchmarkMetric> score = null;

  public DocumentLayoutBenchmark score(List<DocumentLayoutBenchmarkMetric> score) {
    this.score = score;
    return this;
  }

  public DocumentLayoutBenchmark addScoreItem(DocumentLayoutBenchmarkMetric scoreItem) {
    if (this.score == null) {
      this.score = new ArrayList<DocumentLayoutBenchmarkMetric>();
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
    public List<DocumentLayoutBenchmarkMetric> getScore() {
    return score;
  }

  public void setScore(List<DocumentLayoutBenchmarkMetric> score) {
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
    DocumentLayoutBenchmark documentLayoutBenchmark = (DocumentLayoutBenchmark) o;
    return Objects.equals(this.score, documentLayoutBenchmark.score) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(score, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentLayoutBenchmark {\n");
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
