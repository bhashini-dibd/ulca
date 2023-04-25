package com.ulca.model.response;

import java.util.List;

import com.ulca.model.dao.ModelExtended;
import com.ulca.model.dao.ModelExtendedDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelSearchResponse {

	
	
	@Schema(defaultValue = "Model Search Result")
	String message;
	List<ModelExtendedDto> data;
	
	@Schema(defaultValue = "1")

	int count;
	
}
