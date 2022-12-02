package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.DatasetCommonParamsSchema;
import io.swagger.model.DatasetType;
import io.swagger.model.Domain;
import io.swagger.model.GlossaryDatasetCollectionMethod;
import io.swagger.model.LanguagePair;
import io.swagger.model.License;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * GlossaryDatasetParamsSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-25T09:44:34.039Z[GMT]")


public class GlossaryDatasetParamsSchema extends DatasetCommonParamsSchema implements OneOfDatasetParamsSchemaParams {
  /**
   * corresponding level of source-target glossary pair. w for word, p for phrase and s for sentence
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

  public GlossaryDatasetParamsSchema level(LevelEnum level) {
    this.level = level;
    return this;
  }

  /**
   * corresponding level of source-target glossary pair. w for word, p for phrase and s for sentence
   * @return level
   **/
  @Schema(description = "corresponding level of source-target glossary pair. w for word, p for phrase and s for sentence")
  
    public LevelEnum getLevel() {
    return level;
  }

  public void setLevel(LevelEnum level) {
    this.level = level;
  }

  public GlossaryDatasetParamsSchema collectionMethod(GlossaryDatasetCollectionMethod collectionMethod) {
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
    GlossaryDatasetParamsSchema glossaryDatasetParamsSchema = (GlossaryDatasetParamsSchema) o;
    return Objects.equals(this.level, glossaryDatasetParamsSchema.level) &&
        Objects.equals(this.collectionMethod, glossaryDatasetParamsSchema.collectionMethod) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(level, collectionMethod, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GlossaryDatasetParamsSchema {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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
