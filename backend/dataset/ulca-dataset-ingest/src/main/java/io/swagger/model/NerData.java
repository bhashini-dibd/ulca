package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Ner dataset sentencewise metadata
 */
@Schema(description = "Ner dataset sentencewise metadata")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:41:06.560Z[GMT]")


public class NerData  implements AnyOfNerData {
  /**
   * position of the word in phrase. B- Beginning, I- Intermediate
   */
  public enum PositionEnum {
    B("B"),
    
    I("I");

    private String value;

    PositionEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static PositionEnum fromValue(String text) {
      for (PositionEnum b : PositionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("position")
  private PositionEnum position = null;

  /**
   * NER tag for corresponsing word/index. This should be inline with the corresponding tagsFormat specified in params.json.
   */
  public enum TagEnum {
    O("O"),
    
    PER("PER"),
    
    ORG("ORG"),
    
    LOC("LOC");

    private String value;

    TagEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TagEnum fromValue(String text) {
      for (TagEnum b : TagEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("tag")
  private TagEnum tag = null;

  public NerData position(PositionEnum position) {
    this.position = position;
    return this;
  }

  /**
   * position of the word in phrase. B- Beginning, I- Intermediate
   * @return position
   **/
  @Schema(description = "position of the word in phrase. B- Beginning, I- Intermediate")
  
    public PositionEnum getPosition() {
    return position;
  }

  public void setPosition(PositionEnum position) {
    this.position = position;
  }

  public NerData tag(TagEnum tag) {
    this.tag = tag;
    return this;
  }

  /**
   * NER tag for corresponsing word/index. This should be inline with the corresponding tagsFormat specified in params.json.
   * @return tag
   **/
  @Schema(example = "PER", required = true, description = "NER tag for corresponsing word/index. This should be inline with the corresponding tagsFormat specified in params.json.")
      @NotNull

    public TagEnum getTag() {
    return tag;
  }

  public void setTag(TagEnum tag) {
    this.tag = tag;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NerData nerData = (NerData) o;
    return Objects.equals(this.position, nerData.position) &&
        Objects.equals(this.tag, nerData.tag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(position, tag);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NerData {\n");
    
    sb.append("    position: ").append(toIndentedString(position)).append("\n");
    sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
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
