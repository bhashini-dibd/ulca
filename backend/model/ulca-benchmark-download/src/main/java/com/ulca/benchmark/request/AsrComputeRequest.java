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
public class AsrComputeRequest {
	
	String callbackUrl;
	String filePath;
	String sourceLanguage;
}
