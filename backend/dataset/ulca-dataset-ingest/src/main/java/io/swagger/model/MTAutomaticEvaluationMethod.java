package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * evaluation strategy as proposed by https://www.researchgate.net/publication/273475626_Adequacy-Fluency_Metrics_Evaluating_MT_in_the_Continuous_Space_Model_Framework
 */
@Schema(description = "evaluation strategy as proposed by https://www.researchgate.net/publication/273475626_Adequacy-Fluency_Metrics_Evaluating_MT_in_the_Continuous_Space_Model_Framework")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-02-11T16:18:51.317347392Z[GMT]")


public class MTAutomaticEvaluationMethod  implements OneOfCollectionDetailsMachineTranslatedEvaluationMethod, OneOfCollectionDetailsMachineTransliteratedEvaluationMethod {
  @JsonProperty("adequacy")
  private BigDecimal adequacy = null;

  @JsonProperty("fluency")
  private BigDecimal fluency = null;

  public MTAutomaticEvaluationMethod adequacy(BigDecimal adequacy) {
    this.adequacy = adequacy;
    return this;
  }

  /**
   * Adequacy is a rating of how much information is transferred between the original and the translation
   * minimum: 1
   * maximum: 5
   * @return adequacy
   **/
  @Schema(required = true, description = "Adequacy is a rating of how much information is transferred between the original and the translation")
      @NotNull

    @Valid
  @DecimalMin("1") @DecimalMax("5")   public BigDecimal getAdequacy() {
    return adequacy;
  }

  public void setAdequacy(BigDecimal adequacy) {
    this.adequacy = adequacy;
  }

  public MTAutomaticEvaluationMethod fluency(BigDecimal fluency) {
    this.fluency = fluency;
    return this;
  }

  /**
   * fluency is a rating of how good the translation is
   * minimum: 1
   * maximum: 5
   * @return fluency
   **/
  @Schema(required = true, description = "fluency is a rating of how good the translation is")
      @NotNull

    @Valid
  @DecimalMin("1") @DecimalMax("5")   public BigDecimal getFluency() {
    return fluency;
  }

  public void setFluency(BigDecimal fluency) {
    this.fluency = fluency;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MTAutomaticEvaluationMethod mtAutomaticEvaluationMethod = (MTAutomaticEvaluationMethod) o;
    return Objects.equals(this.adequacy, mtAutomaticEvaluationMethod.adequacy) &&
        Objects.equals(this.fluency, mtAutomaticEvaluationMethod.fluency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(adequacy, fluency);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MTAutomaticEvaluationMethod {\n");
    
    sb.append("    adequacy: ").append(toIndentedString(adequacy)).append("\n");
    sb.append("    fluency: ").append(toIndentedString(fluency)).append("\n");
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
