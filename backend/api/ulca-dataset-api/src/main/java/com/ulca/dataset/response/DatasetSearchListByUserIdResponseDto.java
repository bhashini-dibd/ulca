package com.ulca.dataset.response;

import java.util.Date;

import com.ulca.dataset.request.SearchCriteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
@AllArgsConstructor

public class DatasetSearchListByUserIdResponseDto {
	
	private String serviceRequestNumber;
	
	private SearchCriteria searchCriteria;
	
	private Date timestamp;
	
	private String status;
	

}
