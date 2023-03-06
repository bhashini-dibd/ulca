package io.swagger.pipelinerequest;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfAsyncApiDetailsAsyncApiPollingSchema
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = TranslationAsyncPollingInference.class, name = "TranslationAsyncPollingInference"),
  @JsonSubTypes.Type(value = ASRAsyncPollingInference.class, name = "ASRAsyncPollingInference"),
  @JsonSubTypes.Type(value = TTSAsyncPollingInference.class, name = "TTSAsyncPollingInference"),
  @JsonSubTypes.Type(value = OCRAsyncPollingInference.class, name = "OCRAsyncPollingInference")
})
public interface OneOfAsyncApiDetailsAsyncApiPollingSchema {

}
