package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.NerPrediction;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NerOutputList
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-02-15T10:08:37.438508852Z[GMT]")


public class NerOutputList   {
  @JsonProperty("source")
  private String source = null;

  @JsonProperty("nerPrediction")
  @Valid
  private List<NerPrediction> nerPrediction = null;

  public NerOutputList source(String source) {
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

  public NerOutputList nerPrediction(List<NerPrediction> nerPrediction) {
    this.nerPrediction = nerPrediction;
    return this;
  }

  public NerOutputList addNerPredictionItem(NerPrediction nerPredictionItem) {
    if (this.nerPrediction == null) {
      this.nerPrediction = new ArrayList<NerPrediction>();
    }
    this.nerPrediction.add(nerPredictionItem);
    return this;
  }

  /**
   * list of
   * @return nerPrediction
   **/
  @Schema(description = "list of")
      @Valid
    public List<NerPrediction> getNerPrediction() {
    return nerPrediction;
  }

  public void setNerPrediction(List<NerPrediction> nerPrediction) {
    this.nerPrediction = nerPrediction;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NerOutputList nerOutputList = (NerOutputList) o;
    return Objects.equals(this.source, nerOutputList.source) &&
        Objects.equals(this.nerPrediction, nerOutputList.nerPrediction);
  }

  @Override
  public int hashCode() {
    return Objects.hash(source, nerPrediction);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NerOutputList {\n");
    
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("    nerPrediction: ").append(toIndentedString(nerPrediction)).append("\n");
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
