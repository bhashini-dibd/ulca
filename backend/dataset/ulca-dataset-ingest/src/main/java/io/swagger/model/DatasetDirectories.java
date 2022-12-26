package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.DatasetDirectory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * The object represents a compressed file that can contains multiple &#x27;DatasetDirectory&#x27; object.
 */
@Schema(description = "The object represents a compressed file that can contains multiple 'DatasetDirectory' object.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:41:06.560Z[GMT]")


public class DatasetDirectories   {
  @JsonProperty("datasetFiles")
  @Valid
  private List<DatasetDirectory> datasetFiles = null;

  public DatasetDirectories datasetFiles(List<DatasetDirectory> datasetFiles) {
    this.datasetFiles = datasetFiles;
    return this;
  }

  public DatasetDirectories addDatasetFilesItem(DatasetDirectory datasetFilesItem) {
    if (this.datasetFiles == null) {
      this.datasetFiles = new ArrayList<DatasetDirectory>();
    }
    this.datasetFiles.add(datasetFilesItem);
    return this;
  }

  /**
   * Get datasetFiles
   * @return datasetFiles
   **/
  @Schema(description = "")
      @Valid
    public List<DatasetDirectory> getDatasetFiles() {
    return datasetFiles;
  }

  public void setDatasetFiles(List<DatasetDirectory> datasetFiles) {
    this.datasetFiles = datasetFiles;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatasetDirectories datasetDirectories = (DatasetDirectories) o;
    return Objects.equals(this.datasetFiles, datasetDirectories.datasetFiles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datasetFiles);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatasetDirectories {\n");
    
    sb.append("    datasetFiles: ").append(toIndentedString(datasetFiles)).append("\n");
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
