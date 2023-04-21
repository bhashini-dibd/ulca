package com.ulca.model.request;

import org.springframework.beans.factory.annotation.Value;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
	
   
	//@ApiModelProperty(value = "userId", example = "example to show")
	@Schema( defaultValue = "parshant@tarento.com")
	private String userId;
	
	@Schema( defaultValue = "63c95855a0e5e81614ff96a7")
    private String modelId;
	
	private StatusEnum status;
	
	@Schema( defaultValue = "testing")
	private String unpublishReason;
	
	public enum StatusEnum {
	    published, unpublished
	}
	
}
