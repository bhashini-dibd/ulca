package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfNerDatasetCollectionMethodCollectionDetails
*/
/*
 * @JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include =
 * JsonTypeInfo.As.PROPERTY, property = "type")
 * 
 * @JsonSubTypes({
 * 
 * @JsonSubTypes.Type(value = CollectionDetailsManualCurated.class, name =
 * "CollectionDetailsManualCurated"),
 * 
 * @JsonSubTypes.Type(value = CollectionDetailsMachineGenerated.class, name =
 * "CollectionDetailsMachineGenerated"),
 * 
 * @JsonSubTypes.Type(value = CollectionDetailsMachineGeneratedPostEdited.class,
 * name = "CollectionDetailsMachineGeneratedPostEdited") })
 */
public interface OneOfNerDatasetCollectionMethodCollectionDetails {

}
