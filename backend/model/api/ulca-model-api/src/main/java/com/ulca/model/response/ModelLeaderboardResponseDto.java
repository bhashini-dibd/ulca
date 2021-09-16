package com.ulca.model.response;

import io.swagger.model.LanguagePairs;
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

public class ModelLeaderboardResponseDto {
	
	private String modelId;
	private String modelName;
    private LanguagePairs languages;
	private double score;
	private String metric;
	private String benchmarkDatase;
	private String publishedOn;
	

}
