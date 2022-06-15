package com.ulca.model.response;

import java.util.List;

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
public class ModelLeaderboardFiltersMetricResponse {

	List<String> translation;
	List<String> asr;
	List<String> ocr;
	List<String> tts;
	List<String> documentLayout;
	
}
