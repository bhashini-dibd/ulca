package com.ulca.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RedisHealthCheckService {
	
    @Autowired
    @Qualifier("redisTemplate3")
    private  RedisTemplate<String, Object> redisTemplate;

    public boolean isRedisUp() {
        try {
            // Perform a simple operation like setting a key
            redisTemplate.opsForValue().set("health_check_key", "test");
            log.info("redis is working...");
            return true;
        } catch (Exception e) {
            // If an exception is caught, Redis is down
        	log.info("redis is not working...");
            return false;
        }
    }
}
