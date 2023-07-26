package com.ulca.model.response;


import com.ulca.model.dao.PipelineModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadPipelineResponse {

	String message;
	PipelineModel data;

	
}
