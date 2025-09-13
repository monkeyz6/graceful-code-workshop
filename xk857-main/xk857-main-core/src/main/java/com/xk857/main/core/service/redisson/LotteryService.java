package com.xk857.main.core.service.redisson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 公平锁 - 摇号队列
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LotteryService {

    private final RedissonClient redissonClient;
    private static final String FAIR_LOCK_KEY = "lottery_lock";
    // 模拟摇号名单（顺序性）
    private static final List<String> QUEUE = new CopyOnWriteArrayList<>();

    public String applyForLottery(String userId) {
        RLock fairLock = redissonClient.getFairLock(FAIR_LOCK_KEY);
        fairLock.lock();
        try {
            QUEUE.add(userId);
            log.info("用户 {} 按顺序入池，当前顺序: {}", userId, QUEUE);
            return "queued#" + userId;
        } finally {
            fairLock.unlock();
        }
    }

    public List<String> listQueue() { return QUEUE; }
}

