package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * GenderPrediction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-06-12T11:10:30.623150359Z[GMT]")


public class GenderPrediction   {
  @JsonProperty("gender")
  private Gender gender = null;

  @JsonProperty("genderScore")
  private BigDecimal genderScore = null;

  public GenderPrediction gender(Gender gender) {
    this.gender = gender;
    return this;
  }

  /**
   * Get gender
   * @return gender
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public GenderPrediction genderScore(BigDecimal genderScore) {
    this.genderScore = genderScore;
    return this;
  }

  /**
   * Score of gender specific.
   * @return genderScore
   **/
  @Schema(required = true, description = "Score of gender specific.")
      @NotNull

    @Valid
    public BigDecimal getGenderScore() {
    return genderScore;
  }

  public void setGenderScore(BigDecimal genderScore) {
    this.genderScore = genderScore;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GenderPrediction genderPrediction = (GenderPrediction) o;
    return Objects.equals(this.gender, genderPrediction.gender) &&
        Objects.equals(this.genderScore, genderPrediction.genderScore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gender, genderScore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GenderPrediction {\n");
    
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    genderScore: ").append(toIndentedString(genderScore)).append("\n");
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
