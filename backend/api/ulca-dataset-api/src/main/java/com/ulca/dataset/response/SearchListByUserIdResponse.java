package com.ulca.dataset.response;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class SearchListByUserIdResponse {
	
	
	String message;
	List<SearchListByUserIdResponseDto> data;
	int count;
	Integer startPage;
	Integer endPage;
	public SearchListByUserIdResponse(String message, List<SearchListByUserIdResponseDto> data, Integer startPage, Integer endPage) {
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
	
	public SearchListByUserIdResponse(String message, List<SearchListByUserIdResponseDto> data) {
		super();
		this.message = message;
		this.data = data;
		this.count = data.size();
		
	}
}
