package com.ulca.dataset.dao;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.ulca.dataset.model.TaskTrackerRedis;

@Repository
public class TaskTrackerRedisDao {

	
	public static final String HASH_KEY = "ServiceRequestNumber";
	
    
	@Autowired
    RedisTemplate<String, Object> redisTemplate;
    
    

    public TaskTrackerRedis save(TaskTrackerRedis task){
    	redisTemplate.opsForHash().put(HASH_KEY,task.getServiceRequestNumber(),task);
    	
        return task;
    }
    
    public TaskTrackerRedis findById(String serviceRequestNumber){
        return (TaskTrackerRedis) redisTemplate.opsForHash().get(HASH_KEY,serviceRequestNumber);
    }
    public Map<Object, Object> findAll(){
    	
        return  redisTemplate.opsForHash().entries(HASH_KEY);
        
    }


    public String deleteProduct(String serviceRequestNumber){
    	redisTemplate.opsForHash().delete(HASH_KEY,serviceRequestNumber);
        return "product removed !!";
    }
    
    
    public Object getValue( final String key ) {
        return redisTemplate.opsForValue().get( key );
        
    }

    public void setValue( final String key, final String value ) {
    	redisTemplate.opsForValue().set( key, value );
    }
    
}
