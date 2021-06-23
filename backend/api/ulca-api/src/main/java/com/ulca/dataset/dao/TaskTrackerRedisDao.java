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
   
    


   
   
   public  Map<String,  Map< String, String >> findAll() {
	   
	   System.out.println("logging before fetching all entries ");
	   
	   Set<String> keyList = redisTemplate.keys(Prefix+"*");
	   
	   
	   
	   Map<String,  Map<String, String >> map = new HashMap<String,  Map< String, String >>();
	   
	   
	   for(String key : keyList) {
		   
		   String  serviceRequestNumber  = redisTemplate.opsForHash().get(key, "serviceRequestNumber")+"";
		   
		   String  ingestComplete  = redisTemplate.opsForHash().get(key, "ingestComplete")+"";
		   
		   String count  = redisTemplate.opsForHash().get(key, "count")+"";
		   
		   String ingestSuccess  = redisTemplate.opsForHash().get(key, "ingestSuccess")+"";
		   
		   String ingestError  = redisTemplate.opsForHash().get(key, "ingestError")+"";
		   
		   String publishSuccess  = redisTemplate.opsForHash().get(key, "publishSuccess")+"";
		   
		   
		   String publishError  = redisTemplate.opsForHash().get(key, "publishError")+"";
		   
		   String validateError  = redisTemplate.opsForHash().get(key, "validateError")+"";
		   
		   String validateSuccess  = redisTemplate.opsForHash().get(key, "validateSuccess")+"";
		   
		   Map<String, String > innerMap = new HashMap<String, String>();
		   
		   innerMap.put("serviceRequestNumber", serviceRequestNumber);
		   innerMap.put("ingestComplete", ingestComplete);
		   
		   innerMap.put("count", count);
		   innerMap.put("ingestSuccess", ingestSuccess);
		   innerMap.put("ingestError", ingestError);
		   innerMap.put("publishSuccess", publishSuccess);
		   innerMap.put("publishError", publishError);
		   innerMap.put("validateError", validateError);
		   innerMap.put("validateSuccess", validateSuccess);
		   
		   map.put(serviceRequestNumber, innerMap);
		   
		   
		   log.info("printing the values  ");
		   
		 
		   
		   
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
	   
	   
	   log.info("intialize values");
	   
	   final String key = Prefix+serviceRequestNumber;
	   
	  
	   redisTemplate.opsForHash().put(key, "serviceRequestNumber", serviceRequestNumber);
	   redisTemplate.opsForHash().put(key, "ingestComplete", "0");
	   redisTemplate.opsForHash().put(key, "count",  "0");
	   redisTemplate.opsForHash().put(key, "ingestError",  "0");
	   
	   redisTemplate.opsForHash().put(key, "ingestSuccess",  "0");
	   redisTemplate.opsForHash().put(key, "validateError",  "0");
	   
	   redisTemplate.opsForHash().put(key, "validateSuccess",  "0");
	   
	   redisTemplate.opsForHash().put(key, "publishError",  "0");
	   
	   redisTemplate.opsForHash().put(key, "publishSuccess",  "0");
	   
	   
	  }
   
   public void increment(String  serviceRequestNumber,String key ) {
	   System.out.println("calling the increment value");
	   redisTemplate.opsForHash().increment(Prefix+serviceRequestNumber, key, 1);
	   
	  }
   
   public void setCountAndIngestComplete(String  serviceRequestNumber,int count ) {
	   
	   redisTemplate.opsForHash().put(Prefix+serviceRequestNumber, "count", String.valueOf(count));
	   redisTemplate.opsForHash().put(Prefix+serviceRequestNumber, "ingestComplete", String.valueOf(1));
	   
	  }
   
 
   
   public void delete(String serviceRequestNumber) {
	   log.info("calling the delete  operation"); 
	   redisTemplate.delete(Prefix+serviceRequestNumber);
   }
}
