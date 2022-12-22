package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SentenceList
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-20T10:51:52.599Z[GMT]")


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
  public boolean equals(Object o) {
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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
