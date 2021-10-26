package com.ulca.benchmark.request;

import javax.validation.constraints.NotBlank;

import io.swagger.model.Domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BenchmarkListByModelRequest {
	
	@NotBlank(message="modelId is required")
	private String modelId;
   

}
