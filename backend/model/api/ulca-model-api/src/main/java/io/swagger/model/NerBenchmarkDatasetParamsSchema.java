package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.BenchmarkDatasetCommonParamsSchema;
import io.swagger.model.Domain;
import io.swagger.model.License;
import io.swagger.model.ModelTask;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import io.swagger.model.SupportedTagsFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NerBenchmarkDatasetParamsSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T03:59:56.369839514Z[GMT]")


public class NerBenchmarkDatasetParamsSchema extends BenchmarkDatasetCommonParamsSchema implements OneOfDatasetParamsSchemaParams {
  @JsonProperty("tagsFormat")
  private SupportedTagsFormat tagsFormat = null;

  public NerBenchmarkDatasetParamsSchema tagsFormat(SupportedTagsFormat tagsFormat) {
    this.tagsFormat = tagsFormat;
    return this;
  }

  /**
   * Get tagsFormat
   * @return tagsFormat
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public SupportedTagsFormat getTagsFormat() {
    return tagsFormat;
  }

  public void setTagsFormat(SupportedTagsFormat tagsFormat) {
    this.tagsFormat = tagsFormat;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NerBenchmarkDatasetParamsSchema nerBenchmarkDatasetParamsSchema = (NerBenchmarkDatasetParamsSchema) o;
    return Objects.equals(this.tagsFormat, nerBenchmarkDatasetParamsSchema.tagsFormat) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tagsFormat, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NerBenchmarkDatasetParamsSchema {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    tagsFormat: ").append(toIndentedString(tagsFormat)).append("\n");
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
