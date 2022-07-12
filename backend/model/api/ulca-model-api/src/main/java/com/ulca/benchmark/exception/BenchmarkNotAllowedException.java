package com.ulca.benchmark.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BenchmarkNotAllowedException  extends RuntimeException {

	public BenchmarkNotAllowedException(String message) {
		super(message);
	}

}
