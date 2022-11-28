package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * informs the progress for every submitted records in the dataset.
 */
@Schema(description = "informs the progress for every submitted records in the dataset.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-25T09:44:34.039Z[GMT]")


public class DatasetProgressInformation   {
  @JsonProperty("totalRecordCount")
  private Integer totalRecordCount = null;

  @JsonProperty("validaatedRecordCount")
  private Integer validaatedRecordCount = null;

  @JsonProperty("failedRecordCount")
  private Integer failedRecordCount = null;

  @JsonProperty("validationReportId")
  private String validationReportId = null;

  public DatasetProgressInformation totalRecordCount(Integer totalRecordCount) {
    this.totalRecordCount = totalRecordCount;
    return this;
  }

  /**
   * total number of records count submitted
   * @return totalRecordCount
   **/
  @Schema(description = "total number of records count submitted")
  
    public Integer getTotalRecordCount() {
    return totalRecordCount;
  }

  public void setTotalRecordCount(Integer totalRecordCount) {
    this.totalRecordCount = totalRecordCount;
  }

  public DatasetProgressInformation validaatedRecordCount(Integer validaatedRecordCount) {
    this.validaatedRecordCount = validaatedRecordCount;
    return this;
  }

  /**
   * how many records have validated
   * @return validaatedRecordCount
   **/
  @Schema(description = "how many records have validated")
  
    public Integer getValidaatedRecordCount() {
    return validaatedRecordCount;
  }

  public void setValidaatedRecordCount(Integer validaatedRecordCount) {
    this.validaatedRecordCount = validaatedRecordCount;
  }

  public DatasetProgressInformation failedRecordCount(Integer failedRecordCount) {
    this.failedRecordCount = failedRecordCount;
    return this;
  }

  /**
   * how many records failed the valdiation
   * @return failedRecordCount
   **/
  @Schema(description = "how many records failed the valdiation")
  
    public Integer getFailedRecordCount() {
    return failedRecordCount;
  }

  public void setFailedRecordCount(Integer failedRecordCount) {
    this.failedRecordCount = failedRecordCount;
  }

  public DatasetProgressInformation validationReportId(String validationReportId) {
    this.validationReportId = validationReportId;
    return this;
  }

  /**
   * download why the records failed the validation
   * @return validationReportId
   **/
  @Schema(description = "download why the records failed the validation")
  
    public String getValidationReportId() {
    return validationReportId;
  }

  public void setValidationReportId(String validationReportId) {
    this.validationReportId = validationReportId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatasetProgressInformation datasetProgressInformation = (DatasetProgressInformation) o;
    return Objects.equals(this.totalRecordCount, datasetProgressInformation.totalRecordCount) &&
        Objects.equals(this.validaatedRecordCount, datasetProgressInformation.validaatedRecordCount) &&
        Objects.equals(this.failedRecordCount, datasetProgressInformation.failedRecordCount) &&
        Objects.equals(this.validationReportId, datasetProgressInformation.validationReportId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalRecordCount, validaatedRecordCount, failedRecordCount, validationReportId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatasetProgressInformation {\n");
    
    sb.append("    totalRecordCount: ").append(toIndentedString(totalRecordCount)).append("\n");
    sb.append("    validaatedRecordCount: ").append(toIndentedString(validaatedRecordCount)).append("\n");
    sb.append("    failedRecordCount: ").append(toIndentedString(failedRecordCount)).append("\n");
    sb.append("    validationReportId: ").append(toIndentedString(validationReportId)).append("\n");
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
