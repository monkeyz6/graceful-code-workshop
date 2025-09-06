package com.xk857.framework.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * 开启Spring缓存（默认使用ConcurrentMap缓存，演示足够）
 */
@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {
}

