package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.AsrCommonSchema;
import io.swagger.model.AsrlanguagesSpoken;
import io.swagger.model.AudioBitsPerSample;
import io.swagger.model.AudioChannel;
import io.swagger.model.AudioQualityEvaluation;
import io.swagger.model.CollectionMethodAudio;
import io.swagger.model.Gender;
import io.swagger.model.Source;
import io.swagger.model.SupportedLanguages;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the schema defines the column name present in physical file that is being pointed by dataFilename key.
 */
@Schema(description = "the schema defines the column name present in physical file that is being pointed by dataFilename key.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-02-11T16:18:51.317347392Z[GMT]")


public class AsrRowSchema extends AsrCommonSchema implements OneOfDatasetDataRowSchemaData {
  @JsonProperty("text")
  private String text = null;

  public AsrRowSchema text(String text) {
    this.text = text;
    return this;
  }

  /**
   * textual output of the audio
   * @return text
   **/
  @Schema(required = true, description = "textual output of the audio")
      @NotNull

    public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AsrRowSchema asrRowSchema = (AsrRowSchema) o;
    return Objects.equals(this.text, asrRowSchema.text) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AsrRowSchema {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
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
