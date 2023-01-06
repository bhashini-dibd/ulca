package com.ulca.model.service.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModelComputeException extends RuntimeException {

	String errorCode;
	HttpStatus status;
	
	public ModelComputeException(String errorCode, String message, HttpStatus status) {
		super(message);
		this.status = status;
		this.errorCode = errorCode;
		
	}
	
	
}
