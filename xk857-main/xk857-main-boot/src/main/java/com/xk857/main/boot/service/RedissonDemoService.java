package com.xk857.main.boot.service;

import com.xk857.main.boot.annotation.WorkshopDemo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Redisson 常用能力演示：分布式锁、KV、简单限流。
 */
@Slf4j
@Service
@RequiredArgsConstructor
@WorkshopDemo(scene = "Redisson能力演示", focus = "RLock/RBucket/RRateLimiter")
public class RedissonDemoService {

    private final RedissonClient redissonClient;

    public String doWithLock(String key, long leaseSeconds, long bizMillis) throws InterruptedException {
        String lockName = "demo:lock:" + key;
        RLock lock = redissonClient.getLock(lockName);
        boolean ok = lock.tryLock(0, leaseSeconds, TimeUnit.SECONDS);
        if (!ok) {
            return "acquire_lock_failed";
        }
        try {
            log.info("Lock acquired: {}", lockName);
            if (bizMillis > 0) {
                Thread.sleep(bizMillis);
            }
            return "biz_done";
        } finally {
            try {
                lock.unlock();
                log.info("Lock released: {}", lockName);
            } catch (Exception e) {
                log.warn("Unlock failed: {}", e.getMessage());
            }
        }
    }

    public String setAndGet(String key, String value, long ttlSeconds) {
        String bucketKey = "demo:kv:" + key;
        RBucket<String> bucket = redissonClient.getBucket(bucketKey);
        if (ttlSeconds > 0) {
            bucket.set(value, ttlSeconds, TimeUnit.SECONDS);
        } else {
            bucket.set(value);
        }
        return bucket.get();
    }

    public boolean rateLimitTryAcquire(String key, long rate, long intervalSeconds) {
        String limiterKey = "demo:rate:" + key;
        RRateLimiter limiter = redissonClient.getRateLimiter(limiterKey);
        // 初始化限流配置（幂等：已存在则返回false不影响后续tryAcquire）
        limiter.trySetRate(RateType.OVERALL, rate, intervalSeconds, RateIntervalUnit.SECONDS);
        return limiter.tryAcquire();
    }
}

