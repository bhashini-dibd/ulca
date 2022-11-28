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
 * describes the audio quality evaluation method
 */
@Schema(description = "describes the audio quality evaluation method")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-25T09:44:34.039Z[GMT]")


public class AudioQualityEvaluation   {
  /**
   * evaluation method used for the audio
   */
  public enum MethodTypeEnum {
    WADASNR("WadaSnr");

    private String value;

    MethodTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static MethodTypeEnum fromValue(String text) {
      for (MethodTypeEnum b : MethodTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("methodType")
  private MethodTypeEnum methodType = null;

  @JsonProperty("methodDetails")
  private OneOfAudioQualityEvaluationMethodDetails methodDetails = null;

  public AudioQualityEvaluation methodType(MethodTypeEnum methodType) {
    this.methodType = methodType;
    return this;
  }

  /**
   * evaluation method used for the audio
   * @return methodType
   **/
  @Schema(description = "evaluation method used for the audio")
  
    public MethodTypeEnum getMethodType() {
    return methodType;
  }

  public void setMethodType(MethodTypeEnum methodType) {
    this.methodType = methodType;
  }

  public AudioQualityEvaluation methodDetails(OneOfAudioQualityEvaluationMethodDetails methodDetails) {
    this.methodDetails = methodDetails;
    return this;
  }

  /**
   * Get methodDetails
   * @return methodDetails
   **/
  @Schema(description = "")
  
    public OneOfAudioQualityEvaluationMethodDetails getMethodDetails() {
    return methodDetails;
  }

  public void setMethodDetails(OneOfAudioQualityEvaluationMethodDetails methodDetails) {
    this.methodDetails = methodDetails;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AudioQualityEvaluation audioQualityEvaluation = (AudioQualityEvaluation) o;
    return Objects.equals(this.methodType, audioQualityEvaluation.methodType) &&
        Objects.equals(this.methodDetails, audioQualityEvaluation.methodDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(methodType, methodDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AudioQualityEvaluation {\n");
    
    sb.append("    methodType: ").append(toIndentedString(methodType)).append("\n");
    sb.append("    methodDetails: ").append(toIndentedString(methodDetails)).append("\n");
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
