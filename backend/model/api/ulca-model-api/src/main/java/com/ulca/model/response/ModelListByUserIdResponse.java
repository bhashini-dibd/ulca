package com.ulca.model.response;

import java.util.List;

import com.ulca.model.dao.ModelExtended;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelListByUserIdResponse {
	
	String message;
	List<ModelExtended> data;
	int count;

}
