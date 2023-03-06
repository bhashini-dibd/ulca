package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * training dataset metadata used to train the model
 */
@Schema(description = "training dataset metadata used to train the model")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T03:55:25.562740452Z[GMT]")


public class TrainingDataset   {
  @JsonProperty("datasetId")
  private String datasetId = null;

  @JsonProperty("description")
  private String description = null;

  public TrainingDataset datasetId(String datasetId) {
    this.datasetId = datasetId;
    return this;
  }

  /**
   * dataset identifier that has been exported from ULCA system, passing this information makes your model enriched with further information for the community
   * @return datasetId
   **/
  @Schema(description = "dataset identifier that has been exported from ULCA system, passing this information makes your model enriched with further information for the community")
  
    public String getDatasetId() {
    return datasetId;
  }

  public void setDatasetId(String datasetId) {
    this.datasetId = datasetId;
  }

  public TrainingDataset description(String description) {
    this.description = description;
    return this;
  }

  /**
   * explain your dataset that you have used for training your model
   * @return description
   **/
  @Schema(required = true, description = "explain your dataset that you have used for training your model")
      @NotNull

    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TrainingDataset trainingDataset = (TrainingDataset) o;
    return Objects.equals(this.datasetId, trainingDataset.datasetId) &&
        Objects.equals(this.description, trainingDataset.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datasetId, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TrainingDataset {\n");
    
    sb.append("    datasetId: ").append(toIndentedString(datasetId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
