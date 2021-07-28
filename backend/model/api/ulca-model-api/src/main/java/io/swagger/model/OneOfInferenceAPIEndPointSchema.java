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
		  property = "name")
		@JsonSubTypes({ 
		  @Type(value = ASRInference.class, name = "ASRInference"), 
		  @Type(value = OCRInference.class, name = "OCRInference"),
		  @Type(value = TranslationInference.class, name = "TranslationInference"), 
		  @Type(value = TTSInference.class, name = "TTSInference")
		})
public interface OneOfInferenceAPIEndPointSchema {

}
