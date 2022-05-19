package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfTransliterationDatasetCollectionMethodCollectionDetails
*/
/*
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = CollectionDetailsAutoAligned.class, name = "CollectionDetailsAutoAligned"),
  @JsonSubTypes.Type(value = CollectionDetailsMachineTransliterated.class, name = "CollectionDetailsMachineTransliterated"),
  @JsonSubTypes.Type(value = CollectionDetailsMachineTransliteratedPostEdited.class, name = "CollectionDetailsMachineTransliteratedPostEdited"),
  @JsonSubTypes.Type(value = CollectionDetailsManualTransliterated.class, name = "CollectionDetailsManualTransliterated")
})*/
public interface OneOfTransliterationDatasetCollectionMethodCollectionDetails {

}
