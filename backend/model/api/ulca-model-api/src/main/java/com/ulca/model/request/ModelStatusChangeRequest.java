package com.ulca.model.request;

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
public class ModelStatusChangeRequest {
	
	private String userId;
	private String modelId;
	private StatusEnum status;

	public enum StatusEnum {
	    published, unpublished
	}
	
}
