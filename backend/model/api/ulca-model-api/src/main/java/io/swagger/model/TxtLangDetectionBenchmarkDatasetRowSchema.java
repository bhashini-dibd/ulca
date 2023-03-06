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


public class TxtLangDetectionBenchmarkDatasetRowSchema  implements OneOfDatasetDataRowSchemaData {
  @JsonProperty("sourceText")
  private String sourceText = null;

  @JsonProperty("langCode")
  private String langCode = null;

  public TxtLangDetectionBenchmarkDatasetRowSchema sourceText(String sourceText) {
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

  public TxtLangDetectionBenchmarkDatasetRowSchema langCode(String langCode) {
    this.langCode = langCode;
    return this;
  }

  /**
   * Indic language code, iso-639-1, iso 639-2
   * @return langCode
   **/
  @Schema(required = true, description = "Indic language code, iso-639-1, iso 639-2")
      @NotNull

    public String getLangCode() {
    return langCode;
  }

  public void setLangCode(String langCode) {
    this.langCode = langCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TxtLangDetectionBenchmarkDatasetRowSchema txtLangDetectionBenchmarkDatasetRowSchema = (TxtLangDetectionBenchmarkDatasetRowSchema) o;
    return Objects.equals(this.sourceText, txtLangDetectionBenchmarkDatasetRowSchema.sourceText) &&
        Objects.equals(this.langCode, txtLangDetectionBenchmarkDatasetRowSchema.langCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sourceText, langCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TxtLangDetectionBenchmarkDatasetRowSchema {\n");
    
    sb.append("    sourceText: ").append(toIndentedString(sourceText)).append("\n");
    sb.append("    langCode: ").append(toIndentedString(langCode)).append("\n");
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
