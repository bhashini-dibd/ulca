
package com.ulca.benchmark.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;

import io.swagger.model.ModelTask;

import javax.validation.constraints.*;


@Data
@RequiredArgsConstructor
public class BenchmarkSubmitRequest   {

	@NotBlank(message="userId is required")
	private final String userId;
	
	@NotBlank(message="name is required")
	private final String name;

	@NotBlank(message="dataset is required")
	@URL
    private final String dataset;
	
	@NotBlank(message="task is required")
	private ModelTask task;



}
