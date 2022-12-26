//package com.ulca.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//@EnableRedisRepositories
//public class RedisConfig {
//	
//	@Value(value = "${redis.url}")
//    private String redisHost;
//	
//	@Value(value = "${redis.port}")
//    private String redisPort;
//	
//	@Value(value = "${redis.pass}")
//    private String redisPass;
//
//    @Bean
//    public JedisConnectionFactory connectionFactory() {
//        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//        configuration.setHostName(redisHost);
//        configuration.setPort(new Integer(redisPort));
//        configuration.setPassword(redisPass);
//        configuration.setDatabase(0);
//        return new JedisConnectionFactory(configuration);
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        
//    	final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
//        redisTemplate.setConnectionFactory(connectionFactory());
//    	redisTemplate.setKeySerializer( new StringRedisSerializer() );
//        redisTemplate.setValueSerializer(new StringRedisSerializer() );
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer( new StringRedisSerializer() );
//        
//        return redisTemplate;
//    }
//    
//    
//
//}