package com.ulca.benchmark.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BenchmarkSubmitResponse {

	private String message;
	private String benchamrkId;
	private String status;
}
