package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SentenceList
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T03:55:25.562740452Z[GMT]")


public class SentenceList   {
  @JsonProperty("source")
  private String source = null;

  @JsonProperty("target")
  @Valid
  private List<String> target = null;

  public SentenceList source(String source) {
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

  public SentenceList target(List<String> target) {
    this.target = target;
    return this;
  }

  public SentenceList addTargetItem(String targetItem) {
    if (this.target == null) {
      this.target = new ArrayList<String>();
    }
    this.target.add(targetItem);
    return this;
  }

  /**
   * list of sentences
   * @return target
   **/
  @Schema(description = "list of sentences")
  
    public List<String> getTarget() {
    return target;
  }

  public void setTarget(List<String> target) {
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
    SentenceList sentenceList = (SentenceList) o;
    return Objects.equals(this.source, sentenceList.source) &&
        Objects.equals(this.target, sentenceList.target);
  }

  @Override
  public int hashCode() {
    return Objects.hash(source, target);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SentenceList {\n");
    
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
