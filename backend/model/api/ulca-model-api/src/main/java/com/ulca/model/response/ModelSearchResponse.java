package com.ulca.model.response;

import java.util.List;

import com.ulca.model.dao.ModelExtended;
import com.ulca.model.dao.ModelExtendedDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelSearchResponse {

	String message;
	List<ModelExtendedDto> data;
	int count;
	
}
