package com.ulca.dataset.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchCriteria {

	private String serviceRequestNumber;
	private String datasetType;
	private String userId;
	
	private List<String> sourceLanguage;
	private List<String> targetLanguage;
	private List<String> collectionMethod;
	private List<String> alignmentTool;
	private List<String> editingTool;
	private List<String> translationModel;
	private List<String> license;
	private List<String> domain;
	private List<String> datasetId;
	private List<String> channel;
	private List<String> gender;
	private List<String> format;
	private List<String> bitsPerSample;
	private List<String> dialect;
	private List<String> snrTool;
	private List<String> collectionDescription;
	private List<String> ocrTool;
	private List<String> dpi;
	private List<String> imageTextType;

	private String collectionSource;
	private String submitterName;

	private Float minScore;
	private Float maxScore;
	private Float score;
	private Float samplingRate;
	
	private Integer countOfTranslations;
	private Integer minNoOfSpeakers;
	private Integer maxNoOfSpeakers;
	private Integer noOfSpeakers;
	private Integer minAge;
	private Integer maxAge;
	private Integer age;

	private Boolean multipleContributors;
	private Boolean originalSourceSentence;
	private Boolean groupBy;
	
}
