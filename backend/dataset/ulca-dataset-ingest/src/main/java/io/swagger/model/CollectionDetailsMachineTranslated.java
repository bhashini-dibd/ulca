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
 * machine translated collection details
 */
@Schema(description = "machine translated collection details")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-02-11T16:18:51.317347392Z[GMT]")


public class CollectionDetailsMachineTranslated  implements OneOfParallelDatasetCollectionMethodCollectionDetails {
  @JsonProperty("translationModel")
  private String translationModel = null;

  @JsonProperty("translationModelVersion")
  private String translationModelVersion = null;

  @JsonProperty("evaluationMethod")
  private OneOfCollectionDetailsMachineTranslatedEvaluationMethod evaluationMethod = null;

  /**
   * manual evaluation strategy adopted
   */
  public enum EvaluationMethodTypeEnum {
    MTAUTOMATICEVALUATIONMETHOD("MTAutomaticEvaluationMethod"),
    
    MACHINETRANSLATEDEVALUATIONMETHOD2("MachineTranslatedEvaluationMethod2");

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

  public CollectionDetailsMachineTranslated translationModel(String translationModel) {
    this.translationModel = translationModel;
    return this;
  }

  /**
   * name of the translation model/engine used
   * @return translationModel
   **/
  @Schema(example = "google translation", required = true, description = "name of the translation model/engine used")
      @NotNull

    public String getTranslationModel() {
    return translationModel;
  }

  public void setTranslationModel(String translationModel) {
    this.translationModel = translationModel;
  }

  public CollectionDetailsMachineTranslated translationModelVersion(String translationModelVersion) {
    this.translationModelVersion = translationModelVersion;
    return this;
  }

  /**
   * translation model/engine version
   * @return translationModelVersion
   **/
  @Schema(description = "translation model/engine version")
  
    public String getTranslationModelVersion() {
    return translationModelVersion;
  }

  public void setTranslationModelVersion(String translationModelVersion) {
    this.translationModelVersion = translationModelVersion;
  }

  public CollectionDetailsMachineTranslated evaluationMethod(OneOfCollectionDetailsMachineTranslatedEvaluationMethod evaluationMethod) {
    this.evaluationMethod = evaluationMethod;
    return this;
  }

  /**
   * Get evaluationMethod
   * @return evaluationMethod
   **/
  @Schema(description = "")
  
    public OneOfCollectionDetailsMachineTranslatedEvaluationMethod getEvaluationMethod() {
    return evaluationMethod;
  }

  public void setEvaluationMethod(OneOfCollectionDetailsMachineTranslatedEvaluationMethod evaluationMethod) {
    this.evaluationMethod = evaluationMethod;
  }

  public CollectionDetailsMachineTranslated evaluationMethodType(EvaluationMethodTypeEnum evaluationMethodType) {
    this.evaluationMethodType = evaluationMethodType;
    return this;
  }

  /**
   * manual evaluation strategy adopted
   * @return evaluationMethodType
   **/
  @Schema(example = "MTAutomaticEvaluationMethod", description = "manual evaluation strategy adopted")
  
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
    CollectionDetailsMachineTranslated collectionDetailsMachineTranslated = (CollectionDetailsMachineTranslated) o;
    return Objects.equals(this.translationModel, collectionDetailsMachineTranslated.translationModel) &&
        Objects.equals(this.translationModelVersion, collectionDetailsMachineTranslated.translationModelVersion) &&
        Objects.equals(this.evaluationMethod, collectionDetailsMachineTranslated.evaluationMethod) &&
        Objects.equals(this.evaluationMethodType, collectionDetailsMachineTranslated.evaluationMethodType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(translationModel, translationModelVersion, evaluationMethod, evaluationMethodType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionDetailsMachineTranslated {\n");
    
    sb.append("    translationModel: ").append(toIndentedString(translationModel)).append("\n");
    sb.append("    translationModelVersion: ").append(toIndentedString(translationModelVersion)).append("\n");
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
