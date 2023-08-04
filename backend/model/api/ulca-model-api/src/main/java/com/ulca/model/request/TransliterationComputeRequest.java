package com.ulca.model.request;

import io.swagger.model.TranslationRequest;
import io.swagger.model.TransliterationRequest;
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
public class TransliterationComputeRequest implements ModelComputeRequest {

	
	    public String modelId;
		
		public String task;
		
		public String userId;
		
		private TransliterationRequest request = null;
}
