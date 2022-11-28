package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfParallelDatasetCollectionMethodCollectionDetails
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = CollectionDetailsAutoAligned.class, name = "CollectionDetailsAutoAligned"),
  @JsonSubTypes.Type(value = CollectionDetailsAutoAlignedFromParallelDocs.class, name = "CollectionDetailsAutoAlignedFromParallelDocs"),
  @JsonSubTypes.Type(value = CollectionDetailsManualAligned.class, name = "CollectionDetailsManualAligned"),
  @JsonSubTypes.Type(value = CollectionDetailsMachineTranslated.class, name = "CollectionDetailsMachineTranslated"),
  @JsonSubTypes.Type(value = CollectionDetailsMachineTranslatedPostEdited.class, name = "CollectionDetailsMachineTranslatedPostEdited"),
  @JsonSubTypes.Type(value = CollectionDetailsManualTranslated.class, name = "CollectionDetailsManualTranslated")
})
public interface OneOfParallelDatasetCollectionMethodCollectionDetails {

}
