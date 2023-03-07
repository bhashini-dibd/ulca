package com.ulca.model.response;

import io.swagger.pipelinerequest.PipelineResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ModelPipelineResponse {

	String message;
    String jsonResponse;
}
