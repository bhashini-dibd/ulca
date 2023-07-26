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
 * the identifier object retuned when compressed dataset file is uploaded. https://raw.githubusercontent.com/tus/tus-resumable-upload-protocol/master/OpenAPI/openapi3.yaml
 */
@Schema(description = "the identifier object retuned when compressed dataset file is uploaded. https://raw.githubusercontent.com/tus/tus-resumable-upload-protocol/master/OpenAPI/openapi3.yaml")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T03:59:56.369839514Z[GMT]")


public class DatasetFileIdentifier   {
  @JsonProperty("fileId")
  private String fileId = null;

  /**
   * Gets or Sets datasetFormatType
   */
  public enum DatasetFormatTypeEnum {
    JSON("json"),
    
    CSV("csv");

    private String value;

    DatasetFormatTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DatasetFormatTypeEnum fromValue(String text) {
      for (DatasetFormatTypeEnum b : DatasetFormatTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("datasetFormatType")
  private DatasetFormatTypeEnum datasetFormatType = null;

  @JsonProperty("timestamp")
  private String timestamp = null;

  /**
   * supported file compression format
   */
  public enum FormatEnum {
    TAR_GZ("tar_gz"),
    
    ZIP("zip");

    private String value;

    FormatEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static FormatEnum fromValue(String text) {
      for (FormatEnum b : FormatEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("format")
  private FormatEnum format = null;

  public DatasetFileIdentifier fileId(String fileId) {
    this.fileId = fileId;
    return this;
  }

  /**
   * upload identifier returned by the file upload service
   * @return fileId
   **/
  @Schema(required = true, description = "upload identifier returned by the file upload service")
      @NotNull

    public String getFileId() {
    return fileId;
  }

  public void setFileId(String fileId) {
    this.fileId = fileId;
  }

  public DatasetFileIdentifier datasetFormatType(DatasetFormatTypeEnum datasetFormatType) {
    this.datasetFormatType = datasetFormatType;
    return this;
  }

  /**
   * Get datasetFormatType
   * @return datasetFormatType
   **/
  @Schema(required = true, description = "")
      @NotNull

    public DatasetFormatTypeEnum getDatasetFormatType() {
    return datasetFormatType;
  }

  public void setDatasetFormatType(DatasetFormatTypeEnum datasetFormatType) {
    this.datasetFormatType = datasetFormatType;
  }

  public DatasetFileIdentifier timestamp(String timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * upload completed timestamp
   * @return timestamp
   **/
  @Schema(description = "upload completed timestamp")
  
    public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public DatasetFileIdentifier format(FormatEnum format) {
    this.format = format;
    return this;
  }

  /**
   * supported file compression format
   * @return format
   **/
  @Schema(description = "supported file compression format")
  
    public FormatEnum getFormat() {
    return format;
  }

  public void setFormat(FormatEnum format) {
    this.format = format;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatasetFileIdentifier datasetFileIdentifier = (DatasetFileIdentifier) o;
    return Objects.equals(this.fileId, datasetFileIdentifier.fileId) &&
        Objects.equals(this.datasetFormatType, datasetFileIdentifier.datasetFormatType) &&
        Objects.equals(this.timestamp, datasetFileIdentifier.timestamp) &&
        Objects.equals(this.format, datasetFileIdentifier.format);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fileId, datasetFormatType, timestamp, format);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatasetFileIdentifier {\n");
    
    sb.append("    fileId: ").append(toIndentedString(fileId)).append("\n");
    sb.append("    datasetFormatType: ").append(toIndentedString(datasetFormatType)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    format: ").append(toIndentedString(format)).append("\n");
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
