package com.ulca.model.response;

import java.util.List;


import io.swagger.model.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class UploadModelResponse {

	String message;
	Model data;
	
	
}
