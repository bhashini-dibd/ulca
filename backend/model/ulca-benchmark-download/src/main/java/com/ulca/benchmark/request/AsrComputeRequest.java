package com.ulca.benchmark.request;

import io.swagger.model.ModelTask;
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
public class AsrComputeRequest {
	//new
	String modelId;
	String userId;
	//String audioContent;
	String task;
	String source;
	String audioUri;
	//old
	//String callbackUrl;
	//String filePath;
	//String sourceLanguage;
	
}
