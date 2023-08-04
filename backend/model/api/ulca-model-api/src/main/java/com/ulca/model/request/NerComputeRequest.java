package com.ulca.model.request;

import io.swagger.model.OCRRequest;
import io.swagger.model.TTSRequest;
import io.swagger.model.TranslationRequest;
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
public class NerComputeRequest implements ModelComputeRequest {
	 public String modelId;
		
		public String task;
		
		public String userId;
		
		private TranslationRequest request = null;
}
