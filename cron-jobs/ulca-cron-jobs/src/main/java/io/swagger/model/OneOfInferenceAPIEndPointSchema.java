package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfInferenceAPIEndPointSchema
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "taskType")
@JsonSubTypes({
  @JsonSubTypes.Type(value = TranslationInference.class, name = "TranslationInference"),
  @JsonSubTypes.Type(value = TransliterationInference.class, name = "TransliterationInference"),
  @JsonSubTypes.Type(value = ASRInference.class, name = "ASRInference"),
  @JsonSubTypes.Type(value = TTSInference.class, name = "TTSInference"),
  @JsonSubTypes.Type(value = OCRInference.class, name = "OCRInference"),
  @JsonSubTypes.Type(value = TxtLangDetectionInference.class, name = "TxtLangDetectionInference"),
  @JsonSubTypes.Type(value = NerInference.class, name = "NerInference"),
  @JsonSubTypes.Type(value = AudioLangDetectionInference.class, name = "AudioLangDetectionInference")
})
public interface OneOfInferenceAPIEndPointSchema {

}
