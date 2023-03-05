package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PipelineResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T08:00:21.046011704Z[GMT]")

public class PipelineResponse   {
  @JsonProperty("languages")
  private LanguagesList languages = null;

  @JsonProperty("pipelineResponseConfig")
  private TaskSchemaList pipelineResponseConfig = null;

  @JsonProperty("pipelineInferenceAPIEndPoint")
  private PipelineInferenceAPIEndPoint pipelineInferenceAPIEndPoint = null;

  public PipelineResponse languages(LanguagesList languages) {
    this.languages = languages;
    return this;
  }

  /**
   * Get languages
   * @return languages
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public LanguagesList getLanguages() {
    return languages;
  }

  public void setLanguages(LanguagesList languages) {
    this.languages = languages;
  }

  public PipelineResponse pipelineResponseConfig(TaskSchemaList pipelineResponseConfig) {
    this.pipelineResponseConfig = pipelineResponseConfig;
    return this;
  }

  /**
   * Get pipelineResponseConfig
   * @return pipelineResponseConfig
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public TaskSchemaList getPipelineResponseConfig() {
    return pipelineResponseConfig;
  }

  public void setPipelineResponseConfig(TaskSchemaList pipelineResponseConfig) {
    this.pipelineResponseConfig = pipelineResponseConfig;
  }

  public PipelineResponse pipelineInferenceAPIEndPoint(PipelineInferenceAPIEndPoint pipelineInferenceAPIEndPoint) {
    this.pipelineInferenceAPIEndPoint = pipelineInferenceAPIEndPoint;
    return this;
  }

  /**
   * Get pipelineInferenceAPIEndPoint
   * @return pipelineInferenceAPIEndPoint
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public PipelineInferenceAPIEndPoint getPipelineInferenceAPIEndPoint() {
    return pipelineInferenceAPIEndPoint;
  }

  public void setPipelineInferenceAPIEndPoint(PipelineInferenceAPIEndPoint pipelineInferenceAPIEndPoint) {
    this.pipelineInferenceAPIEndPoint = pipelineInferenceAPIEndPoint;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PipelineResponse pipelineResponse = (PipelineResponse) o;
    return Objects.equals(this.languages, pipelineResponse.languages) &&
        Objects.equals(this.pipelineResponseConfig, pipelineResponse.pipelineResponseConfig) &&
        Objects.equals(this.pipelineInferenceAPIEndPoint, pipelineResponse.pipelineInferenceAPIEndPoint);
  }

  @Override
  public int hashCode() {
    return Objects.hash(languages, pipelineResponseConfig, pipelineInferenceAPIEndPoint);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PipelineResponse {\n");
    
    sb.append("    languages: ").append(toIndentedString(languages)).append("\n");
    sb.append("    pipelineResponseConfig: ").append(toIndentedString(pipelineResponseConfig)).append("\n");
    sb.append("    pipelineInferenceAPIEndPoint: ").append(toIndentedString(pipelineInferenceAPIEndPoint)).append("\n");
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
