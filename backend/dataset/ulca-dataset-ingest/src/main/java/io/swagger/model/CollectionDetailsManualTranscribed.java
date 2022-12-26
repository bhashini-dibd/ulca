package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Contributor;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * manual translated collection details
 */
@Schema(description = "manual translated collection details")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:41:06.560Z[GMT]")


public class CollectionDetailsManualTranscribed  implements OneOfCollectionMethodAudioCollectionDetails {
  @JsonProperty("contributor")
  private Contributor contributor = null;

  public CollectionDetailsManualTranscribed contributor(Contributor contributor) {
    this.contributor = contributor;
    return this;
  }

  /**
   * Get contributor
   * @return contributor
   **/
  @Schema(description = "")
  
    @Valid
    public Contributor getContributor() {
    return contributor;
  }

  public void setContributor(Contributor contributor) {
    this.contributor = contributor;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CollectionDetailsManualTranscribed collectionDetailsManualTranscribed = (CollectionDetailsManualTranscribed) o;
    return Objects.equals(this.contributor, collectionDetailsManualTranscribed.contributor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(contributor);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionDetailsManualTranscribed {\n");
    
    sb.append("    contributor: ").append(toIndentedString(contributor)).append("\n");
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
