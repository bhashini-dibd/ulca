package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Ner dataset sentencewise metadata
 */
@Schema(description = "Ner dataset sentencewise metadata")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-25T09:44:34.039Z[GMT]")


public class NerWord  implements AnyOfNerData {
  @JsonProperty("word")
  private String word = null;

  public NerWord word(String word) {
    this.word = word;
    return this;
  }

  /**
   * corresponsing word in the sentence
   * @return word
   **/
  @Schema(required = true, description = "corresponsing word in the sentence")
      @NotNull

    public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NerWord nerWord = (NerWord) o;
    return Objects.equals(this.word, nerWord.word);
  }

  @Override
  public int hashCode() {
    return Objects.hash(word);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NerWord {\n");
    
    sb.append("    word: ").append(toIndentedString(word)).append("\n");
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
