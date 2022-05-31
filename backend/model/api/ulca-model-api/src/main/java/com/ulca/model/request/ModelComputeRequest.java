package com.ulca.model.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

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
	  
}
