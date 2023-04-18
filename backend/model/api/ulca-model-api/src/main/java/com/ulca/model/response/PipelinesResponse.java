package com.ulca.model.response;

import java.util.List;

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
	int totalCount;
	
	
	
	
}
