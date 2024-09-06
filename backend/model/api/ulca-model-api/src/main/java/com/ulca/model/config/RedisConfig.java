package com.ulca.model.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import com.ulca.benchmark.service.BenchmarkService;

import io.swagger.pipelinerequest.PipelineResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableRedisRepositories
public class RedisConfig {
	
	@Value(value = "${redis.url}")
    private String redisHost;
	
	@Value(value = "${redis.port}")
    private String redisPort;
	
	@Value(value = "${redis.pass}")
    private String redisPass;
	
	@Value(value = "${redis.db}")
    private Integer redisDb;

    @Bean
    public JedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        log.info("Intializing redis connection...");
        log.info("redisHost :: "+redisHost);
        log.info("redisPort :: "+redisPort);
        log.info("redisPass :: "+redisPass);
        log.info("redisDb :: "+redisDb);
        configuration.setHostName(redisHost);
        configuration.setPort(new Integer(redisPort));
        configuration.setPassword(redisPass);
        configuration.setDatabase(redisDb);

        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, PipelineResponse> redisTemplate() {
        
    	final RedisTemplate<String, PipelineResponse> redisTemplate = new RedisTemplate<String, PipelineResponse>();
        redisTemplate.setConnectionFactory(connectionFactory());
    	redisTemplate.setKeySerializer( new StringRedisSerializer() );
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer() );
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer( new GenericJackson2JsonRedisSerializer() );
        
        return redisTemplate;
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate2() {
        
    	final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(connectionFactory());
    	redisTemplate.setKeySerializer( new StringRedisSerializer() );
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer() );
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer( new GenericJackson2JsonRedisSerializer() );
        
        return redisTemplate;
    }
    

}