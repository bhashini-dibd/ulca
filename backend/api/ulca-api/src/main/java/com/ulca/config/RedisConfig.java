package com.ulca.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.ulca.dataset.model.TaskTrackerRedis;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
	
	@Value(value = "${REDIS_URL}")
    private String redisHost;
	
	@Value(value = "${REDIS_PORT}")
    private String redisPort;
	
	@Value(value = "${REDIS_PASS}")
    private String redisPass;

    @Bean
    public JedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost);
        configuration.setPort(new Integer(redisPort));
        configuration.setPassword(redisPass);
        configuration.setDatabase(0);
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        
        //template.setKeySerializer(new StringRedisSerializer());
        //template.setHashKeySerializer(new StringRedisSerializer());
        //template.setHashKeySerializer(new JdkSerializationRedisSerializer());
        
       // template.setValueSerializer(new JdkSerializationRedisSerializer());
        
        //template.setValueSerializer(new Jackson2JsonRedisSerializer(TaskTrackerRedis.class));
        
        template.setKeySerializer( new StringRedisSerializer() );
        //template.setHashValueSerializer( new GenericToStringSerializer< Object >( Object.class ) );
        template.setValueSerializer( new GenericToStringSerializer< Object >( Object.class ) );
        
        
        //template.setEnableTransactionSupport(true);
       // template.afterPropertiesSet();
        return template;
    }

}