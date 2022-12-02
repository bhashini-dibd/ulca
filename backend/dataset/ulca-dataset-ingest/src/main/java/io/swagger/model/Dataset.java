package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.DatasetFileIdentifier;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * describes the dataset schema
 */
@Schema(description = "describes the dataset schema")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-25T09:44:34.039Z[GMT]")


public class Dataset   {
  @JsonProperty("datasetId")
  private String datasetId = null;

  @JsonProperty("count")
  private BigDecimal count = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("datasetFileId")
  private DatasetFileIdentifier datasetFileId = null;

  @JsonProperty("submittedOn")
  private String submittedOn = null;

  @JsonProperty("validatedOn")
  private String validatedOn = null;

  @JsonProperty("publishedOn")
  private String publishedOn = null;

  public Dataset datasetId(String datasetId) {
    this.datasetId = datasetId;
    return this;
  }

  /**
   * unique identification of the dataset. This will be auto-generated value once the submitted dataset is validated by the system
   * @return datasetId
   **/
  @Schema(description = "unique identification of the dataset. This will be auto-generated value once the submitted dataset is validated by the system")
  
    public String getDatasetId() {
    return datasetId;
  }

  public void setDatasetId(String datasetId) {
    this.datasetId = datasetId;
  }

  public Dataset count(BigDecimal count) {
    this.count = count;
    return this;
  }

  /**
   * actual number of unique records present under a dataset. The count can/will vary from the original submitted dataset because of validation stage.
   * minimum: 100
   * @return count
   **/
  @Schema(description = "actual number of unique records present under a dataset. The count can/will vary from the original submitted dataset because of validation stage.")
  
    @Valid
  @DecimalMin("100")  public BigDecimal getCount() {
    return count;
  }

  public void setCount(BigDecimal count) {
    this.count = count;
  }

  public Dataset description(String description) {
    this.description = description;
    return this;
  }

  /**
   * describes the purpose and usage of the dataset so that other user can get benefit out of it.
   * @return description
   **/
  @Schema(example = "contribution from team Anuvaad for government press release domain", required = true, description = "describes the purpose and usage of the dataset so that other user can get benefit out of it.")
      @NotNull

  @Size(min=50,max=200)   public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Dataset datasetFileId(DatasetFileIdentifier datasetFileId) {
    this.datasetFileId = datasetFileId;
    return this;
  }

  /**
   * Get datasetFileId
   * @return datasetFileId
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public DatasetFileIdentifier getDatasetFileId() {
    return datasetFileId;
  }

  public void setDatasetFileId(DatasetFileIdentifier datasetFileId) {
    this.datasetFileId = datasetFileId;
  }

  public Dataset submittedOn(String submittedOn) {
    this.submittedOn = submittedOn;
    return this;
  }

  /**
   * timestamp when dataset is uploaded
   * @return submittedOn
   **/
  @Schema(description = "timestamp when dataset is uploaded")
  
    public String getSubmittedOn() {
    return submittedOn;
  }

  public void setSubmittedOn(String submittedOn) {
    this.submittedOn = submittedOn;
  }

  public Dataset validatedOn(String validatedOn) {
    this.validatedOn = validatedOn;
    return this;
  }

  /**
   * timestamp when dataset is validated
   * @return validatedOn
   **/
  @Schema(description = "timestamp when dataset is validated")
  
    public String getValidatedOn() {
    return validatedOn;
  }

  public void setValidatedOn(String validatedOn) {
    this.validatedOn = validatedOn;
  }

  public Dataset publishedOn(String publishedOn) {
    this.publishedOn = publishedOn;
    return this;
  }

  /**
   * timestamp when dataset got listed
   * @return publishedOn
   **/
  @Schema(description = "timestamp when dataset got listed")
  
    public String getPublishedOn() {
    return publishedOn;
  }

  public void setPublishedOn(String publishedOn) {
    this.publishedOn = publishedOn;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Dataset dataset = (Dataset) o;
    return Objects.equals(this.datasetId, dataset.datasetId) &&
        Objects.equals(this.count, dataset.count) &&
        Objects.equals(this.description, dataset.description) &&
        Objects.equals(this.datasetFileId, dataset.datasetFileId) &&
        Objects.equals(this.submittedOn, dataset.submittedOn) &&
        Objects.equals(this.validatedOn, dataset.validatedOn) &&
        Objects.equals(this.publishedOn, dataset.publishedOn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datasetId, count, description, datasetFileId, submittedOn, validatedOn, publishedOn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Dataset {\n");
    
    sb.append("    datasetId: ").append(toIndentedString(datasetId)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    datasetFileId: ").append(toIndentedString(datasetFileId)).append("\n");
    sb.append("    submittedOn: ").append(toIndentedString(submittedOn)).append("\n");
    sb.append("    validatedOn: ").append(toIndentedString(validatedOn)).append("\n");
    sb.append("    publishedOn: ").append(toIndentedString(publishedOn)).append("\n");
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
