package com.ulca.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PipelineValidationException extends RuntimeException {
    
	HttpStatus status;
	
	public PipelineValidationException(String message) {
		super(message);
	}
	
	
	public PipelineValidationException(String message,HttpStatus status) {
		super(message);
		this.status=status;
	}
	
	
}
