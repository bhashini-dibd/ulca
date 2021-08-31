package com.ulca.benchmark.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.model.Benchmark;
import io.swagger.model.ModelTask;
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
public class BenchmarkSearchRequest {
	
	public enum MetricNameEnum {
	    BLEU("bleu"),
	    
	    SACREBLEU("sacrebleu"),
	    
	    METEOR("meteor"),
	    
	    LEPOR("lepor");

	    private String value;

	    MetricNameEnum(String value) {
	      this.value = value;
	    }

	    @Override
	    @JsonValue
	    public String toString() {
	      return String.valueOf(value);
	    }

	    @JsonCreator
	    public static MetricNameEnum fromValue(String text) {
	      for (MetricNameEnum b : MetricNameEnum.values()) {
	        if (String.valueOf(b.value).equals(text)) {
	          return b;
	        }
	      }
	      return null;
	    }
	  }
	  @JsonProperty("metricName")
	  private MetricNameEnum metricName = null;

	  @JsonProperty("score")
	  private BigDecimal score = null;

   private ModelTask task ;
   private Benchmark domain ;

}
