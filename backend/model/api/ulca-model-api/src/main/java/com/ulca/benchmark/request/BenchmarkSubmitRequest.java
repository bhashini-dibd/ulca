package com.ulca.benchmark.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.*;


@Data
@RequiredArgsConstructor
public class BenchmarkSubmitRequest   {
	
	@NotBlank(message="userId is required")
	private final String userId;
	
	@NotBlank(message="url is required")
	@URL
    private final String url;
	


}
