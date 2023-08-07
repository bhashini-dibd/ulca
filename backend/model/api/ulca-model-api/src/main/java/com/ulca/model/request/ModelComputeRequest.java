package com.ulca.model.request;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.swagger.model.Gender;
import io.swagger.model.OCRRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = 
JsonTypeInfo.As.PROPERTY , property = "task")
@JsonSubTypes({ 
	@Type(value = TranslationComputeRequest.class, name = "translation"),
    @Type(value = TTSComputeRequest.class,name = "tts"),
    @Type(value = NerComputeRequest.class,name = "ner"),
    @Type(value = OCRComputeRequest.class,name = "ocr"),
    @Type(value = TransliterationComputeRequest.class,name = "transliteration"),
    @Type(value = TxtLangDetectionComputeRequest.class,name = "txt-lang-detection")
	
   })
public interface ModelComputeRequest {

}
