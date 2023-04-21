package com.ulca.model.response;

import com.ulca.model.dao.ModelExtended;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadModelResponse {
	@Schema( defaultValue = "parshant@tarento.com")
     String message;
	//ModelExtended data;
	 Object data;
	
}
