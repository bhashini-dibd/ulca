package io.swagger.model;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * the benchmark dataset for model task.
 */
@Schema(description = "the benchmark dataset for model task.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-08-26T01:12:13.097Z[GMT]")

@Document(collection = "benchmark")
public class Benchmark {

	@Id
	@JsonProperty("benchmarkId")
	private String benchmarkId = null;

	@Indexed(unique = true)
	@JsonProperty("name")
	private String name = null;

	@JsonProperty("version")
	private String version = "1";

	@JsonProperty("license")
	private License license = null;
	
	@JsonProperty("licenseUrl")
	private String licenseUrl = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("userId")
	private String userId = null;

	@JsonProperty("dataset")
	private String dataset = null;

	@JsonProperty("domain")
	private Domain domain = null;

	@JsonProperty("task")
	private ModelTask task = null;

	@JsonProperty("languages")
	private LanguagePair languages = null;

	@JsonProperty("submitter")
	private Submitter submitter = null;

	@JsonProperty("collectionSource")
	private Source collectionSource = null;

	@JsonProperty("paramSchema")
	private Object paramSchema = null;

	@JsonProperty("createdOn")
	private long createdOn;

	@JsonProperty("submittedOn")
	private long submittedOn;

	@JsonProperty("status")
	private String status = null;

	public Benchmark benchmarkId(String benchmarkId) {
		this.benchmarkId = benchmarkId;
		return this;
	}

	/**
	 * auto-generated unique identification of benchmark data
	 * 
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
	 * 
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

	public Benchmark version(String version) {
		this.version = version;
		return this;
	}

	/**
	 * params schema version
	 * 
	 * @return version
	 **/
	@Schema(description = "params schema version")

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Benchmark license(License license) {
		this.license = license;
		return this;
	}

	/**
	 * Get license
	 * 
	 * @return license
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}
	
	public Benchmark licenseUrl(String licenseUrl) {
	    this.licenseUrl = licenseUrl;
	    return this;
	  }

	  /**
	   * url of the custom license
	   * @return licenseUrl
	   **/
	  @Schema(description = "url of the custom license")
	  
	    public String getLicenseUrl() {
	    return licenseUrl;
	  }

	  public void setLicenseUrl(String licenseUrl) {
	    this.licenseUrl = licenseUrl;
	  }


	public Benchmark description(String description) {
		this.description = description;
		return this;
	}

	/**
	 * description of the benchmark including how it has been curated
	 * 
	 * @return description
	 **/
	@Schema(example = "benchmark sentences for government press release domain", required = true, description = "description of the benchmark including how it has been curated")
	@NotNull

	@Size(min = 50, max = 200)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Benchmark userId(String userId) {
		this.userId = userId;
		return this;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Benchmark dataset(String dataset) {
		this.dataset = dataset;
		return this;
	}

	/**
	 * description of the benchmark including how it has been curated
	 * 
	 * @return description
	 **/
	@Schema(example = "benchmark dataset url", required = true, description = "benchmark dataset url")
	@NotNull

	@Size(min = 50, max = 200)
	public String getDataset() {
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
	 * 
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
	 * 
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

	public Benchmark languages(LanguagePair languages) {
		this.languages = languages;
		return this;
	}

	/**
	 * Get languages
	 * 
	 * @return languages
	 **/
	@Schema(required = true, description = "")
	@NotNull
	public LanguagePair getLanguages() {
		return languages;
	}

	public void setLanguages(LanguagePair languages) {
		this.languages = languages;
	}

	public Benchmark submitter(Submitter submitter) {
		this.submitter = submitter;
		return this;
	}

	/**
	 * Get submitter
	 * 
	 * @return submitter
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public Submitter getSubmitter() {
		return submitter;
	}

	public void setSubmitter(Submitter submitter) {
		this.submitter = submitter;
	}

	public Benchmark collectionSource(Source collectionSource) {
		this.collectionSource = collectionSource;
		return this;
	}

	/**
	 * various sources, url from where the information is collected.
	 * 
	 * @return collectionSource
	 **/
	@Schema(example = "[\"https://main.sci.gov.in\"]", description = "various sources, url from where the information is collected.")

	@Valid
	public Source getCollectionSource() {
		return collectionSource;
	}

	public void setCollectionSource(Source collectionSource) {
		this.collectionSource = collectionSource;
	}

	public Benchmark paramSchema(Object paramSchema) {
		this.paramSchema = paramSchema;
		return this;
	}

	/**
	 * timestamp when benchmark is created
	 * 
	 * @return createdOn
	 **/
	@Schema(description = "timestamp when benchmark is created")

	public Object getParamSchema() {
		return paramSchema;
	}

	public void setParamSchema(Object paramSchema) {
		this.paramSchema = paramSchema;
	}

	public Benchmark createdOn(long createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	/**
	 * timestamp when benchmark is created
	 * 
	 * @return createdOn
	 **/
	@Schema(description = "timestamp when benchmark is created")

	public long getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}

	public Benchmark submittedOn(long submittedOn) {
		this.submittedOn = submittedOn;
		return this;
	}

	/**
	 * timestamp when benchmark is submitted/published
	 * 
	 * @return submittedOn
	 **/
	@Schema(description = "timestamp when benchmark is submitted/published")

	public long getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(long submittedOn) {
		this.submittedOn = submittedOn;
	}

	public Benchmark status(String status) {
		this.status = status;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		return Objects.equals(this.benchmarkId, benchmark.benchmarkId)
				&& Objects.equals(this.name, benchmark.name)
				&& Objects.equals(this.license, benchmark.license)
				&& Objects.equals(this.licenseUrl, benchmark.licenseUrl)
				&& Objects.equals(this.description, benchmark.description)
				&& Objects.equals(this.userId, benchmark.userId) && Objects.equals(this.dataset, benchmark.dataset)
				&& Objects.equals(this.domain, benchmark.domain) && Objects.equals(this.task, benchmark.task)
				&& Objects.equals(this.languages, benchmark.languages)
				&& Objects.equals(this.submitter, benchmark.submitter)
				&& Objects.equals(this.createdOn, benchmark.createdOn)
				&& Objects.equals(this.submittedOn, benchmark.submittedOn)
				&& Objects.equals(this.description, benchmark.description);
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
		sb.append("    license: ").append(toIndentedString(license)).append("\n");
	    sb.append("    licenseUrl: ").append(toIndentedString(licenseUrl)).append("\n");
		sb.append("    description: ").append(toIndentedString(description)).append("\n");
		sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
		sb.append("    dataset: ").append(toIndentedString(dataset)).append("\n");
		sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
		sb.append("    task: ").append(toIndentedString(task)).append("\n");
		sb.append("    languages: ").append(toIndentedString(languages)).append("\n");
		sb.append("    submitter: ").append(toIndentedString(submitter)).append("\n");
		sb.append("    createdOn: ").append(toIndentedString(createdOn)).append("\n");
		sb.append("    submittedOn: ").append(toIndentedString(submittedOn)).append("\n");
		sb.append("    submittedOn: ").append(toIndentedString(status)).append("\n");
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
