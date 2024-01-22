package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.BoundingBox;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the schema defines the document layout datasets.
 */
@Schema(description = "the schema defines the document layout datasets.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-02-11T16:18:51.317347392Z[GMT]")


public class DocumentLayoutRowSchema  implements OneOfDatasetDataRowSchemaData {
  @JsonProperty("imageFilename")
  private String imageFilename = null;

  /**
   * Gets or Sets layoutClass
   */
  public enum LayoutClassEnum {
    PARAGRAPH("paragraph"),
    
    LINE("line"),
    
    WORD("word"),
    
    SIGNATUREIMAGE("signatureImage");

    private String value;

    LayoutClassEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static LayoutClassEnum fromValue(String text) {
      for (LayoutClassEnum b : LayoutClassEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("layoutClass")
  private LayoutClassEnum layoutClass = null;

  @JsonProperty("boundingBox")
  private BoundingBox boundingBox = null;

  public DocumentLayoutRowSchema imageFilename(String imageFilename) {
    this.imageFilename = imageFilename;
    return this;
  }

  /**
   * filename of the image file
   * @return imageFilename
   **/
  @Schema(required = true, description = "filename of the image file")
      @NotNull

    public String getImageFilename() {
    return imageFilename;
  }

  public void setImageFilename(String imageFilename) {
    this.imageFilename = imageFilename;
  }

  public DocumentLayoutRowSchema layoutClass(LayoutClassEnum layoutClass) {
    this.layoutClass = layoutClass;
    return this;
  }

  /**
   * Get layoutClass
   * @return layoutClass
   **/
  @Schema(required = true, description = "")
      @NotNull

    public LayoutClassEnum getLayoutClass() {
    return layoutClass;
  }

  public void setLayoutClass(LayoutClassEnum layoutClass) {
    this.layoutClass = layoutClass;
  }

  public DocumentLayoutRowSchema boundingBox(BoundingBox boundingBox) {
    this.boundingBox = boundingBox;
    return this;
  }

  /**
   * Get boundingBox
   * @return boundingBox
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public BoundingBox getBoundingBox() {
    return boundingBox;
  }

  public void setBoundingBox(BoundingBox boundingBox) {
    this.boundingBox = boundingBox;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentLayoutRowSchema documentLayoutRowSchema = (DocumentLayoutRowSchema) o;
    return Objects.equals(this.imageFilename, documentLayoutRowSchema.imageFilename) &&
        Objects.equals(this.layoutClass, documentLayoutRowSchema.layoutClass) &&
        Objects.equals(this.boundingBox, documentLayoutRowSchema.boundingBox);
  }

  @Override
  public int hashCode() {
    return Objects.hash(imageFilename, layoutClass, boundingBox);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentLayoutRowSchema {\n");
    
    sb.append("    imageFilename: ").append(toIndentedString(imageFilename)).append("\n");
    sb.append("    layoutClass: ").append(toIndentedString(layoutClass)).append("\n");
    sb.append("    boundingBox: ").append(toIndentedString(boundingBox)).append("\n");
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
