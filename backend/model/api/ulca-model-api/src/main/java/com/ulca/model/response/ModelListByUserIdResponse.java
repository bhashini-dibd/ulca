package com.ulca.model.response;

import java.util.List;

import com.ulca.model.dao.ModelExtended;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelListByUserIdResponse {
	
	@Schema(defaultValue = "Model list by UserId")
	String message;
	List<ModelListResponseDto> data;
	
	@Schema(defaultValue = "1")

	int count;
	@Schema(defaultValue = "1")

	int totalCount;

}
