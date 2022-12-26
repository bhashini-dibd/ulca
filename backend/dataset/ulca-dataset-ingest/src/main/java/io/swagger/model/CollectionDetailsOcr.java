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
 * OCR collection details
 */
@Schema(description = "OCR collection details")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:41:06.560Z[GMT]")


public class CollectionDetailsOcr   {
  /**
   * name of the ocr tool
   */
  public enum OcrToolEnum {
    GOOGLE_CLOUD_VISION("google cloud vision"),
    
    TESSERACT("tesseract"),
    
    ANUVAAD_OCR("anuvaad ocr"),
    
    MICROSOFT_AZURE_COMPUTER_VISION("microsoft azure computer vision"),
    
    CALAMARI("calamari"),
    
    OCROPUS("ocropus"),
    
    AMAZON_TEXTRACT("amazon textract"),
    
    ABBYY_CLOUD_OCR("abbyy cloud ocr"),
    
    UNKNOWN("unknown");

    private String value;

    OcrToolEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static OcrToolEnum fromValue(String text) {
      for (OcrToolEnum b : OcrToolEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("ocrTool")
  private OcrToolEnum ocrTool = null;

  @JsonProperty("ocrToolVersion")
  private String ocrToolVersion = null;

  public CollectionDetailsOcr ocrTool(OcrToolEnum ocrTool) {
    this.ocrTool = ocrTool;
    return this;
  }

  /**
   * name of the ocr tool
   * @return ocrTool
   **/
  @Schema(example = "google cloud vision", required = true, description = "name of the ocr tool")
      @NotNull

    public OcrToolEnum getOcrTool() {
    return ocrTool;
  }

  public void setOcrTool(OcrToolEnum ocrTool) {
    this.ocrTool = ocrTool;
  }

  public CollectionDetailsOcr ocrToolVersion(String ocrToolVersion) {
    this.ocrToolVersion = ocrToolVersion;
    return this;
  }

  /**
   * ocr tool version
   * @return ocrToolVersion
   **/
  @Schema(example = "LaBSE version 3.0", description = "ocr tool version")
  
    public String getOcrToolVersion() {
    return ocrToolVersion;
  }

  public void setOcrToolVersion(String ocrToolVersion) {
    this.ocrToolVersion = ocrToolVersion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CollectionDetailsOcr collectionDetailsOcr = (CollectionDetailsOcr) o;
    return Objects.equals(this.ocrTool, collectionDetailsOcr.ocrTool) &&
        Objects.equals(this.ocrToolVersion, collectionDetailsOcr.ocrToolVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ocrTool, ocrToolVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionDetailsOcr {\n");
    
    sb.append("    ocrTool: ").append(toIndentedString(ocrTool)).append("\n");
    sb.append("    ocrToolVersion: ").append(toIndentedString(ocrToolVersion)).append("\n");
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
