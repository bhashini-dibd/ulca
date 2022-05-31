package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Domain;
import io.swagger.model.LanguagePair;
import io.swagger.model.License;
import io.swagger.model.ModelTask;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * common parameters or attributes of dataset that is primarily same across the supported dataset.
 */
@Schema(description = "common parameters or attributes of dataset that is primarily same across the supported dataset.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-02-17T09:58:25.365Z[GMT]")


public class BenchmarkDatasetCommonParamsSchema   {
  @JsonProperty("version")
  private String version = "1";

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("taskType")
  private ModelTask taskType = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("languages")
  private LanguagePair languages = null;

  @JsonProperty("collectionSource")
  private Source collectionSource = null;

  @JsonProperty("domain")
  private Domain domain = null;

  @JsonProperty("license")
  private License license = null;

  @JsonProperty("licenseUrl")
  private String licenseUrl = null;

  @JsonProperty("submitter")
  private Submitter submitter = null;

  public BenchmarkDatasetCommonParamsSchema version(String version) {
    this.version = version;
    return this;
  }

  /**
   * params schema version
   * @return version
   **/
  @Schema(description = "params schema version")
  
    public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public BenchmarkDatasetCommonParamsSchema name(String name) {
    this.name = name;
    return this;
  }

  /**
   * user defined name for the benchmark dataset
   * @return name
   **/
  @Schema(required = true, description = "user defined name for the benchmark dataset")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BenchmarkDatasetCommonParamsSchema taskType(ModelTask taskType) {
    this.taskType = taskType;
    return this;
  }

  /**
   * Get taskType
   * @return taskType
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ModelTask getTaskType() {
    return taskType;
  }

  public void setTaskType(ModelTask taskType) {
    this.taskType = taskType;
  }

  public BenchmarkDatasetCommonParamsSchema description(String description) {
    this.description = description;
    return this;
  }

  /**
   * description of the benchmark dataset type
   * @return description
   **/
  @Schema(required = true, description = "description of the benchmark dataset type")
      @NotNull

    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BenchmarkDatasetCommonParamsSchema languages(LanguagePair languages) {
    this.languages = languages;
    return this;
  }

  /**
   * Get languages
   * @return languages
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public LanguagePair getLanguages() {
    return languages;
  }

  public void setLanguages(LanguagePair languages) {
    this.languages = languages;
  }

  public BenchmarkDatasetCommonParamsSchema collectionSource(Source collectionSource) {
    this.collectionSource = collectionSource;
    return this;
  }

  /**
   * Get collectionSource
   * @return collectionSource
   **/
  @Schema(description = "")
  
    @Valid
    public Source getCollectionSource() {
    return collectionSource;
  }

  public void setCollectionSource(Source collectionSource) {
    this.collectionSource = collectionSource;
  }

  public BenchmarkDatasetCommonParamsSchema domain(Domain domain) {
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

  public BenchmarkDatasetCommonParamsSchema license(License license) {
    this.license = license;
    return this;
  }

  /**
   * Get license
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

  public BenchmarkDatasetCommonParamsSchema licenseUrl(String licenseUrl) {
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

  public BenchmarkDatasetCommonParamsSchema submitter(Submitter submitter) {
    this.submitter = submitter;
    return this;
  }

  /**
   * Get submitter
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BenchmarkDatasetCommonParamsSchema benchmarkDatasetCommonParamsSchema = (BenchmarkDatasetCommonParamsSchema) o;
    return Objects.equals(this.version, benchmarkDatasetCommonParamsSchema.version) &&
        Objects.equals(this.name, benchmarkDatasetCommonParamsSchema.name) &&
        Objects.equals(this.taskType, benchmarkDatasetCommonParamsSchema.taskType) &&
        Objects.equals(this.description, benchmarkDatasetCommonParamsSchema.description) &&
        Objects.equals(this.languages, benchmarkDatasetCommonParamsSchema.languages) &&
        Objects.equals(this.collectionSource, benchmarkDatasetCommonParamsSchema.collectionSource) &&
        Objects.equals(this.domain, benchmarkDatasetCommonParamsSchema.domain) &&
        Objects.equals(this.license, benchmarkDatasetCommonParamsSchema.license) &&
        Objects.equals(this.licenseUrl, benchmarkDatasetCommonParamsSchema.licenseUrl) &&
        Objects.equals(this.submitter, benchmarkDatasetCommonParamsSchema.submitter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, name, taskType, description, languages, collectionSource, domain, license, licenseUrl, submitter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BenchmarkDatasetCommonParamsSchema {\n");
    
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    taskType: ").append(toIndentedString(taskType)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    languages: ").append(toIndentedString(languages)).append("\n");
    sb.append("    collectionSource: ").append(toIndentedString(collectionSource)).append("\n");
    sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
    sb.append("    license: ").append(toIndentedString(license)).append("\n");
    sb.append("    licenseUrl: ").append(toIndentedString(licenseUrl)).append("\n");
    sb.append("    submitter: ").append(toIndentedString(submitter)).append("\n");
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
