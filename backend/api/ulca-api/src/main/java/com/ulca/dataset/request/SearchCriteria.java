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
	
	private String licence;

    private String minScore;

    private String serviceRequestNumber;

    private String[] targetLanguage;

    private String[] collectionMode;

    private String multipleContributors;

    private String maxScore;

    private String[] collectionSource;

    private String score;

    private String[] domain;

    private String limit;

    private String datasetId;

    private String datasetType;

    private String[] sourceLanguage;
    
    private String[] groupBy;

}
