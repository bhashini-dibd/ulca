package com.ulca.dataset.response;

import java.util.List;

import com.ulca.dataset.model.TaskTracker;
import com.ulca.dataset.request.SearchCriteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@AllArgsConstructor
@Getter
@Setter
public class DatasetSearchStatusResponse {
	
	private final String serviceRequestNumber;
	private final String timestamp;
	private final SearchCriteria searchCriteria;
	private final List<TaskTracker> status;
	


}
