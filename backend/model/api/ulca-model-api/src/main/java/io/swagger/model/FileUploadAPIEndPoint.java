package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * hosted location of the file which would be sent for the validation of the Inference API endpoint.
 */
@Schema(description = "hosted location of the file which would be sent for the validation of the Inference API endpoint.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T03:55:25.562740452Z[GMT]")


public class FileUploadAPIEndPoint   {
  @JsonProperty("callbackUrl")
  private String callbackUrl = null;

  @JsonProperty("schema")
  private OneOfFileUploadAPIEndPointSchema schema = null;

  public FileUploadAPIEndPoint callbackUrl(String callbackUrl) {
    this.callbackUrl = callbackUrl;
    return this;
  }

  /**
   * Get callbackUrl
   * @return callbackUrl
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getCallbackUrl() {
    return callbackUrl;
  }

  public void setCallbackUrl(String callbackUrl) {
    this.callbackUrl = callbackUrl;
  }

  public FileUploadAPIEndPoint schema(OneOfFileUploadAPIEndPointSchema schema) {
    this.schema = schema;
    return this;
  }

  /**
   * Get schema
   * @return schema
   **/
  @Schema(required = true, description = "")
      @NotNull

    public OneOfFileUploadAPIEndPointSchema getSchema() {
    return schema;
  }

  public void setSchema(OneOfFileUploadAPIEndPointSchema schema) {
    this.schema = schema;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileUploadAPIEndPoint fileUploadAPIEndPoint = (FileUploadAPIEndPoint) o;
    return Objects.equals(this.callbackUrl, fileUploadAPIEndPoint.callbackUrl) &&
        Objects.equals(this.schema, fileUploadAPIEndPoint.schema);
  }

  @Override
  public int hashCode() {
    return Objects.hash(callbackUrl, schema);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FileUploadAPIEndPoint {\n");
    
    sb.append("    callbackUrl: ").append(toIndentedString(callbackUrl)).append("\n");
    sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
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
