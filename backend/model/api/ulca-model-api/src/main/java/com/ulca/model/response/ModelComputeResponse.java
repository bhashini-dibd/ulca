package com.ulca.model.response;

import java.util.List;

import com.ulca.model.request.Input;
import com.ulca.model.request.ModelComputeRequest;

import io.swagger.model.ASRResponse;
import io.swagger.model.SentencesList;
import io.swagger.model.TranslationResponse;
import io.swagger.model.TxtLangDetectionResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModelComputeResponse {
	
	private String outputText;
	private TranslationResponse translationResponse;
	private SentencesList transliterationOutput;
	private TxtLangDetectionResponse languageDetectionOutput;

}
