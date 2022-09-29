package io.swagger.model;

import java.util.Objects;
import io.swagger.model.AsrParamsSchema;
import io.swagger.model.AudioBitsPerSample;
import io.swagger.model.AudioChannel;
import io.swagger.model.AudioFormat;
import io.swagger.model.AudioQualityEvaluation;
import io.swagger.model.CollectionMethodAudio;
import io.swagger.model.Gender;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TtsParamsSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-29T16:41:19.402Z[GMT]")


public class TtsParamsSchema extends AsrParamsSchema implements OneOfDatasetParamsSchemaParams {

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
    sb.append("class TtsParamsSchema {\n");
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
