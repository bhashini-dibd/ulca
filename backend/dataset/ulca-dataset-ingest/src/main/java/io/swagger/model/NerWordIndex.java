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
 * Ner dataset sentencewise metadata
 */
@Schema(description = "Ner dataset sentencewise metadata")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:41:06.560Z[GMT]")


public class NerWordIndex  implements AnyOfNerData {
  @JsonProperty("wordIndex")
  private BigDecimal wordIndex = null;

  public NerWordIndex wordIndex(BigDecimal wordIndex) {
    this.wordIndex = wordIndex;
    return this;
  }

  /**
   * index number of word
   * @return wordIndex
   **/
  @Schema(example = "1", required = true, description = "index number of word")
      @NotNull

    @Valid
    public BigDecimal getWordIndex() {
    return wordIndex;
  }

  public void setWordIndex(BigDecimal wordIndex) {
    this.wordIndex = wordIndex;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NerWordIndex nerWordIndex = (NerWordIndex) o;
    return Objects.equals(this.wordIndex, nerWordIndex.wordIndex);
  }

  @Override
  public int hashCode() {
    return Objects.hash(wordIndex);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NerWordIndex {\n");
    
    sb.append("    wordIndex: ").append(toIndentedString(wordIndex)).append("\n");
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
