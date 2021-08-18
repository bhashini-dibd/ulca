package com.ulca.dataset.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchCriteria {
	
	private String[] licence;

    private Float minScore;

    private String serviceRequestNumber;

    private String[] targetLanguage;

    private String[] collectionMode;

    private Boolean multipleContributors;

    private Float maxScore;

    private String[] collectionSource;

    private Float score;

    private String[] domain;

    private Integer limit;

    private String datasetId;

    private String datasetType;

    private String[] sourceLanguage;
    
    private Boolean groupBy;
    
    private Integer countOfTranslations;
    
    private Boolean originalSourceSentence;
    
    

}
