package com.ulca.model.response;

import java.util.List;

import io.swagger.model.LanguagePair;
import io.swagger.model.ModelTask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModelLeaderboardFiltersResponse {

	ModelTask.TypeEnum[] task;
	LanguagePair.SourceLanguageEnum[] sourceLanguage;
	
	LanguagePair.TargetLanguageEnum[] targetLanguage;
	
	ModelLeaderboardFiltersMetricResponse metric;
	
}
