package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.LanguagePair;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DocumentOCRRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:57:01.789Z[GMT]")


public class DocumentOCRRequest  implements OneOfInferenceAPIEndPointSchema {
  @JsonProperty("fileId")
  private String fileId = null;

  @JsonProperty("language")
  private LanguagePair language = null;

  public DocumentOCRRequest fileId(String fileId) {
    this.fileId = fileId;
    return this;
  }

  /**
   * fileId should be same as returned in the response of file-upload
   * @return fileId
   **/
  @Schema(required = true, description = "fileId should be same as returned in the response of file-upload")
      @NotNull

    public String getFileId() {
    return fileId;
  }

  public void setFileId(String fileId) {
    this.fileId = fileId;
  }

  public DocumentOCRRequest language(LanguagePair language) {
    this.language = language;
    return this;
  }

  /**
   * Get language
   * @return language
   **/
  @Schema(description = "")
  
    @Valid
    public LanguagePair getLanguage() {
    return language;
  }

  public void setLanguage(LanguagePair language) {
    this.language = language;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentOCRRequest documentOCRRequest = (DocumentOCRRequest) o;
    return Objects.equals(this.fileId, documentOCRRequest.fileId) &&
        Objects.equals(this.language, documentOCRRequest.language);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fileId, language);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentOCRRequest {\n");
    
    sb.append("    fileId: ").append(toIndentedString(fileId)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
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
