package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.DocumentLayoutBenchmarkMetric;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DocumentLayoutIndividualPageBenchmarkResult
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:57:01.789Z[GMT]")


public class DocumentLayoutIndividualPageBenchmarkResult   {
  @JsonProperty("class")
  private String propertyClass = null;

  @JsonProperty("scores")
  @Valid
  private List<DocumentLayoutBenchmarkMetric> scores = null;

  public DocumentLayoutIndividualPageBenchmarkResult propertyClass(String propertyClass) {
    this.propertyClass = propertyClass;
    return this;
  }

  /**
   * supported type of document layout
   * @return propertyClass
   **/
  @Schema(description = "supported type of document layout")
  
    public String getPropertyClass() {
    return propertyClass;
  }

  public void setPropertyClass(String propertyClass) {
    this.propertyClass = propertyClass;
  }

  public DocumentLayoutIndividualPageBenchmarkResult scores(List<DocumentLayoutBenchmarkMetric> scores) {
    this.scores = scores;
    return this;
  }

  public DocumentLayoutIndividualPageBenchmarkResult addScoresItem(DocumentLayoutBenchmarkMetric scoresItem) {
    if (this.scores == null) {
      this.scores = new ArrayList<DocumentLayoutBenchmarkMetric>();
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
    public List<DocumentLayoutBenchmarkMetric> getScores() {
    return scores;
  }

  public void setScores(List<DocumentLayoutBenchmarkMetric> scores) {
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
    DocumentLayoutIndividualPageBenchmarkResult documentLayoutIndividualPageBenchmarkResult = (DocumentLayoutIndividualPageBenchmarkResult) o;
    return Objects.equals(this.propertyClass, documentLayoutIndividualPageBenchmarkResult.propertyClass) &&
        Objects.equals(this.scores, documentLayoutIndividualPageBenchmarkResult.scores);
  }

  @Override
  public int hashCode() {
    return Objects.hash(propertyClass, scores);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentLayoutIndividualPageBenchmarkResult {\n");
    
    sb.append("    propertyClass: ").append(toIndentedString(propertyClass)).append("\n");
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
