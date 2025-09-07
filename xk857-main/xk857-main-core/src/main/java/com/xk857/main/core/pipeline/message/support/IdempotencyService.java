package com.xk857.main.core.pipeline.message.support;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 幂等校验（内存模拟，5分钟内相同requestId拒绝重复）
 */
@Service
public class IdempotencyService {
    private static final long TTL_MS = 5 * 60 * 1000L;
    private final Map<String, Long> seen = new ConcurrentHashMap<>();

    public synchronized boolean firstSeen(String requestId) {
        long now = Instant.now().toEpochMilli();
        // 清理过期
        seen.entrySet().removeIf(e -> now - e.getValue() > TTL_MS);
        // 首次出现返回true，并记录
        return seen.putIfAbsent(requestId, now) == null;
    }
}

