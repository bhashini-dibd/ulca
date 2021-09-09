package io.swagger.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import org.apache.kafka.common.MetricName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the benchmark dataset for model task.
 */
@Schema(description = "the benchmark dataset for model task.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-08-26T01:12:13.097Z[GMT]")

@Document(collection = "benchmark")
public class Benchmark   {
	
	@Id
  @JsonProperty("benchmarkId")
  private String benchmarkId = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;
 
  @JsonProperty("metric")
  private List<String> metric = null;

  @JsonProperty("dataset")
  private String dataset = null;

  @JsonProperty("domain")
  private Domain domain = null;
  
  @JsonProperty("task")
  private ModelTask task = null;
  

  @JsonProperty("createdOn")
  private String createdOn = null;

  @JsonProperty("submittedOn")
  private String submittedOn = null;

  public Benchmark benchmarkId(String benchmarkId) {
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

  public Benchmark name(String name) {
    this.name = name;
    return this;
  }

  /**
   * name of the benchmark
   * @return name
   **/
  @Schema(required = true, description = "name of the benchmark")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Benchmark description(String description) {
    this.description = description;
    return this;
  }

  /**
   * description of the benchmark including how it has been curated
   * @return description
   **/
  @Schema(example = "benchmark sentences for government press release domain", required = true, description = "description of the benchmark including how it has been curated")
      @NotNull

  @Size(min=50,max=200)   public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  public Benchmark metric(List<String> metric) {
	    this.metric = metric;
	    return this;
	  }
 
  public List<String> getMetric() {
		return metric;
	}

	public void setMetric(List<String> metric) {
		this.metric = metric;
	}
  
  public Benchmark dataset(String dataset) {
	    this.dataset = dataset;
	    return this;
	  }

	  /**
	   * description of the benchmark including how it has been curated
	   * @return description
	   **/
	  @Schema(example = "benchmark dataset url", required = true, description = "benchmark dataset url")
	      @NotNull

	  @Size(min=50,max=200)   public String getDataset() {
	    return dataset;
	  }

	  public void setDataset(String dataset) {
	    this.dataset = dataset;
	  }
	  

  public Benchmark domain(Domain domain) {
    this.domain = domain;
    return this;
  }

  /**
   * Get domain
   * @return domain
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public Domain getDomain() {
    return domain;
  }

  public void setDomain(Domain domain) {
    this.domain = domain;
  }
  
  public Benchmark task(ModelTask task) {
	    this.task = task;
	    return this;
	  }

	  /**
	   * Get task
	   * @return task
	   **/
	  @Schema(required = true, description = "")
	      @NotNull

	    @Valid
	    public ModelTask getTask() {
	    return task;
	  }

	  public void setTask(ModelTask task) {
	    this.task = task;
	  }

  public Benchmark createdOn(String createdOn) {
    this.createdOn = createdOn;
    return this;
  }

  /**
   * timestamp when benchmark is created
   * @return createdOn
   **/
  @Schema(description = "timestamp when benchmark is created")
  
    public String getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(String createdOn) {
    this.createdOn = createdOn;
  }

  public Benchmark submittedOn(String submittedOn) {
    this.submittedOn = submittedOn;
    return this;
  }

  /**
   * timestamp when benchmark is submitted/published
   * @return submittedOn
   **/
  @Schema(description = "timestamp when benchmark is submitted/published")
  
    public String getSubmittedOn() {
    return submittedOn;
  }

  public void setSubmittedOn(String submittedOn) {
    this.submittedOn = submittedOn;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Benchmark benchmark = (Benchmark) o;
    return Objects.equals(this.benchmarkId, benchmark.benchmarkId) &&
        Objects.equals(this.name, benchmark.name) &&
        Objects.equals(this.description, benchmark.description) &&
        Objects.equals(this.dataset, benchmark.dataset) &&
        Objects.equals(this.domain, benchmark.domain) &&
        Objects.equals(this.task, benchmark.task) &&
        Objects.equals(this.createdOn, benchmark.createdOn) &&
        Objects.equals(this.submittedOn, benchmark.submittedOn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(benchmarkId, name, description, domain, createdOn, submittedOn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Benchmark {\n");
    
    sb.append("    benchmarkId: ").append(toIndentedString(benchmarkId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    dataset: ").append(toIndentedString(dataset)).append("\n");
    sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
    sb.append("    task: ").append(toIndentedString(task)).append("\n");
    sb.append("    createdOn: ").append(toIndentedString(createdOn)).append("\n");
    sb.append("    submittedOn: ").append(toIndentedString(submittedOn)).append("\n");
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
