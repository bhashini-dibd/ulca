package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * machine generat6ed collection details
 */
@Schema(description = "machine generat6ed collection details")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:41:06.560Z[GMT]")


public class CollectionDetailsMachineGenerated  implements OneOfGlossaryDatasetCollectionMethodCollectionDetails, OneOfNerDatasetCollectionMethodCollectionDetails {
  @JsonProperty("model")
  private String model = null;

  @JsonProperty("modelVersion")
  private String modelVersion = null;

  public CollectionDetailsMachineGenerated model(String model) {
    this.model = model;
    return this;
  }

  /**
   * name of the transliteration model/engine used
   * @return model
   **/
  @Schema(example = "google transliteration", required = true, description = "name of the transliteration model/engine used")
      @NotNull

    public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public CollectionDetailsMachineGenerated modelVersion(String modelVersion) {
    this.modelVersion = modelVersion;
    return this;
  }

  /**
   * model/engine version
   * @return modelVersion
   **/
  @Schema(description = "model/engine version")
  
    public String getModelVersion() {
    return modelVersion;
  }

  public void setModelVersion(String modelVersion) {
    this.modelVersion = modelVersion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CollectionDetailsMachineGenerated collectionDetailsMachineGenerated = (CollectionDetailsMachineGenerated) o;
    return Objects.equals(this.model, collectionDetailsMachineGenerated.model) &&
        Objects.equals(this.modelVersion, collectionDetailsMachineGenerated.modelVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(model, modelVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionDetailsMachineGenerated {\n");
    
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    modelVersion: ").append(toIndentedString(modelVersion)).append("\n");
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
