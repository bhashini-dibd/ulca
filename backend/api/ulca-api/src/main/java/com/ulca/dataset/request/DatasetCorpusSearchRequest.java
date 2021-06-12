package com.ulca.dataset.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.model.DatasetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@AllArgsConstructor
@Getter
@Setter
public class DatasetCorpusSearchRequest {
	
	
	private final DatasetType datasetType;
	
	@NotBlank(message="criteria is required")
    private final SearchCriteria criteria;
	
	
    private final String[] groupby;

}
