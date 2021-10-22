package com.ulca.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RequestParamValidationException extends RuntimeException {

	public RequestParamValidationException(String message) {
		super(message);
	}

}
