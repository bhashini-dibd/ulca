package com.ulca.benchmark.download.kafka.model;

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
public class BenchmarkMetricRequest {

	  String benchmarkId;
      String metric;
     
}
