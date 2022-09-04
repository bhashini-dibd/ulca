package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.TxtLangDetectionPrediction;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TxtLangDetectionList
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-09-04T16:26:09.493Z[GMT]")


public class TxtLangDetectionList   {
  @JsonProperty("source")
  private String source = null;

  @JsonProperty("langPrediction")
  @Valid
  private List<TxtLangDetectionPrediction> langPrediction = null;

  public TxtLangDetectionList source(String source) {
    this.source = source;
    return this;
  }

  /**
   * input sentence for the model
   * @return source
   **/
  @Schema(required = true, description = "input sentence for the model")
      @NotNull

  @Size(min=1)   public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public TxtLangDetectionList langPrediction(List<TxtLangDetectionPrediction> langPrediction) {
    this.langPrediction = langPrediction;
    return this;
  }

  public TxtLangDetectionList addLangPredictionItem(TxtLangDetectionPrediction langPredictionItem) {
    if (this.langPrediction == null) {
      this.langPrediction = new ArrayList<TxtLangDetectionPrediction>();
    }
    this.langPrediction.add(langPredictionItem);
    return this;
  }

  /**
   * list of
   * @return langPrediction
   **/
  @Schema(description = "list of")
      @Valid
    public List<TxtLangDetectionPrediction> getLangPrediction() {
    return langPrediction;
  }

  public void setLangPrediction(List<TxtLangDetectionPrediction> langPrediction) {
    this.langPrediction = langPrediction;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TxtLangDetectionList txtLangDetectionList = (TxtLangDetectionList) o;
    return Objects.equals(this.source, txtLangDetectionList.source) &&
        Objects.equals(this.langPrediction, txtLangDetectionList.langPrediction);
  }

  @Override
  public int hashCode() {
    return Objects.hash(source, langPrediction);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TxtLangDetectionList {\n");
    
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("    langPrediction: ").append(toIndentedString(langPrediction)).append("\n");
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
