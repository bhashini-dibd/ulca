package com.ulca.model.request;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.model.Gender;
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
public class ModelComputeRequest {

	@NotBlank(message="modelId is required")
	public String modelId;
	
	public String task;
	public Gender gender;
    public List<Input> input;
    public String audioUri;
    public byte[] audioContent;
    public String imageUri;
    private BigDecimal speed;
    private BigDecimal duration;
    private String audioBase64;
	  
}
