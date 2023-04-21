package com.ulca.model.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PipelinesResponse {

	
	//String message;
	List<PipelineModelResponse> data;
	
	@Schema( defaultValue = "1")
    int totalCount;
	
	
	
	
}
