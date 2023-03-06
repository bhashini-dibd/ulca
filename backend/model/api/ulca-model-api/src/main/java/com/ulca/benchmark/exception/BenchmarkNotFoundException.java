package com.ulca.benchmark.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BenchmarkNotFoundException extends RuntimeException {

	public BenchmarkNotFoundException(String message) {
		super(message);
	}

}
