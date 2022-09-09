package com.ulca.dataset.response;

import java.util.List;

import com.ulca.dataset.model.TaskTracker;
import com.ulca.dataset.request.SearchCriteria;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchListByUserIdResponseDto {
	
	
	private final String serviceRequestNumber;
	private final Long timestamp;
	private final SearchCriteria searchCriteria;
	private final List<TaskTracker> status;
	

}
