package com.ulca.dataset.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
//@RedisHash("ServiceRequestNumber")
public class TaskTrackerRedis implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    private String serviceRequestNumber;
	private long ingestComplete;
	private long count;
	private long ingestError;
	private long ingestSuccess;
	private long validateError;
	private long validateSuccess;
	private long publishError;
	private long publishSuccess;  
	
	
   
    
}
