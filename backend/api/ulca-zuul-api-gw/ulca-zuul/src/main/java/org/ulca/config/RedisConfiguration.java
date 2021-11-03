package org.ulca.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value(value = "${redis.url}")
    private String host;

    @Value(value = "${redis.port}")
    private String port;

    @Value(value = "${redis.pass}")
    private String pass;

    @Bean
    public JedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        logger.info("host: {}, port: {}, pass: {}", host, port, pass);
        configuration.setHostName(host);
        configuration.setPort(Integer.parseInt(port));
        configuration.setPassword(pass);
        configuration.setDatabase(10);
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(connectionFactory());
        redisTemplate.setKeySerializer( new StringRedisSerializer() );
        redisTemplate.setValueSerializer(new StringRedisSerializer() );
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer( new StringRedisSerializer() );
        return redisTemplate;
    }

    @Bean
    RedisClient redisClient() {
        logger.info("host: {}, port: {}, pass: {}", this.host, this.port, this.pass);
        RedisURI uri = RedisURI.Builder.redis(this.host, Integer.parseInt(this.port))
                .withPassword(this.pass)
                .build();
        return RedisClient.create(uri);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) { this.host = host; }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
