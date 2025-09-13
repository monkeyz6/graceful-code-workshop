package com.xk857.main.boot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 客户端配置（使用 application.yml 中的 redisson.* 配置）
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
@RequiredArgsConstructor
public class RedissonConfig {

    private final RedissonProperties props;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        var single = config.useSingleServer()
                .setAddress(props.getAddress())
                .setDatabase(props.getDatabase());
        if (props.getPassword() != null && !props.getPassword().isBlank()) {
            single.setPassword(props.getPassword());
        }
        if (props.getTimeout() != null) {
            single.setTimeout(props.getTimeout());
        }
        RedissonClient client = Redisson.create(config);
        log.info("RedissonClient 初始化完成：{} db={}", props.getAddress(), props.getDatabase());
        return client;
    }
}
