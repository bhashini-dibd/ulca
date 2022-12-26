package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the dataset params.json/.csv file should follow this schema
 */
@Schema(description = "the dataset params.json/.csv file should follow this schema")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:41:06.560Z[GMT]")


public class DatasetParamsSchema   {
  @JsonProperty("params")
  private OneOfDatasetParamsSchemaParams params = null;

  public DatasetParamsSchema params(OneOfDatasetParamsSchemaParams params) {
    this.params = params;
    return this;
  }

  /**
   * Get params
   * @return params
   **/
  @Schema(description = "")
  
    public OneOfDatasetParamsSchemaParams getParams() {
    return params;
  }

  public void setParams(OneOfDatasetParamsSchemaParams params) {
    this.params = params;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatasetParamsSchema datasetParamsSchema = (DatasetParamsSchema) o;
    return Objects.equals(this.params, datasetParamsSchema.params);
  }

  @Override
  public int hashCode() {
    return Objects.hash(params);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatasetParamsSchema {\n");
    
    sb.append("    params: ").append(toIndentedString(params)).append("\n");
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
