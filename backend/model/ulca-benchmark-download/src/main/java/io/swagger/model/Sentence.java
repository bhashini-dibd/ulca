package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Sentence
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-08-02T06:46:17.068Z[GMT]")


public class Sentence   {
  @JsonProperty("source")
  private String source = null;

  @JsonProperty("target")
  private String target = null;

  public Sentence source(String source) {
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

  public Sentence target(String target) {
    this.target = target;
    return this;
  }

  /**
   * to be used along with translation model. expected translated sentence, for reference
   * @return target
   **/
  @Schema(description = "to be used along with translation model. expected translated sentence, for reference")
  
  @Size(min=1)   public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Sentence sentence = (Sentence) o;
    return Objects.equals(this.source, sentence.source) &&
        Objects.equals(this.target, sentence.target);
  }

  @Override
  public int hashCode() {
    return Objects.hash(source, target);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Sentence {\n");
    
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("    target: ").append(toIndentedString(target)).append("\n");
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