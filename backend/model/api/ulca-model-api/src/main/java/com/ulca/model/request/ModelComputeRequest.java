package com.ulca.model.request;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.model.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(defaultValue = "6411a27dcc538940941d64c6")
	@NotBlank(message="modelId is required")
	public String modelId;
	
    @Schema(defaultValue = "translation")

	public String task;
	public Gender gender;
	
    public List<Input> input;
    @Schema(defaultValue = "audioUri.wav")

    public String audioUri;
    @Schema(defaultValue = "audioContent")

    public byte[] audioContent;
    
    @Schema(defaultValue ="image.png")

    public String imageUri;
    @Schema(defaultValue = "162632")

    private BigDecimal speed;
    @Schema(defaultValue = "641344")

    private BigDecimal duration;
	  
}
