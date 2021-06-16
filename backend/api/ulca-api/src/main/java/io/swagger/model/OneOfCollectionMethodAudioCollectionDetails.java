package io.swagger.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ulca.dataset.model.deserializer.OneOfCollectionMethodAudioCollectionDetailsDeserializer;

/**
* OneOfCollectionMethodAudioCollectionDetails
*/
@JsonDeserialize(using = OneOfCollectionMethodAudioCollectionDetailsDeserializer.class)
public interface OneOfCollectionMethodAudioCollectionDetails {

}
