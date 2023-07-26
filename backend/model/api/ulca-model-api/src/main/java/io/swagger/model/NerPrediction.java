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
 * NerPrediction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T03:55:25.562740452Z[GMT]")


public class NerPrediction   {
  @JsonProperty("token")
  private String token = null;

  @JsonProperty("tag")
  private String tag = null;

  @JsonProperty("score")
  private BigDecimal score = null;

  @JsonProperty("tokenIndex")
  private BigDecimal tokenIndex = null;

  @JsonProperty("tokenStartIndex")
  private BigDecimal tokenStartIndex = null;

  @JsonProperty("tokenEndIndex")
  private BigDecimal tokenEndIndex = null;

  public NerPrediction token(String token) {
    this.token = token;
    return this;
  }

  /**
   * corresponding word in the sentence which is tagged
   * @return token
   **/
  @Schema(required = true, description = "corresponding word in the sentence which is tagged")
      @NotNull

    public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public NerPrediction tag(String tag) {
    this.tag = tag;
    return this;
  }

  /**
   * Ner tag associated with selected word
   * @return tag
   **/
  @Schema(required = true, description = "Ner tag associated with selected word")
      @NotNull

    public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public NerPrediction score(BigDecimal score) {
    this.score = score;
    return this;
  }

  /**
   * the measure of accuracy of language prediction
   * @return score
   **/
  @Schema(description = "the measure of accuracy of language prediction")
  
    @Valid
    public BigDecimal getScore() {
    return score;
  }

  public void setScore(BigDecimal score) {
    this.score = score;
  }

  public NerPrediction tokenIndex(BigDecimal tokenIndex) {
    this.tokenIndex = tokenIndex;
    return this;
  }

  /**
   * the index number of particular token in sentence
   * @return tokenIndex
   **/
  @Schema(description = "the index number of particular token in sentence")
  
    @Valid
    public BigDecimal getTokenIndex() {
    return tokenIndex;
  }

  public void setTokenIndex(BigDecimal tokenIndex) {
    this.tokenIndex = tokenIndex;
  }

  public NerPrediction tokenStartIndex(BigDecimal tokenStartIndex) {
    this.tokenStartIndex = tokenStartIndex;
    return this;
  }

  /**
   * the starting index of word in sentence
   * @return tokenStartIndex
   **/
  @Schema(required = true, description = "the starting index of word in sentence")
      @NotNull

    @Valid
    public BigDecimal getTokenStartIndex() {
    return tokenStartIndex;
  }

  public void setTokenStartIndex(BigDecimal tokenStartIndex) {
    this.tokenStartIndex = tokenStartIndex;
  }

  public NerPrediction tokenEndIndex(BigDecimal tokenEndIndex) {
    this.tokenEndIndex = tokenEndIndex;
    return this;
  }

  /**
   * the ending index of word in sentence
   * @return tokenEndIndex
   **/
  @Schema(required = true, description = "the ending index of word in sentence")
      @NotNull

    @Valid
    public BigDecimal getTokenEndIndex() {
    return tokenEndIndex;
  }

  public void setTokenEndIndex(BigDecimal tokenEndIndex) {
    this.tokenEndIndex = tokenEndIndex;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NerPrediction nerPrediction = (NerPrediction) o;
    return Objects.equals(this.token, nerPrediction.token) &&
        Objects.equals(this.tag, nerPrediction.tag) &&
        Objects.equals(this.score, nerPrediction.score) &&
        Objects.equals(this.tokenIndex, nerPrediction.tokenIndex) &&
        Objects.equals(this.tokenStartIndex, nerPrediction.tokenStartIndex) &&
        Objects.equals(this.tokenEndIndex, nerPrediction.tokenEndIndex);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, tag, score, tokenIndex, tokenStartIndex, tokenEndIndex);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NerPrediction {\n");
    
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
    sb.append("    score: ").append(toIndentedString(score)).append("\n");
    sb.append("    tokenIndex: ").append(toIndentedString(tokenIndex)).append("\n");
    sb.append("    tokenStartIndex: ").append(toIndentedString(tokenStartIndex)).append("\n");
    sb.append("    tokenEndIndex: ").append(toIndentedString(tokenEndIndex)).append("\n");
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
