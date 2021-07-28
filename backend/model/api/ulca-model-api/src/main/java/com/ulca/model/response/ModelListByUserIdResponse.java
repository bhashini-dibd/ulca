package com.ulca.model.response;

import java.util.List;

import io.swagger.model.Model;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelListByUserIdResponse {
	
	String message;
	List<Model> data;
	int count;

}
