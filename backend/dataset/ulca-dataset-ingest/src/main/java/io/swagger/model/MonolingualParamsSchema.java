package io.swagger.model;

import java.util.Objects;
import io.swagger.model.DatasetCommonParamsSchema;
import io.swagger.model.DatasetType;
import io.swagger.model.Domain;
import io.swagger.model.LanguagePair;
import io.swagger.model.License;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * MonolingualParamsSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-25T09:44:34.039Z[GMT]")


public class MonolingualParamsSchema extends DatasetCommonParamsSchema implements OneOfDatasetParamsSchemaParams {

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MonolingualParamsSchema {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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
