package com.xk857.main.boot.listener;

import com.xk857.main.boot.annotation.WorkshopDemo;
import com.xk857.main.boot.cache.LocalProductCache;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@WorkshopDemo(scene = "RTopic广播站", focus = "本地缓存失效通知")
public class CacheInvalidationListener {

    private final RedissonClient redissonClient;
    private final LocalProductCache localCache;

    @PostConstruct
    public void subscribe() {
        RTopic topic = redissonClient.getTopic("cache-invalidation-topic");
        topic.addListener(String.class, (channel, message) -> {
            log.info("收到广播: {}", message);
            if (message.startsWith("product:price_changed:")) {
                String productId = message.substring("product:price_changed:".length());
                localCache.invalidate(productId);
            }
        });
    }
}

