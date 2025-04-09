package com.ulca;


import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisMultiDbFlushOnStart {

    @Resource(name = "jedisConnectionFactoryDb1")
    private JedisConnectionFactory jedisConnectionFactoryDb1;

    @Resource(name = "jedisConnectionFactoryDb2")
    private JedisConnectionFactory jedisConnectionFactoryDb2;

    @Resource(name = "jedisConnectionFactoryDb3")
    private JedisConnectionFactory jedisConnectionFactoryDb3;

    @EventListener(ApplicationReadyEvent.class)
    public void flushRedisDatabases() {
        log.info("Flushing Redis DBs 1, 2, and 3 on startup...");

        try {
            jedisConnectionFactoryDb1.getConnection().flushDb();
            log.info("✅ Redis DB 1 flushed.");
        } catch (Exception e) {
            log.error("❌ Failed to flush Redis DB 1", e);
        }

        try {
            jedisConnectionFactoryDb2.getConnection().flushDb();
            log.info("✅ Redis DB 2 flushed.");
        } catch (Exception e) {
            log.error("❌ Failed to flush Redis DB 2", e);
        }

        try {
            jedisConnectionFactoryDb3.getConnection().flushDb();
            log.info("✅ Redis DB 3 flushed.");
        } catch (Exception e) {
            log.error("❌ Failed to flush Redis DB 3", e);
        }
    }
}


