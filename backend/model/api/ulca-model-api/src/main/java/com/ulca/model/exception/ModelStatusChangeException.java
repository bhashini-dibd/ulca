package com.ulca.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModelStatusChangeException extends RuntimeException {

	String status;
	
	public ModelStatusChangeException(String message, String status) {
		super(message);
		this.status = status;
	}
}
