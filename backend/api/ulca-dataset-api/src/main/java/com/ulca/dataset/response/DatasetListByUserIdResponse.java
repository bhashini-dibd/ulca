package com.ulca.dataset.response;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class DatasetListByUserIdResponse {
	
	
	String message;
	List<DatasetListByUserIdResponseDto> data;
	int count;

	int totalCount;
	Integer startPage;
	Integer endPage;
	public DatasetListByUserIdResponse(String message, List<DatasetListByUserIdResponseDto> data, Integer startPage, Integer endPage,Integer totalCount) {
		super();
		this.message = message;
		this.data = data;

		this.count = data.size();
		this.totalCount = totalCount;
		if(startPage != null) {
			this.startPage = startPage;
			
		}
		if(endPage != null) {
			this.endPage = endPage;
		}
	}
	
	public DatasetListByUserIdResponse(String message, List<DatasetListByUserIdResponseDto> data) {
		super();
		this.message = message;
		this.data = data;
		this.count = data.size();
		
	}
}
