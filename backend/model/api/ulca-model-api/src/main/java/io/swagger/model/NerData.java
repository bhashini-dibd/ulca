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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T03:59:56.369839514Z[GMT]")


public class NerData   {
  @JsonProperty("token")
  private String token = null;

  @JsonProperty("tag")
  private String tag = null;

  @JsonProperty("tokenIndex")
  private BigDecimal tokenIndex = null;

  @JsonProperty("tokenStartIndex")
  private BigDecimal tokenStartIndex = null;

  @JsonProperty("tokenEndIndex")
  private BigDecimal tokenEndIndex = null;

  public NerData token(String token) {
    this.token = token;
    return this;
  }

  /**
   * corresponsing word in the sentence
   * @return token
   **/
  @Schema(required = true, description = "corresponsing word in the sentence")
      @NotNull

    public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public NerData tag(String tag) {
    this.tag = tag;
    return this;
  }

  /**
   * NER tag for corresponsing word/index. This should be inline with the corresponding tagsFormat specified in params.json.
   * @return tag
   **/
  @Schema(example = "PER", required = true, description = "NER tag for corresponsing word/index. This should be inline with the corresponding tagsFormat specified in params.json.")
      @NotNull

    public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public NerData tokenIndex(BigDecimal tokenIndex) {
    this.tokenIndex = tokenIndex;
    return this;
  }

  /**
   * index number of token
   * @return tokenIndex
   **/
  @Schema(example = "1", description = "index number of token")
  
    @Valid
    public BigDecimal getTokenIndex() {
    return tokenIndex;
  }

  public void setTokenIndex(BigDecimal tokenIndex) {
    this.tokenIndex = tokenIndex;
  }

  public NerData tokenStartIndex(BigDecimal tokenStartIndex) {
    this.tokenStartIndex = tokenStartIndex;
    return this;
  }

  /**
   * the starting index of word in sentence
   * @return tokenStartIndex
   **/
  @Schema(description = "the starting index of word in sentence")
  
    @Valid
    public BigDecimal getTokenStartIndex() {
    return tokenStartIndex;
  }

  public void setTokenStartIndex(BigDecimal tokenStartIndex) {
    this.tokenStartIndex = tokenStartIndex;
  }

  public NerData tokenEndIndex(BigDecimal tokenEndIndex) {
    this.tokenEndIndex = tokenEndIndex;
    return this;
  }

  /**
   * the ending index of word in sentence
   * @return tokenEndIndex
   **/
  @Schema(description = "the ending index of word in sentence")
  
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
    NerData nerData = (NerData) o;
    return Objects.equals(this.token, nerData.token) &&
        Objects.equals(this.tag, nerData.tag) &&
        Objects.equals(this.tokenIndex, nerData.tokenIndex) &&
        Objects.equals(this.tokenStartIndex, nerData.tokenStartIndex) &&
        Objects.equals(this.tokenEndIndex, nerData.tokenEndIndex);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, tag, tokenIndex, tokenStartIndex, tokenEndIndex);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NerData {\n");
    
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
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
