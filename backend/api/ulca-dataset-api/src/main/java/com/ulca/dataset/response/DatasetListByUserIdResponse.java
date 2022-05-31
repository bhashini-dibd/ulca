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
	Integer startPage;
	Integer endPage;
	public DatasetListByUserIdResponse(String message, List<DatasetListByUserIdResponseDto> data, Integer startPage, Integer endPage) {
		super();
		this.message = message;
		this.data = data;
		this.count = data.size();
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
