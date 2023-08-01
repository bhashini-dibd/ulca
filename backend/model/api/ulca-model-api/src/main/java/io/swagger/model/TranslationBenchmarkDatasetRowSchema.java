package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the schema of data file uploaded should adhere to this specified structure.
 */
@Schema(description = "the schema of data file uploaded should adhere to this specified structure.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T03:59:56.369839514Z[GMT]")


public class TranslationBenchmarkDatasetRowSchema  implements OneOfDatasetDataRowSchemaData {
  @JsonProperty("sourceText")
  private String sourceText = null;

  @JsonProperty("targetText")
  private String targetText = null;

  public TranslationBenchmarkDatasetRowSchema sourceText(String sourceText) {
    this.sourceText = sourceText;
    return this;
  }

  /**
   * textual data in source language
   * @return sourceText
   **/
  @Schema(required = true, description = "textual data in source language")
      @NotNull

    public String getSourceText() {
    return sourceText;
  }

  public void setSourceText(String sourceText) {
    this.sourceText = sourceText;
  }

  public TranslationBenchmarkDatasetRowSchema targetText(String targetText) {
    this.targetText = targetText;
    return this;
  }

  /**
   * textual data in target language
   * @return targetText
   **/
  @Schema(required = true, description = "textual data in target language")
      @NotNull

    public String getTargetText() {
    return targetText;
  }

  public void setTargetText(String targetText) {
    this.targetText = targetText;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TranslationBenchmarkDatasetRowSchema translationBenchmarkDatasetRowSchema = (TranslationBenchmarkDatasetRowSchema) o;
    return Objects.equals(this.sourceText, translationBenchmarkDatasetRowSchema.sourceText) &&
        Objects.equals(this.targetText, translationBenchmarkDatasetRowSchema.targetText);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sourceText, targetText);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TranslationBenchmarkDatasetRowSchema {\n");
    
    sb.append("    sourceText: ").append(toIndentedString(sourceText)).append("\n");
    sb.append("    targetText: ").append(toIndentedString(targetText)).append("\n");
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
