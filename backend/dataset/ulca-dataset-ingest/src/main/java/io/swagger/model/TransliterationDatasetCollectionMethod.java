package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * specifies how the dataset is curated.
 */
@Schema(description = "specifies how the dataset is curated.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-25T09:44:34.039Z[GMT]")


public class TransliterationDatasetCollectionMethod   {
  /**
   * Gets or Sets collectionDescription
   */
  public enum CollectionDescriptionEnum {
    AUTO_ALIGNED("auto-aligned"),
    
    MACHINE_TRANSLITERATED("machine-transliterated"),
    
    MACHINE_TRANSLITERATED_POST_EDITED("machine-transliterated-post-edited"),
    
    MANUAL_TRANSLITERATED("manual-transliterated");

    private String value;

    CollectionDescriptionEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static CollectionDescriptionEnum fromValue(String text) {
      for (CollectionDescriptionEnum b : CollectionDescriptionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("collectionDescription")
  @Valid
  private List<CollectionDescriptionEnum> collectionDescription = new ArrayList<CollectionDescriptionEnum>();

  @JsonProperty("collectionDetails")
  private OneOfTransliterationDatasetCollectionMethodCollectionDetails collectionDetails = null;

  public TransliterationDatasetCollectionMethod collectionDescription(List<CollectionDescriptionEnum> collectionDescription) {
    this.collectionDescription = collectionDescription;
    return this;
  }

  public TransliterationDatasetCollectionMethod addCollectionDescriptionItem(CollectionDescriptionEnum collectionDescriptionItem) {
    this.collectionDescription.add(collectionDescriptionItem);
    return this;
  }

  /**
   * various collection methods user have used to create the dataset
   * @return collectionDescription
   **/
  @Schema(required = true, description = "various collection methods user have used to create the dataset")
      @NotNull

    public List<CollectionDescriptionEnum> getCollectionDescription() {
    return collectionDescription;
  }

  public void setCollectionDescription(List<CollectionDescriptionEnum> collectionDescription) {
    this.collectionDescription = collectionDescription;
  }

  public TransliterationDatasetCollectionMethod collectionDetails(OneOfTransliterationDatasetCollectionMethodCollectionDetails collectionDetails) {
    this.collectionDetails = collectionDetails;
    return this;
  }

  /**
   * Get collectionDetails
   * @return collectionDetails
   **/
  @Schema(description = "")
  
    public OneOfTransliterationDatasetCollectionMethodCollectionDetails getCollectionDetails() {
    return collectionDetails;
  }

  public void setCollectionDetails(OneOfTransliterationDatasetCollectionMethodCollectionDetails collectionDetails) {
    this.collectionDetails = collectionDetails;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransliterationDatasetCollectionMethod transliterationDatasetCollectionMethod = (TransliterationDatasetCollectionMethod) o;
    return Objects.equals(this.collectionDescription, transliterationDatasetCollectionMethod.collectionDescription) &&
        Objects.equals(this.collectionDetails, transliterationDatasetCollectionMethod.collectionDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(collectionDescription, collectionDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransliterationDatasetCollectionMethod {\n");
    
    sb.append("    collectionDescription: ").append(toIndentedString(collectionDescription)).append("\n");
    sb.append("    collectionDetails: ").append(toIndentedString(collectionDetails)).append("\n");
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
