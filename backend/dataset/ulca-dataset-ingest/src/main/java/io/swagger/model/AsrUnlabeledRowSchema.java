package io.swagger.model;

import java.util.Objects;
import io.swagger.model.AsrCommonSchema;
import io.swagger.model.AsrlanguagesSpoken;
import io.swagger.model.AudioBitsPerSample;
import io.swagger.model.AudioChannel;
import io.swagger.model.AudioQualityEvaluation;
import io.swagger.model.CollectionMethodAudio;
import io.swagger.model.Gender;
import io.swagger.model.Source;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-09T11:49:54.138Z[GMT]")


public class AsrUnlabeledRowSchema extends AsrCommonSchema implements OneOfDatasetDataRowSchemaData {

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AsrUnlabeledRowSchema {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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
