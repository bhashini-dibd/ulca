package com.ulca.model.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.swagger.model.ASRInference;
import io.swagger.model.NerInference;
import io.swagger.model.OCRInference;
import io.swagger.model.OCRRequest;
import io.swagger.model.TTSInference;
import io.swagger.model.TTSRequest;
import io.swagger.model.TranslationInference;
import io.swagger.model.TranslationRequest;
import io.swagger.model.TransliterationInference;
import io.swagger.model.TransliterationRequest;
import io.swagger.model.TxtLangDetectionInference;
import io.swagger.model.TxtLangDetectionRequest;
/**
@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
				  include = JsonTypeInfo.As.PROPERTY, 
		property = "taskType"
		)
		@JsonSubTypes({
			  @JsonSubTypes.Type(value = TranslationRequest.class, name = "translation"),
			  @JsonSubTypes.Type(value = TransliterationRequest.class, name = "transliteration"),
			  @JsonSubTypes.Type(value = TTSRequest.class, name = "tts"),
			  @JsonSubTypes.Type(value = OCRRequest.class, name = "ocr"),
			  @JsonSubTypes.Type(value = TxtLangDetectionRequest.class, name = "txt-lang-detection"),
			  @JsonSubTypes.Type(value = NerRequest.class, name = "ner")
			})*/

/*@JsonSubTypes({
    @JsonSubTypes.Type(TranslationRequest.class),
    @JsonSubTypes.Type(TransliterationRequest.class),
    @JsonSubTypes.Type(TTSRequest.class),
    @JsonSubTypes.Type(OCRRequest.class),
    @JsonSubTypes.Type(TxtLangDetectionRequest.class),
    @JsonSubTypes.Type(NerRequest.class)
    })*/
//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,include = JsonTypeInfo.As.PROPERTY , property = "@class")

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = 
JsonTypeInfo.As.PROPERTY , property = "type")
@JsonSubTypes({ @Type(value = TranslationRequest.class, name = "TranslationRequest"),
    @Type(value = TransliterationRequest.class,name = "TransliterationRequest") ,
    @Type(value = TTSRequest.class,name = "TTSRequest"),
    @Type(value = OCRRequest.class,name = "OCRRequest"),
    @Type(value = TxtLangDetectionRequest.class,name = "TxtLangDetectionRequest"),
    @Type(value = NerRequest.class,name = "NerRequest")


})
public interface OneOfRequest {

}
