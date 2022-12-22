package com.ulca.model.response;

import io.swagger.model.SupportedLanguages;
import io.swagger.model.SupportedTasks;
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

	SupportedTasks[] task;
	SupportedLanguages[] sourceLanguage;
	
	SupportedLanguages[] targetLanguage;
	
	ModelLeaderboardFiltersMetricResponse metric;
	
}
