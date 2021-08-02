package com.ulca.model.request;

import java.util.List;

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

	public String modelId;
	public String task;
    public List<Input> input;
    public String audioUri;
	  
}
