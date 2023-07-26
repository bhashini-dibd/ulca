package com.ulca.model.response;

import java.util.List;

import com.ulca.benchmark.model.BenchmarkProcess;

import io.swagger.pipelinemodel.PipelineTaskSequence;
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
public class PipelineModelResponse {

	private String pipelineId;
	private String name;
	private String description;
	private String serviceProviderName;
	private List<PipelineTaskSequence> supportedTasks;
	
	
}
