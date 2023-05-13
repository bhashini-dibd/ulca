package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.DatasetCommonParamsSchema;
import io.swagger.model.DatasetType;
import io.swagger.model.Domain;
import io.swagger.model.LanguagePair;
import io.swagger.model.License;
import io.swagger.model.ParallelDatasetCollectionMethod;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ParallelDatasetParamsSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-02-11T16:18:51.317347392Z[GMT]")


public class ParallelDatasetParamsSchema extends DatasetCommonParamsSchema implements OneOfDatasetParamsSchemaParams {
  @JsonProperty("collectionMethod")
  private ParallelDatasetCollectionMethod collectionMethod = null;

  public ParallelDatasetParamsSchema collectionMethod(ParallelDatasetCollectionMethod collectionMethod) {
    this.collectionMethod = collectionMethod;
    return this;
  }

  /**
   * Get collectionMethod
   * @return collectionMethod
   **/
  @Schema(description = "")
  
    @Valid
    public ParallelDatasetCollectionMethod getCollectionMethod() {
    return collectionMethod;
  }

  public void setCollectionMethod(ParallelDatasetCollectionMethod collectionMethod) {
    this.collectionMethod = collectionMethod;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParallelDatasetParamsSchema parallelDatasetParamsSchema = (ParallelDatasetParamsSchema) o;
    return Objects.equals(this.collectionMethod, parallelDatasetParamsSchema.collectionMethod) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(collectionMethod, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ParallelDatasetParamsSchema {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    collectionMethod: ").append(toIndentedString(collectionMethod)).append("\n");
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
