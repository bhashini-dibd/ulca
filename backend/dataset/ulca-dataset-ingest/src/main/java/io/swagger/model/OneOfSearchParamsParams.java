package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfSearchParamsParams
*/
/*
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = Domain.class, name = "Domain"),
  @JsonSubTypes.Type(value = LanguagePair.class, name = "LanguagePair"),
  @JsonSubTypes.Type(value = Source.class, name = "Source"),
  @JsonSubTypes.Type(value = CollectionMethod.class, name = "CollectionMethod")
})
*/
public interface OneOfSearchParamsParams {

}
