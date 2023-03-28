package com.ulca.model.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
	
	private String code;
	private String message;
	private Date timestamp;
	
	
	public ErrorDetails (String message , Date timestamp) {
		this.message=message;
		this.timestamp=timestamp;
		
	}
	
}
