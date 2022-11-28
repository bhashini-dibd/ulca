package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.NerDataArray;
import io.swagger.model.NerDatasetCollectionMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the schema of data file uploaded should adhere to this specified structure.
 */
@Schema(description = "the schema of data file uploaded should adhere to this specified structure.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-25T09:44:34.039Z[GMT]")


public class NerDatasetRowSchema  implements OneOfDatasetDataRowSchemaData {
  @JsonProperty("sourceText")
  private String sourceText = null;

  @JsonProperty("nerData")
  private NerDataArray nerData = null;

  @JsonProperty("collectionMethod")
  private NerDatasetCollectionMethod collectionMethod = null;

  public NerDatasetRowSchema sourceText(String sourceText) {
    this.sourceText = sourceText;
    return this;
  }

  /**
   * textual data in source language
   * @return sourceText
   **/
  @Schema(description = "textual data in source language")
  
    public String getSourceText() {
    return sourceText;
  }

  public void setSourceText(String sourceText) {
    this.sourceText = sourceText;
  }

  public NerDatasetRowSchema nerData(NerDataArray nerData) {
    this.nerData = nerData;
    return this;
  }

  /**
   * Get nerData
   * @return nerData
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public NerDataArray getNerData() {
    return nerData;
  }

  public void setNerData(NerDataArray nerData) {
    this.nerData = nerData;
  }

  public NerDatasetRowSchema collectionMethod(NerDatasetCollectionMethod collectionMethod) {
    this.collectionMethod = collectionMethod;
    return this;
  }

  /**
   * Get collectionMethod
   * @return collectionMethod
   **/
  @Schema(description = "")
  
    @Valid
    public NerDatasetCollectionMethod getCollectionMethod() {
    return collectionMethod;
  }

  public void setCollectionMethod(NerDatasetCollectionMethod collectionMethod) {
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
    NerDatasetRowSchema nerDatasetRowSchema = (NerDatasetRowSchema) o;
    return Objects.equals(this.sourceText, nerDatasetRowSchema.sourceText) &&
        Objects.equals(this.nerData, nerDatasetRowSchema.nerData) &&
        Objects.equals(this.collectionMethod, nerDatasetRowSchema.collectionMethod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sourceText, nerData, collectionMethod);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NerDatasetRowSchema {\n");
    
    sb.append("    sourceText: ").append(toIndentedString(sourceText)).append("\n");
    sb.append("    nerData: ").append(toIndentedString(nerData)).append("\n");
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
