package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.DatasetType;
import io.swagger.model.Domain;
import io.swagger.model.LanguagePair;
import io.swagger.model.License;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-02-17T09:27:38.837Z[GMT]")


public class DatasetCommonParamsSchema   {
  @JsonProperty("version")
  private String version = "1";

  @JsonProperty("datasetType")
  private DatasetType datasetType = null;

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

  public DatasetCommonParamsSchema version(String version) {
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

  public DatasetCommonParamsSchema datasetType(DatasetType datasetType) {
    this.datasetType = datasetType;
    return this;
  }

  /**
   * Get datasetType
   * @return datasetType
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public DatasetType getDatasetType() {
    return datasetType;
  }

  public void setDatasetType(DatasetType datasetType) {
    this.datasetType = datasetType;
  }

  public DatasetCommonParamsSchema languages(LanguagePair languages) {
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

  public DatasetCommonParamsSchema collectionSource(Source collectionSource) {
    this.collectionSource = collectionSource;
    return this;
  }

  /**
   * Get collectionSource
   * @return collectionSource
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public Source getCollectionSource() {
    return collectionSource;
  }

  public void setCollectionSource(Source collectionSource) {
    this.collectionSource = collectionSource;
  }

  public DatasetCommonParamsSchema domain(Domain domain) {
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

  public DatasetCommonParamsSchema license(License license) {
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

  public DatasetCommonParamsSchema licenseUrl(String licenseUrl) {
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

  public DatasetCommonParamsSchema submitter(Submitter submitter) {
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
    DatasetCommonParamsSchema datasetCommonParamsSchema = (DatasetCommonParamsSchema) o;
    return Objects.equals(this.version, datasetCommonParamsSchema.version) &&
        Objects.equals(this.datasetType, datasetCommonParamsSchema.datasetType) &&
        Objects.equals(this.languages, datasetCommonParamsSchema.languages) &&
        Objects.equals(this.collectionSource, datasetCommonParamsSchema.collectionSource) &&
        Objects.equals(this.domain, datasetCommonParamsSchema.domain) &&
        Objects.equals(this.license, datasetCommonParamsSchema.license) &&
        Objects.equals(this.licenseUrl, datasetCommonParamsSchema.licenseUrl) &&
        Objects.equals(this.submitter, datasetCommonParamsSchema.submitter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, datasetType, languages, collectionSource, domain, license, licenseUrl, submitter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatasetCommonParamsSchema {\n");
    
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    datasetType: ").append(toIndentedString(datasetType)).append("\n");
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
