package org.ulca.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spring.redis")
public class RedisConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String host;
    private String port;
    private String pass;

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

    public void setHost(String host) {
        this.host = host;
    }

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
