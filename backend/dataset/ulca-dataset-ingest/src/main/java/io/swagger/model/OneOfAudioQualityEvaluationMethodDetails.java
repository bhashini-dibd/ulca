package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfAudioQualityEvaluationMethodDetails
*/
/*
 * @JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include =
 * JsonTypeInfo.As.PROPERTY, property = "type")
 * 
 * @JsonSubTypes({
 * 
 * @JsonSubTypes.Type(value = WadaSnr.class, name = "WadaSnr") })
 */
public interface OneOfAudioQualityEvaluationMethodDetails {

}
