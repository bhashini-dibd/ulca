package com.ulca.model.response;

import java.util.List;

import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.dao.ModelExtendedDto;

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
public class ModelListResponseDto extends ModelExtendedDto {
	
	private List<String> metric;
	List<BenchmarkProcess> benchmarkPerformance ;

}
