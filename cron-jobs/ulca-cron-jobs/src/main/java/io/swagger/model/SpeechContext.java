package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SpeechContext
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-07-11T05:46:23.563016166Z[GMT]")


public class SpeechContext   {
  @JsonProperty("phrases")
  @Valid
  private List<String> phrases = null;

  @JsonProperty("boost")
  private BigDecimal boost = null;

  public SpeechContext phrases(List<String> phrases) {
    this.phrases = phrases;
    return this;
  }

  public SpeechContext addPhrasesItem(String phrasesItem) {
    if (this.phrases == null) {
      this.phrases = new ArrayList<String>();
    }
    this.phrases.add(phrasesItem);
    return this;
  }

  /**
   * list of
   * @return phrases
   **/
  @Schema(example = "[\"weather\",\"rain\"]", description = "list of")
  
    public List<String> getPhrases() {
    return phrases;
  }

  public void setPhrases(List<String> phrases) {
    this.phrases = phrases;
  }

  public SpeechContext boost(BigDecimal boost) {
    this.boost = boost;
    return this;
  }

  /**
   * numeric to specify phrase boosting threshold
   * @return boost
   **/
  @Schema(description = "numeric to specify phrase boosting threshold")
  
    @Valid
    public BigDecimal getBoost() {
    return boost;
  }

  public void setBoost(BigDecimal boost) {
    this.boost = boost;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SpeechContext speechContext = (SpeechContext) o;
    return Objects.equals(this.phrases, speechContext.phrases) &&
        Objects.equals(this.boost, speechContext.boost);
  }

  @Override
  public int hashCode() {
    return Objects.hash(phrases, boost);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SpeechContext {\n");
    
    sb.append("    phrases: ").append(toIndentedString(phrases)).append("\n");
    sb.append("    boost: ").append(toIndentedString(boost)).append("\n");
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
