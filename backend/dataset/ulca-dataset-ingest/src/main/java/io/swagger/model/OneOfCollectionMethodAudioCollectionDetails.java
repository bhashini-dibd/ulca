package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfCollectionMethodAudioCollectionDetails
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = CollectionDetailsAudioAutoAligned.class, name = "CollectionDetailsAudioAutoAligned"),
  @JsonSubTypes.Type(value = CollectionDetailsMachineGeneratedTranscript.class, name = "CollectionDetailsMachineGeneratedTranscript"),
  @JsonSubTypes.Type(value = CollectionDetailsManualTranscribed.class, name = "CollectionDetailsManualTranscribed")
})
public interface OneOfCollectionMethodAudioCollectionDetails {

}
