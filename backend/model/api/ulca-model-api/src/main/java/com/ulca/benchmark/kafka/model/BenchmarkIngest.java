package com.ulca.benchmark.kafka.model;

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
public class BenchmarkIngest {

	private String benchmarkId;
	private String serviceRequestNumber;
}
