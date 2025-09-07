package com.xk857.main.core.pipeline.message.support;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单限流（每用户每渠道每分钟最多60次）
 */
@Service
public class RateLimitService {
    private static final int LIMIT = 60;

    private static class Counter {
        long windowStartMs;
        int count;
    }

    private final Map<String, Counter> counters = new ConcurrentHashMap<>();

    public synchronized boolean allow(String key) {
        long now = Instant.now().toEpochMilli();
        long window = now / 60_000; // 分钟窗口
        Counter c = counters.computeIfAbsent(key, k -> new Counter());
        if (c.windowStartMs != window) {
            c.windowStartMs = window;
            c.count = 0;
        }
        if (c.count >= LIMIT) return false;
        c.count++;
        return true;
    }
}

