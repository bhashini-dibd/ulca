package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.DatasetCommonParamsSchema;
import io.swagger.model.DatasetType;
import io.swagger.model.Domain;
import io.swagger.model.LanguagePair;
import io.swagger.model.License;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import io.swagger.model.TransliterationDatasetCollectionMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TransliterationDatasetParamsSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-16T02:11:13.718Z[GMT]")


public class TransliterationDatasetParamsSchema extends DatasetCommonParamsSchema implements OneOfDatasetParamsSchemaParams {
  @JsonProperty("collectionMethod")
  private TransliterationDatasetCollectionMethod collectionMethod = null;

  public TransliterationDatasetParamsSchema collectionMethod(TransliterationDatasetCollectionMethod collectionMethod) {
    this.collectionMethod = collectionMethod;
    return this;
  }

  /**
   * Get collectionMethod
   * @return collectionMethod
   **/
  @Schema(description = "")
  
    @Valid
    public TransliterationDatasetCollectionMethod getCollectionMethod() {
    return collectionMethod;
  }

  public void setCollectionMethod(TransliterationDatasetCollectionMethod collectionMethod) {
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
    TransliterationDatasetParamsSchema transliterationDatasetParamsSchema = (TransliterationDatasetParamsSchema) o;
    return Objects.equals(this.collectionMethod, transliterationDatasetParamsSchema.collectionMethod) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(collectionMethod, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransliterationDatasetParamsSchema {\n");
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
