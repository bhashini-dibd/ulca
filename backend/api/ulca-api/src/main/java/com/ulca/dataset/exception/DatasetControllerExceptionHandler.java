package com.ulca.dataset.exception;

import java.util.Date;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RestController
public class DatasetControllerExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	  public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		
		String errorCode = "something went wrong";
		log.info("inside DatasetControllerExceptionHandler :: handleAllExceptions ");
		ex.printStackTrace();
	    ErrorDetails errorDetails = new ErrorDetails(errorCode,ex.getMessage(), new Date());
	    		
	    return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	
	@ExceptionHandler(DuplicateKeyException.class)
	  public final ResponseEntity<Object> handleDuplicateKeyExceptions(DuplicateKeyException ex, WebRequest request) {
		
		String errorCode = "Duplicate Key Error"; 
		ErrorDetails errorDetails = new ErrorDetails(errorCode,ex.getMessage(), new Date());
	    return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	  }

	
	  @ExceptionHandler(ServiceRequestNumberNotFoundException.class)
	  public final ResponseEntity<Object> handleUserNotFoundException(ServiceRequestNumberNotFoundException ex, WebRequest request) {
	    
		  String errorCode = "ServiceRequestNumber Not Found"; 
		  ErrorDetails errorDetails = new ErrorDetails(errorCode,ex.getMessage(), new Date());
		  
	      return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
	  }

	  
}
