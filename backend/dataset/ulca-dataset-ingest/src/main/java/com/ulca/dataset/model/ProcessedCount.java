package com.ulca.dataset.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Task Tracker entity
 */
@Schema(description = "Task Tracker entity")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-08T12:36:29.236Z[GMT]")


public class ProcessedCount   {
  /**
   * type of process
   */
  public enum TypeEnum {
    SUCCESSFUL("successful"),
    
    FAILED("failed");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("type")
  private TypeEnum type = null;

  /**
   * typeDetails
   */
  public enum TypeDetailsEnum {
    DUPLICATE("duplicate"),
    
    INVALID("invalid"),
    
    INTERNAL_ERROR("internal-error");

    private String value;

    TypeDetailsEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeDetailsEnum fromValue(String text) {
      for (TypeDetailsEnum b : TypeDetailsEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("typeDetails")
  private TypeDetailsEnum typeDetails = null;

  @JsonProperty("count")
  private BigDecimal count = null;

  public ProcessedCount type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * type of process
   * @return type
   **/
  @Schema(description = "type of process")
  
    public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public ProcessedCount typeDetails(TypeDetailsEnum typeDetails) {
    this.typeDetails = typeDetails;
    return this;
  }

  /**
   * typeDetails
   * @return typeDetails
   **/
  @Schema(description = "typeDetails")
  
    public TypeDetailsEnum getTypeDetails() {
    return typeDetails;
  }

  public void setTypeDetails(TypeDetailsEnum typeDetails) {
    this.typeDetails = typeDetails;
  }

  public ProcessedCount count(BigDecimal count) {
    this.count = count;
    return this;
  }

  /**
   * Count of the records falling into this category
   * @return count
   **/
  @Schema(description = "Count of the records falling into this category")
  
    @Valid
    public BigDecimal getCount() {
    return count;
  }

  public void setCount(BigDecimal count) {
    this.count = count;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProcessedCount processedCount = (ProcessedCount) o;
    return Objects.equals(this.type, processedCount.type) &&
        Objects.equals(this.typeDetails, processedCount.typeDetails) &&
        Objects.equals(this.count, processedCount.count);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, typeDetails, count);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProcessedCount {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    typeDetails: ").append(toIndentedString(typeDetails)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
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
