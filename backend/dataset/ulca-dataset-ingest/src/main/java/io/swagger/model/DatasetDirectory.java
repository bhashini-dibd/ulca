package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.DatasetDataRowSchema;
import io.swagger.model.DatasetFile;
import io.swagger.model.DatasetParamsSchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * The object defines the physical structure of the uploaded dataset file. The dataset data and its respective parameters has to be present in an individual directory/folder. The dataset data should have name &#x27;data.json&#x27; or &#x27;data.csv&#x27;, similarly the associated parameters should have name &#x27;params.json&#x27; or &#x27;params.csv&#x27;. Dataset data file should adhere to &#x27;dataSchema&#x27; described in the properties, similarly the parameter of the dataset should adhere to &#x27;paramSchema&#x27;. Non compliant will result in rejection of the submission.
 */
@Schema(description = "The object defines the physical structure of the uploaded dataset file. The dataset data and its respective parameters has to be present in an individual directory/folder. The dataset data should have name 'data.json' or 'data.csv', similarly the associated parameters should have name 'params.json' or 'params.csv'. Dataset data file should adhere to 'dataSchema' described in the properties, similarly the parameter of the dataset should adhere to 'paramSchema'. Non compliant will result in rejection of the submission.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:41:06.560Z[GMT]")


public class DatasetDirectory   {
  @JsonProperty("data")
  private DatasetFile data = null;

  @JsonProperty("dataSchema")
  private DatasetDataRowSchema dataSchema = null;

  @JsonProperty("params")
  private DatasetFile params = null;

  @JsonProperty("paramSchema")
  private DatasetParamsSchema paramSchema = null;

  public DatasetDirectory data(DatasetFile data) {
    this.data = data;
    return this;
  }

  /**
   * Get data
   * @return data
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public DatasetFile getData() {
    return data;
  }

  public void setData(DatasetFile data) {
    this.data = data;
  }

  public DatasetDirectory dataSchema(DatasetDataRowSchema dataSchema) {
    this.dataSchema = dataSchema;
    return this;
  }

  /**
   * Get dataSchema
   * @return dataSchema
   **/
  @Schema(description = "")
  
    @Valid
    public DatasetDataRowSchema getDataSchema() {
    return dataSchema;
  }

  public void setDataSchema(DatasetDataRowSchema dataSchema) {
    this.dataSchema = dataSchema;
  }

  public DatasetDirectory params(DatasetFile params) {
    this.params = params;
    return this;
  }

  /**
   * Get params
   * @return params
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public DatasetFile getParams() {
    return params;
  }

  public void setParams(DatasetFile params) {
    this.params = params;
  }

  public DatasetDirectory paramSchema(DatasetParamsSchema paramSchema) {
    this.paramSchema = paramSchema;
    return this;
  }

  /**
   * Get paramSchema
   * @return paramSchema
   **/
  @Schema(description = "")
  
    @Valid
    public DatasetParamsSchema getParamSchema() {
    return paramSchema;
  }

  public void setParamSchema(DatasetParamsSchema paramSchema) {
    this.paramSchema = paramSchema;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatasetDirectory datasetDirectory = (DatasetDirectory) o;
    return Objects.equals(this.data, datasetDirectory.data) &&
        Objects.equals(this.dataSchema, datasetDirectory.dataSchema) &&
        Objects.equals(this.params, datasetDirectory.params) &&
        Objects.equals(this.paramSchema, datasetDirectory.paramSchema);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, dataSchema, params, paramSchema);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatasetDirectory {\n");
    
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    dataSchema: ").append(toIndentedString(dataSchema)).append("\n");
    sb.append("    params: ").append(toIndentedString(params)).append("\n");
    sb.append("    paramSchema: ").append(toIndentedString(paramSchema)).append("\n");
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
