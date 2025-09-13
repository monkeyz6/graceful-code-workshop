package com.xk857.main.core.service.redisson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.RedissonRedLock;
import org.springframework.stereotype.Service;

/**
 * 红锁 RedLock - 世界BOSS 刷新
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorldBossService {

    private final RedissonClient redissonClient;

    public String spawn() {
        // 演示使用同一 redis 实例生成多个锁（真实生产应为多个独立 master）
        RLock l1 = redissonClient.getLock("world_boss_spawn_lock");
        RLock l2 = redissonClient.getLock("world_boss_spawn_lock#2");
        RLock l3 = redissonClient.getLock("world_boss_spawn_lock#3");
        RedissonRedLock redLock = new RedissonRedLock(l1, l2, l3);
        try {
            if (redLock.tryLock()) {
                log.info("红锁获取成功，本节点负责刷新BOSS");
                return "SPAWNED";
            }
            return "BUSY";
        } finally {
            try { redLock.unlock(); } catch (Exception ignored) {}
        }
    }
}
