package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* AnyOfNerData
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = NerWordIndex.class, name = "NerWordIndex"),
  @JsonSubTypes.Type(value = NerWord.class, name = "NerWord")
})
public interface AnyOfNerData {

}
