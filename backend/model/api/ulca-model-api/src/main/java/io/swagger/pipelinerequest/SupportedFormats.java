package io.swagger.pipelinerequest;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* SupportedFormats
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = AudioFormat.class, name = "AudioFormat"),
  @JsonSubTypes.Type(value = TextFormat.class, name = "TextFormat")
})
public interface SupportedFormats {

}
