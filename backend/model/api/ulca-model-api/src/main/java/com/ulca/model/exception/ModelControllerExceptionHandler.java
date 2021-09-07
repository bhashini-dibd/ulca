package com.ulca.model.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.ulca.benchmark.exception.BenchmarkNotAllowedException;
import com.ulca.benchmark.exception.BenchmarkNotFoundException;
import com.ulca.model.exception.ErrorDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ModelControllerExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	  public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		
		String errorCode = "something went wrong";
		log.info("Inside ModelControllerExceptionHandler :: handleAllExceptions ");
		ex.printStackTrace();
	    ErrorDetails errorDetails = new ErrorDetails(errorCode,ex.getMessage(), new Date());
	    		
	    return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public  ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
     
		List<String> details = new ArrayList<>();
      for(ObjectError error : ex.getBindingResult().getAllErrors()) {
          details.add(error.getDefaultMessage());
      }
      ErrorDetails errorDetails = new ErrorDetails("Validation Failed",details.toString(), new Date());
      return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
  }
	
	@ExceptionHandler(DuplicateKeyException.class)
	  public final ResponseEntity<Object> handleDuplicateKeyExceptions(DuplicateKeyException ex, WebRequest request) {
		
		String errorCode = "Duplicate Key Error"; 
		ErrorDetails errorDetails = new ErrorDetails(errorCode,ex.getMessage(), new Date());
	    return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	  }

	@ExceptionHandler(BenchmarkNotAllowedException.class)
	  public final ResponseEntity<Object> handleBenchmarkNotAllowedException(BenchmarkNotAllowedException ex, WebRequest request) {
		
		String errorCode = "Benchmark already executed"; 
		ErrorDetails errorDetails = new ErrorDetails(errorCode,ex.getMessage(), new Date());
	    return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	  }

	@ExceptionHandler(BenchmarkNotFoundException.class)
	  public final ResponseEntity<Object> handleBenchmarkNotFoundException(BenchmarkNotFoundException ex, WebRequest request) {
		
		String errorCode = "Benchmark Not Found"; 
		ErrorDetails errorDetails = new ErrorDetails(errorCode,ex.getMessage(), new Date());
	    return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	@ExceptionHandler(ModelNotFoundException.class)
	  public final ResponseEntity<Object> handleModelNotFoundException(ModelNotFoundException ex, WebRequest request) {
		
		String errorCode = "Model Not Found"; 
		ErrorDetails errorDetails = new ErrorDetails(errorCode,ex.getMessage(), new Date());
	    return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
}
