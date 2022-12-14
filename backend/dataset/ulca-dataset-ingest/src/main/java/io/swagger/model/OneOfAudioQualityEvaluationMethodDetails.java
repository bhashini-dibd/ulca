package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfAudioQualityEvaluationMethodDetails
*/
//commented as we are deserializing manually to have better control on deserializer
/*
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = WadaSnr.class, name = "WadaSnr")
})
*/
public interface OneOfAudioQualityEvaluationMethodDetails {

}
