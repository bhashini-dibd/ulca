package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
* OneOfInferenceAPIEndPointSchema
*/
@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME, 
		  include = JsonTypeInfo.As.PROPERTY, 
		  property = "taskType")
		@JsonSubTypes({ 
		  @Type(value = ASRInference.class, name = "asr"), 
		  @Type(value = OCRInference.class, name = "ocr"),
		  @Type(value = TranslationInference.class, name = "translation"), 
		  @Type(value = TTSInference.class, name = "tts")
		})

public interface OneOfInferenceAPIEndPointSchema {

}
