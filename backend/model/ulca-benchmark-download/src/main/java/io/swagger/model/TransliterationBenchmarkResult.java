package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.TransliterationBenchmarkMetric;
import io.swagger.model.TransliterationIndividualBenchmarkResult;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * describes the translation benchmark result
 */
@Schema(description = "describes the translation benchmark result")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:57:01.789Z[GMT]")


public class TransliterationBenchmarkResult  implements OneOfModelBenchmarksPerformance {
  @JsonProperty("benchmarkId")
  private String benchmarkId = null;

  @JsonProperty("results")
  @Valid
  private List<TransliterationIndividualBenchmarkResult> results = null;

  @JsonProperty("summaries")
  @Valid
  private List<TransliterationBenchmarkMetric> summaries = null;

  public TransliterationBenchmarkResult benchmarkId(String benchmarkId) {
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

  public TransliterationBenchmarkResult results(List<TransliterationIndividualBenchmarkResult> results) {
    this.results = results;
    return this;
  }

  public TransliterationBenchmarkResult addResultsItem(TransliterationIndividualBenchmarkResult resultsItem) {
    if (this.results == null) {
      this.results = new ArrayList<TransliterationIndividualBenchmarkResult>();
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
    public List<TransliterationIndividualBenchmarkResult> getResults() {
    return results;
  }

  public void setResults(List<TransliterationIndividualBenchmarkResult> results) {
    this.results = results;
  }

  public TransliterationBenchmarkResult summaries(List<TransliterationBenchmarkMetric> summaries) {
    this.summaries = summaries;
    return this;
  }

  public TransliterationBenchmarkResult addSummariesItem(TransliterationBenchmarkMetric summariesItem) {
    if (this.summaries == null) {
      this.summaries = new ArrayList<TransliterationBenchmarkMetric>();
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
    public List<TransliterationBenchmarkMetric> getSummaries() {
    return summaries;
  }

  public void setSummaries(List<TransliterationBenchmarkMetric> summaries) {
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
    TransliterationBenchmarkResult transliterationBenchmarkResult = (TransliterationBenchmarkResult) o;
    return Objects.equals(this.benchmarkId, transliterationBenchmarkResult.benchmarkId) &&
        Objects.equals(this.results, transliterationBenchmarkResult.results) &&
        Objects.equals(this.summaries, transliterationBenchmarkResult.summaries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(benchmarkId, results, summaries);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransliterationBenchmarkResult {\n");
    
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
