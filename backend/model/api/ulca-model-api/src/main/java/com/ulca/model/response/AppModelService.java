package com.ulca.model.response;



import io.swagger.model.SupportedLanguages;
import io.swagger.model.SupportedScripts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppModelService {

	private String name;
	private String description;
	private String serviceId;
	private SupportedLanguages sourceLanguage;
	private SupportedLanguages targetLanguage;
	private SupportedScripts sourceScriptCode;
	private SupportedScripts targetScriptCode;
	private Boolean defaultModel;
	
}
