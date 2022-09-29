package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * machine translated collection details
 */
@Schema(description = "machine translated collection details")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-09T08:20:20.072Z[GMT]")

@JsonDeserialize(as = CollectionDetailsMachineGeneratedTranscript.class)
public class CollectionDetailsMachineGeneratedTranscript  implements OneOfCollectionMethodAudioCollectionDetails {
  @JsonProperty("asrModel")
  private String asrModel = null;

  @JsonProperty("asrModelVersion")
  private String asrModelVersion = null;

  @JsonProperty("evaluationMethod")
  private OneOfCollectionDetailsMachineGeneratedTranscriptEvaluationMethod evaluationMethod = null;

  /**
   * manual evaluation strategy adopted
   */
  public enum EvaluationMethodTypeEnum {
    TRANSCRIPTIONEVALUATIONMETHOD1("TranscriptionEvaluationMethod1");

    private String value;

    EvaluationMethodTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static EvaluationMethodTypeEnum fromValue(String text) {
      for (EvaluationMethodTypeEnum b : EvaluationMethodTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("evaluationMethodType")
  private EvaluationMethodTypeEnum evaluationMethodType = null;

  public CollectionDetailsMachineGeneratedTranscript asrModel(String asrModel) {
    this.asrModel = asrModel;
    return this;
  }

  /**
   * name of the asr model/engine used
   * @return asrModel
   **/
  @Schema(example = "google asr", description = "name of the asr model/engine used")
  
    public String getAsrModel() {
    return asrModel;
  }

  public void setAsrModel(String asrModel) {
    this.asrModel = asrModel;
  }

  public CollectionDetailsMachineGeneratedTranscript asrModelVersion(String asrModelVersion) {
    this.asrModelVersion = asrModelVersion;
    return this;
  }

  /**
   * asr model/engine version
   * @return asrModelVersion
   **/
  @Schema(description = "asr model/engine version")
  
    public String getAsrModelVersion() {
    return asrModelVersion;
  }

  public void setAsrModelVersion(String asrModelVersion) {
    this.asrModelVersion = asrModelVersion;
  }

  public CollectionDetailsMachineGeneratedTranscript evaluationMethod(OneOfCollectionDetailsMachineGeneratedTranscriptEvaluationMethod evaluationMethod) {
    this.evaluationMethod = evaluationMethod;
    return this;
  }

  /**
   * Get evaluationMethod
   * @return evaluationMethod
   **/
  @Schema(description = "")
  
    public OneOfCollectionDetailsMachineGeneratedTranscriptEvaluationMethod getEvaluationMethod() {
    return evaluationMethod;
  }

  public void setEvaluationMethod(OneOfCollectionDetailsMachineGeneratedTranscriptEvaluationMethod evaluationMethod) {
    this.evaluationMethod = evaluationMethod;
  }

  public CollectionDetailsMachineGeneratedTranscript evaluationMethodType(EvaluationMethodTypeEnum evaluationMethodType) {
    this.evaluationMethodType = evaluationMethodType;
    return this;
  }

  /**
   * manual evaluation strategy adopted
   * @return evaluationMethodType
   **/
  @Schema(example = "TranscriptionEvaluationMethod1", description = "manual evaluation strategy adopted")
  
    public EvaluationMethodTypeEnum getEvaluationMethodType() {
    return evaluationMethodType;
  }

  public void setEvaluationMethodType(EvaluationMethodTypeEnum evaluationMethodType) {
    this.evaluationMethodType = evaluationMethodType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CollectionDetailsMachineGeneratedTranscript collectionDetailsMachineGeneratedTranscript = (CollectionDetailsMachineGeneratedTranscript) o;
    return Objects.equals(this.asrModel, collectionDetailsMachineGeneratedTranscript.asrModel) &&
        Objects.equals(this.asrModelVersion, collectionDetailsMachineGeneratedTranscript.asrModelVersion) &&
        Objects.equals(this.evaluationMethod, collectionDetailsMachineGeneratedTranscript.evaluationMethod) &&
        Objects.equals(this.evaluationMethodType, collectionDetailsMachineGeneratedTranscript.evaluationMethodType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(asrModel, asrModelVersion, evaluationMethod, evaluationMethodType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionDetailsMachineGeneratedTranscript {\n");
    
    sb.append("    asrModel: ").append(toIndentedString(asrModel)).append("\n");
    sb.append("    asrModelVersion: ").append(toIndentedString(asrModelVersion)).append("\n");
    sb.append("    evaluationMethod: ").append(toIndentedString(evaluationMethod)).append("\n");
    sb.append("    evaluationMethodType: ").append(toIndentedString(evaluationMethodType)).append("\n");
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
