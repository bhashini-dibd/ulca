package com.ulca.dataset.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("ServiceRequestNumber")
public class TaskTrackerRedis implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    private String serviceRequestNumber;
	private int ingestComplete;
	private int count;
	private int ingestError;
	private int ingestSuccess;
	private int validateError;
	private int validateSuccess;
	private int publishError;
	private int publishSuccess;  
	
	
   
    
}
