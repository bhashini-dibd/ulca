package com.ulca.benchmark.request;

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
public class AsrComputeResponse {
	
	public int count;
    public Data data;
    public String message;
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Data {
    	public String source;
    }
}
