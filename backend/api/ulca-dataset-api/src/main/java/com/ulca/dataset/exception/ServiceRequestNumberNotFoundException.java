package com.ulca.dataset.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServiceRequestNumberNotFoundException extends RuntimeException{

	

	public ServiceRequestNumberNotFoundException(String message) {
		super(message);
	}

	

}
