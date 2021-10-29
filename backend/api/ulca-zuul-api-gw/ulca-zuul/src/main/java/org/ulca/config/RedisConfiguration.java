package org.ulca.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spring.redis")
public class RedisConfiguration {
    private String host;
    private int port;

    @Bean
    RedisClient redisClient() {
        RedisURI uri = RedisURI.Builder.redis(this.host, this.port)
                .build();
        return RedisClient.create(uri);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
