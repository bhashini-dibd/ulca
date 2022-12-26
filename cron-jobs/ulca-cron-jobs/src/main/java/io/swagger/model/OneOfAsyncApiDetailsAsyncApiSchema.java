package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfAsyncApiDetailsAsyncApiSchema
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = TranslationAsyncInference.class, name = "TranslationAsyncInference"),
  @JsonSubTypes.Type(value = ASRAsyncInference.class, name = "ASRAsyncInference"),
  @JsonSubTypes.Type(value = TTSAsyncInference.class, name = "TTSAsyncInference"),
  @JsonSubTypes.Type(value = OCRAsyncInference.class, name = "OCRAsyncInference")
})
public interface OneOfAsyncApiDetailsAsyncApiSchema {

}
