package com.ulca.model.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import com.ulca.model.response.pipeline.script.PipelineResponseWithScript;

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
	
	@Value(value = "${redis.db2}")
    private Integer redisDb2;
	
	@Value(value = "${redis.db3}")
    private Integer redisDb3;
	
	
	
	   @Bean(name = "jedisConnectionFactoryDb1")
	    public JedisConnectionFactory jedisConnectionFactoryDb1() {
	        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
	        log.info("Initializing redis connection for db1...");
	        configuration.setHostName(redisHost);
	        configuration.setPort(Integer.parseInt(redisPort));
	        configuration.setPassword(redisPass);
	        configuration.setDatabase(redisDb);

	        return new JedisConnectionFactory(configuration);
	    }

	    @Bean(name = "jedisConnectionFactoryDb2")
	    public JedisConnectionFactory jedisConnectionFactoryDb2() {
	        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
	        log.info("Initializing redis connection for db2...");
	        configuration.setHostName(redisHost);
	        configuration.setPort(Integer.parseInt(redisPort));
	        configuration.setPassword(redisPass);
	        configuration.setDatabase(redisDb2);

	        return new JedisConnectionFactory(configuration);
	    }
	
	    @Bean(name = "jedisConnectionFactoryDb3")
	    public JedisConnectionFactory jedisConnectionFactoryDb3() {
	        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
	        log.info("Initializing redis connection for db3...");
	        configuration.setHostName(redisHost);
	        configuration.setPort(Integer.parseInt(redisPort));
	        configuration.setPassword(redisPass);
	        configuration.setDatabase(redisDb3);

	        return new JedisConnectionFactory(configuration);
	    }
	

		/*
		 * @Bean public JedisConnectionFactory connectionFactory(Integer database) {
		 * RedisStandaloneConfiguration configuration = new
		 * RedisStandaloneConfiguration(); log.info("Intializing redis connection...");
		 * log.info("redisHost :: "+redisHost); log.info("redisPort :: "+redisPort);
		 * log.info("redisPass :: "+redisPass); log.info("redisDb :: "+database);
		 * configuration.setHostName(redisHost); configuration.setPort(new
		 * Integer(redisPort)); configuration.setPassword(redisPass);
		 * configuration.setDatabase(database);
		 * 
		 * return new JedisConnectionFactory(configuration); }
		 */
	    
	    
    @Bean(name = "redisDb1")
    public RedisTemplate<String, PipelineResponse> redisTemplate(@Qualifier("jedisConnectionFactoryDb1") JedisConnectionFactory jedisConnectionFactoryDb1) {
        log.info("db1");
    	final RedisTemplate<String, PipelineResponse> redisTemplate = new RedisTemplate<String, PipelineResponse>();
        redisTemplate.setConnectionFactory(jedisConnectionFactoryDb1);
    	redisTemplate.setKeySerializer( new StringRedisSerializer() );
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer() );
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer( new GenericJackson2JsonRedisSerializer() );
        
        return redisTemplate;
    }
    
    @Bean(name = "redisDb2")
    public RedisTemplate<String, PipelineResponse> redisTemplate2(@Qualifier("jedisConnectionFactoryDb2") JedisConnectionFactory jedisConnectionFactoryDb2) {
        log.info("db2");
    	final RedisTemplate<String, PipelineResponse> redisTemplate = new RedisTemplate<String, PipelineResponse>();
        redisTemplate.setConnectionFactory(jedisConnectionFactoryDb2);
    	redisTemplate.setKeySerializer( new StringRedisSerializer() );
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer() );
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer( new GenericJackson2JsonRedisSerializer() );
        
        return redisTemplate;
    }
    
    @Bean(name = "redisDb3")
    public RedisTemplate<String, PipelineResponseWithScript> redisTemplate4(@Qualifier("jedisConnectionFactoryDb3") JedisConnectionFactory jedisConnectionFactoryDb3) {
        log.info("db3");
    	final RedisTemplate<String, PipelineResponseWithScript> redisTemplate = new RedisTemplate<String, PipelineResponseWithScript>();
        redisTemplate.setConnectionFactory(jedisConnectionFactoryDb3);
    	redisTemplate.setKeySerializer( new StringRedisSerializer() );
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer() );
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer( new GenericJackson2JsonRedisSerializer() );
        
        return redisTemplate;
    }
    @Bean(name = "redisTemplate3")
    public RedisTemplate<String, Object> redisTemplate3(@Qualifier("jedisConnectionFactoryDb1") JedisConnectionFactory jedisConnectionFactoryDb1) {
        
    	final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactoryDb1);
    	redisTemplate.setKeySerializer( new StringRedisSerializer() );
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer() );
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer( new GenericJackson2JsonRedisSerializer() );
        
        return redisTemplate;
    }
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, PipelineResponse> defaultRedisTemplate(@Qualifier("jedisConnectionFactoryDb1") JedisConnectionFactory jedisConnectionFactoryDb1) {
        final RedisTemplate<String, PipelineResponse> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactoryDb1);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }

}