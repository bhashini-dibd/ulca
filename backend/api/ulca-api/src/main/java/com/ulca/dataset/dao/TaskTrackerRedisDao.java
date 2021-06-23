package com.ulca.dataset.dao;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.ulca.dataset.model.TaskTrackerRedis;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskTrackerRedisDao {

	
	public static final String Prefix = "ServiceRequestNumber_";
	
    
	@Autowired
    RedisTemplate<String, Object> redisTemplate;
   
    


   
   
   public  Map<String,  Map< Object, Object >> findAll() {
	   
	   System.out.println("logging before fetching all entries ");
	   
	   Set<String> keyList = redisTemplate.keys(Prefix+"*");
	   
	   
	   
	   Map<String,  Map< Object, Object >> map = new HashMap<String,  Map< Object, Object >>();
	   
	   
	   for(String key : keyList) {
		   
		   
		   Integer ingestComplete  = Integer.parseInt(redisTemplate.opsForHash().get(key, "ingestComplete").toString());
		   
		   Integer count  = Integer.parseInt(redisTemplate.opsForHash().get(key, "count").toString());
		   
		   Integer ingestSuccess  = Integer.parseInt(redisTemplate.opsForHash().get(key, "ingestSuccess").toString());
		   
		   Integer ingestError  = Integer.parseInt(redisTemplate.opsForHash().get(key, "ingestError").toString());
		   
		   Integer publishSuccess  = Integer.parseInt(redisTemplate.opsForHash().get(key, "publishSuccess").toString());
		   
		   
		   Integer publishError  = Integer.parseInt(redisTemplate.opsForHash().get(key, "publishError").toString());
		   
		   Integer validateError  = Integer.parseInt(redisTemplate.opsForHash().get(key, "validateError").toString());
		   
		   Integer validateSuccess  = Integer.parseInt(redisTemplate.opsForHash().get(key, "validateSuccess").toString());
		  
		   
		   log.info("printing the vlues ");
		   log.info("ingestComplete " + ingestComplete);
		   
		  log.info("count " + count);
		  log.info("ingestSuccess " + ingestSuccess);
		  log.info("ingestError " + ingestError);
		  log.info("publishSuccess " + publishSuccess);
		  log.info("publishError " + publishError);
		  log.info("validateError " + validateError);
		  log.info("validateSuccess " + validateSuccess);
		  
		   
		   
	   }
	   
	   
	   return map;
	   
   }
   
  
  
   
   public void intialize( final String  serviceRequestNumber ) {
	   
	   
	   System.out.println("intialize values");
	   
	   final String key = Prefix+serviceRequestNumber;
	   
	   final Map< String, Object > properties = new HashMap< String, Object >();

	   properties.put( "serviceRequestNumber", serviceRequestNumber);
	   properties.put( "ingestComplete", 0 );
	   properties.put( "count", 0 );
	   
	   properties.put( "ingestError", 0 );
	   properties.put( "ingestSuccess", 0 );
	   properties.put( "validateError", 0 );
	   
	   properties.put( "validateSuccess", 0 );
	   properties.put( "publishError", 0 );
	   properties.put( "publishSuccess", 0 );

	   redisTemplate.opsForHash().putAll( key, properties);
	  }
   
   public void increment(String  serviceRequestNumber,String key ) {
	   System.out.println("calling the increment value");
	   redisTemplate.opsForHash().increment(Prefix+serviceRequestNumber, key, 1);
	   
	  }
   
   public void setCountAndIngestComplete(String  serviceRequestNumber,int count ) {
	   
	   redisTemplate.opsForHash().put(Prefix+serviceRequestNumber, "count", count);
	   redisTemplate.opsForHash().put(Prefix+serviceRequestNumber, "ingestComplete", 1);
	   
	  }
   
  public String getSuccess(String serviceRequestNumber) {
	  
	  System.out.println("get success value ");
	  System.out.println(redisTemplate.opsForHash().get( Prefix+serviceRequestNumber, "ingestSuccess" ));
	  String name = (String) redisTemplate.opsForHash().get( Prefix+serviceRequestNumber, "ingestSuccess" );
	  
	  
	  System.out.println("getting list form redisTemplate");
	  
	  Set<String> list = redisTemplate.keys("*");
	  
	  System.out.println(list);
	  
	  
	  
	  return name;
  }
   
   public void delete(String serviceRequestNumber) {
	   log.info("calling the delete  operation"); 
	   redisTemplate.delete(Prefix+serviceRequestNumber);
   }
}
