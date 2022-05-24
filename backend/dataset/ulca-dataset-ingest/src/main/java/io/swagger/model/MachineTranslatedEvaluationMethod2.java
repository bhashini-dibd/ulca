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
 * evaluation strategy as proposed by example researcher.
 */
@Schema(description = "evaluation strategy as proposed by example researcher.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-16T02:11:13.718Z[GMT]")


public class MachineTranslatedEvaluationMethod2  implements OneOfCollectionDetailsMachineTranslatedEvaluationMethod, OneOfCollectionDetailsMachineTransliteratedEvaluationMethod {
  @JsonProperty("unknownParameter1")
  private BigDecimal unknownParameter1 = null;

  @JsonProperty("unknownParameter2")
  private BigDecimal unknownParameter2 = null;

  @JsonProperty("unknownParameter3")
  private BigDecimal unknownParameter3 = null;

  public MachineTranslatedEvaluationMethod2 unknownParameter1(BigDecimal unknownParameter1) {
    this.unknownParameter1 = unknownParameter1;
    return this;
  }

  /**
   * researcher has defined this parameter
   * @return unknownParameter1
   **/
  @Schema(description = "researcher has defined this parameter")
  
    @Valid
    public BigDecimal getUnknownParameter1() {
    return unknownParameter1;
  }

  public void setUnknownParameter1(BigDecimal unknownParameter1) {
    this.unknownParameter1 = unknownParameter1;
  }

  public MachineTranslatedEvaluationMethod2 unknownParameter2(BigDecimal unknownParameter2) {
    this.unknownParameter2 = unknownParameter2;
    return this;
  }

  /**
   * researcher has defined this parameter
   * @return unknownParameter2
   **/
  @Schema(description = "researcher has defined this parameter")
  
    @Valid
    public BigDecimal getUnknownParameter2() {
    return unknownParameter2;
  }

  public void setUnknownParameter2(BigDecimal unknownParameter2) {
    this.unknownParameter2 = unknownParameter2;
  }

  public MachineTranslatedEvaluationMethod2 unknownParameter3(BigDecimal unknownParameter3) {
    this.unknownParameter3 = unknownParameter3;
    return this;
  }

  /**
   * researcher has defined this parameter
   * @return unknownParameter3
   **/
  @Schema(description = "researcher has defined this parameter")
  
    @Valid
    public BigDecimal getUnknownParameter3() {
    return unknownParameter3;
  }

  public void setUnknownParameter3(BigDecimal unknownParameter3) {
    this.unknownParameter3 = unknownParameter3;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MachineTranslatedEvaluationMethod2 machineTranslatedEvaluationMethod2 = (MachineTranslatedEvaluationMethod2) o;
    return Objects.equals(this.unknownParameter1, machineTranslatedEvaluationMethod2.unknownParameter1) &&
        Objects.equals(this.unknownParameter2, machineTranslatedEvaluationMethod2.unknownParameter2) &&
        Objects.equals(this.unknownParameter3, machineTranslatedEvaluationMethod2.unknownParameter3);
  }

  @Override
  public int hashCode() {
    return Objects.hash(unknownParameter1, unknownParameter2, unknownParameter3);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MachineTranslatedEvaluationMethod2 {\n");
    
    sb.append("    unknownParameter1: ").append(toIndentedString(unknownParameter1)).append("\n");
    sb.append("    unknownParameter2: ").append(toIndentedString(unknownParameter2)).append("\n");
    sb.append("    unknownParameter3: ").append(toIndentedString(unknownParameter3)).append("\n");
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
