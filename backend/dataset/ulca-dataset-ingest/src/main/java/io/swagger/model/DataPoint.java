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
 * DataPoint
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:21:00.339Z[GMT]")


public class DataPoint   {
  @JsonProperty("label")
  private String label = null;

  @JsonProperty("value")
  private BigDecimal value = null;

  @JsonProperty("internalLabel")
  private String internalLabel = null;

  public DataPoint label(String label) {
    this.label = label;
    return this;
  }

  /**
   * string value to show
   * @return label
   **/
  @Schema(required = true, description = "string value to show")
      @NotNull

    public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public DataPoint value(BigDecimal value) {
    this.value = value;
    return this;
  }

  /**
   * numeric value of the data point
   * @return value
   **/
  @Schema(required = true, description = "numeric value of the data point")
      @NotNull

    @Valid
    public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public DataPoint internalLabel(String internalLabel) {
    this.internalLabel = internalLabel;
    return this;
  }

  /**
   * internal representation of the label
   * @return internalLabel
   **/
  @Schema(description = "internal representation of the label")
  
    public String getInternalLabel() {
    return internalLabel;
  }

  public void setInternalLabel(String internalLabel) {
    this.internalLabel = internalLabel;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DataPoint dataPoint = (DataPoint) o;
    return Objects.equals(this.label, dataPoint.label) &&
        Objects.equals(this.value, dataPoint.value) &&
        Objects.equals(this.internalLabel, dataPoint.internalLabel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, value, internalLabel);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DataPoint {\n");
    
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    internalLabel: ").append(toIndentedString(internalLabel)).append("\n");
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
