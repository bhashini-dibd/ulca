package com.ulca.model.response;

import java.util.List;

import com.ulca.benchmark.model.BenchmarkProcess;

import io.swagger.pipelinemodel.PipelineTaskSequence;
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
public class PipelineModelResponse {

	@Schema( defaultValue = "63c95855a0e5e81614ff96a7")
    private String pipelineId;
	
	@Schema( defaultValue = "abc")
	private String name;
	
	@Schema( defaultValue = "AI4Bharat Pipeline")

	private String description;
	
	@Schema( defaultValue = "AI4Bharat")

	private String serviceProviderName;
	private List<PipelineTaskSequence> supportedTasks;
	
	
}
