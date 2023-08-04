package com.ulca.model.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.gson.Gson;

import io.swagger.model.Gender;
import io.swagger.model.ImageFile;
import io.swagger.model.ImageFiles;
import io.swagger.model.OCRRequest;
import io.swagger.model.Sentence;
import io.swagger.model.Sentences;
import io.swagger.model.TTSRequest;
import io.swagger.model.TTSRequestConfig;
import io.swagger.model.TranslationRequest;

@JsonTypeName(value = "NerRequest")
public class NerRequest extends TranslationRequest implements  OneOfRequest {

	
	
	
	
}
