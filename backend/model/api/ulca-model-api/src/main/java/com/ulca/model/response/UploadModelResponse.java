package com.ulca.model.response;

import com.ulca.model.dao.ModelExtended;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadModelResponse {

	String message;
	ModelExtended data;
	
}
