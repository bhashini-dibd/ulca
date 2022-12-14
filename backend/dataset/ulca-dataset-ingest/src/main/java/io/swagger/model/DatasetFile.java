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
 * represent the physical file attributes
 */
@Schema(description = "represent the physical file attributes")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-25T09:44:34.039Z[GMT]")


public class DatasetFile   {
  /**
   * name of the file
   */
  public enum FilenameEnum {
    DATA("data"),
    
    PARAMS("params");

    private String value;

    FilenameEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static FilenameEnum fromValue(String text) {
      for (FilenameEnum b : FilenameEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("filename")
  private FilenameEnum filename = null;

  /**
   * file format
   */
  public enum FileTypeEnum {
    JSON("json"),
    
    CSV("csv");

    private String value;

    FileTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static FileTypeEnum fromValue(String text) {
      for (FileTypeEnum b : FileTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("fileType")
  private FileTypeEnum fileType = null;

  public DatasetFile filename(FilenameEnum filename) {
    this.filename = filename;
    return this;
  }

  /**
   * name of the file
   * @return filename
   **/
  @Schema(required = true, description = "name of the file")
      @NotNull

    public FilenameEnum getFilename() {
    return filename;
  }

  public void setFilename(FilenameEnum filename) {
    this.filename = filename;
  }

  public DatasetFile fileType(FileTypeEnum fileType) {
    this.fileType = fileType;
    return this;
  }

  /**
   * file format
   * @return fileType
   **/
  @Schema(required = true, description = "file format")
      @NotNull

    public FileTypeEnum getFileType() {
    return fileType;
  }

  public void setFileType(FileTypeEnum fileType) {
    this.fileType = fileType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatasetFile datasetFile = (DatasetFile) o;
    return Objects.equals(this.filename, datasetFile.filename) &&
        Objects.equals(this.fileType, datasetFile.fileType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filename, fileType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatasetFile {\n");
    
    sb.append("    filename: ").append(toIndentedString(filename)).append("\n");
    sb.append("    fileType: ").append(toIndentedString(fileType)).append("\n");
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
