package com.xk857.main.boot.config;

import com.xk857.main.boot.annotation.WorkshopDemo;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 客户端基础配置
 */
@Configuration
@WorkshopDemo(scene = "Redisson接入", focus = "读取application.yml的Redis配置")
public class RedissonConfig {

    @Value("${spring.data.redis.host:127.0.0.1}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private int port;

    @Value("${spring.data.redis.password:}")
    private String password;

    @Value("${spring.data.redis.database:0}")
    private int database;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        String addr = String.format("redis://%s:%d", host, port);
        Config config = new Config();
        var single = config.useSingleServer()
                .setAddress(addr)
                .setDatabase(database)
                .setConnectionMinimumIdleSize(2)
                .setConnectionPoolSize(16);
        if (password != null && !password.isEmpty()) {
            single.setPassword(password);
        }
        return Redisson.create(config);
    }
}
