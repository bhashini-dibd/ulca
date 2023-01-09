package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.DocumentOCRBenchmarkMetric;
import io.swagger.model.DocumentOCRIndividualPageBenchmarkResult;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * describes the document ocr benchmark result
 */
@Schema(description = "describes the document ocr benchmark result")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:57:01.789Z[GMT]")


public class DocumentOCRBenchmarkResult   {
  @JsonProperty("benchmarkId")
  private String benchmarkId = null;

  @JsonProperty("results")
  @Valid
  private List<DocumentOCRIndividualPageBenchmarkResult> results = null;

  @JsonProperty("summaries")
  @Valid
  private List<DocumentOCRBenchmarkMetric> summaries = null;

  public DocumentOCRBenchmarkResult benchmarkId(String benchmarkId) {
    this.benchmarkId = benchmarkId;
    return this;
  }

  /**
   * auto-generated unique identification of benchmark data
   * @return benchmarkId
   **/
  @Schema(description = "auto-generated unique identification of benchmark data")
  
    public String getBenchmarkId() {
    return benchmarkId;
  }

  public void setBenchmarkId(String benchmarkId) {
    this.benchmarkId = benchmarkId;
  }

  public DocumentOCRBenchmarkResult results(List<DocumentOCRIndividualPageBenchmarkResult> results) {
    this.results = results;
    return this;
  }

  public DocumentOCRBenchmarkResult addResultsItem(DocumentOCRIndividualPageBenchmarkResult resultsItem) {
    if (this.results == null) {
      this.results = new ArrayList<DocumentOCRIndividualPageBenchmarkResult>();
    }
    this.results.add(resultsItem);
    return this;
  }

  /**
   * Get results
   * @return results
   **/
  @Schema(description = "")
      @Valid
    public List<DocumentOCRIndividualPageBenchmarkResult> getResults() {
    return results;
  }

  public void setResults(List<DocumentOCRIndividualPageBenchmarkResult> results) {
    this.results = results;
  }

  public DocumentOCRBenchmarkResult summaries(List<DocumentOCRBenchmarkMetric> summaries) {
    this.summaries = summaries;
    return this;
  }

  public DocumentOCRBenchmarkResult addSummariesItem(DocumentOCRBenchmarkMetric summariesItem) {
    if (this.summaries == null) {
      this.summaries = new ArrayList<DocumentOCRBenchmarkMetric>();
    }
    this.summaries.add(summariesItem);
    return this;
  }

  /**
   * Get summaries
   * @return summaries
   **/
  @Schema(description = "")
      @Valid
    public List<DocumentOCRBenchmarkMetric> getSummaries() {
    return summaries;
  }

  public void setSummaries(List<DocumentOCRBenchmarkMetric> summaries) {
    this.summaries = summaries;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentOCRBenchmarkResult documentOCRBenchmarkResult = (DocumentOCRBenchmarkResult) o;
    return Objects.equals(this.benchmarkId, documentOCRBenchmarkResult.benchmarkId) &&
        Objects.equals(this.results, documentOCRBenchmarkResult.results) &&
        Objects.equals(this.summaries, documentOCRBenchmarkResult.summaries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(benchmarkId, results, summaries);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentOCRBenchmarkResult {\n");
    
    sb.append("    benchmarkId: ").append(toIndentedString(benchmarkId)).append("\n");
    sb.append("    results: ").append(toIndentedString(results)).append("\n");
    sb.append("    summaries: ").append(toIndentedString(summaries)).append("\n");
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
