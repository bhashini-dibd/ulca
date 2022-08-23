package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.GlossaryDatasetCollectionMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the schema of data file uploaded should adhere to this specified structure.
 */
@Schema(description = "the schema of data file uploaded should adhere to this specified structure.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-07-06T11:30:23.888Z[GMT]")


public class GlossaryDatasetRowSchema  implements OneOfDatasetDataRowSchemaData {
  @JsonProperty("sourceText")
  private String sourceText = null;

  @JsonProperty("targetText")
  private String targetText = null;

  /**
   * corresponding level of source-target glossary pair. W for word, P for phrase and S for sentence
   */
  public enum LevelEnum {
    W("w"),
    
    P("p"),
    
    S("s");

    private String value;

    LevelEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static LevelEnum fromValue(String text) {
      for (LevelEnum b : LevelEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("level")
  private LevelEnum level = null;

  @JsonProperty("collectionMethod")
  private GlossaryDatasetCollectionMethod collectionMethod = null;

  public GlossaryDatasetRowSchema sourceText(String sourceText) {
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

  public GlossaryDatasetRowSchema targetText(String targetText) {
    this.targetText = targetText;
    return this;
  }

  /**
   * Get targetText
   * @return targetText
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getTargetText() {
    return targetText;
  }

  public void setTargetText(String targetText) {
    this.targetText = targetText;
  }

  public GlossaryDatasetRowSchema level(LevelEnum level) {
    this.level = level;
    return this;
  }

  /**
   * corresponding level of source-target glossary pair. W for word, P for phrase and S for sentence
   * @return level
   **/
  @Schema(description = "corresponding level of source-target glossary pair. W for word, P for phrase and S for sentence")
  
    public LevelEnum getLevel() {
    return level;
  }

  public void setLevel(LevelEnum level) {
    this.level = level;
  }

  public GlossaryDatasetRowSchema collectionMethod(GlossaryDatasetCollectionMethod collectionMethod) {
    this.collectionMethod = collectionMethod;
    return this;
  }

  /**
   * Get collectionMethod
   * @return collectionMethod
   **/
  @Schema(description = "")
  
    @Valid
    public GlossaryDatasetCollectionMethod getCollectionMethod() {
    return collectionMethod;
  }

  public void setCollectionMethod(GlossaryDatasetCollectionMethod collectionMethod) {
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
    GlossaryDatasetRowSchema glossaryDatasetRowSchema = (GlossaryDatasetRowSchema) o;
    return Objects.equals(this.sourceText, glossaryDatasetRowSchema.sourceText) &&
        Objects.equals(this.targetText, glossaryDatasetRowSchema.targetText) &&
        Objects.equals(this.level, glossaryDatasetRowSchema.level) &&
        Objects.equals(this.collectionMethod, glossaryDatasetRowSchema.collectionMethod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sourceText, targetText, level, collectionMethod);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GlossaryDatasetRowSchema {\n");
    
    sb.append("    sourceText: ").append(toIndentedString(sourceText)).append("\n");
    sb.append("    targetText: ").append(toIndentedString(targetText)).append("\n");
    sb.append("    level: ").append(toIndentedString(level)).append("\n");
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
