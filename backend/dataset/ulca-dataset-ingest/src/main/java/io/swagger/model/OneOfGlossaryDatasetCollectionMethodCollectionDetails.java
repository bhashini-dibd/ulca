package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfGlossaryDatasetCollectionMethodCollectionDetails
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = CollectionDetailsGlossaryAutoAligned.class, name = "CollectionDetailsGlossaryAutoAligned"),
  @JsonSubTypes.Type(value = CollectionDetailsManualCurated.class, name = "CollectionDetailsManualCurated"),
  @JsonSubTypes.Type(value = CollectionDetailsMachineGenerated.class, name = "CollectionDetailsMachineGenerated"),
  @JsonSubTypes.Type(value = CollectionDetailsMachineGeneratedPostEdited.class, name = "CollectionDetailsMachineGeneratedPostEdited")
})
public interface OneOfGlossaryDatasetCollectionMethodCollectionDetails {

}
