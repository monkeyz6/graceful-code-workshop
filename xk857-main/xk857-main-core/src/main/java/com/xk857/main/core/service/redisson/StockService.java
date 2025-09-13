package com.xk857.main.core.service.redisson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 秒杀库存 - 可重入锁 RLock 示例
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final RedissonClient redissonClient;

    // 模拟库存“数据库”
    private static final Map<String, Integer> STOCK_DB = new ConcurrentHashMap<>();

    public void resetStock(String productId, int stock) {
        STOCK_DB.put(productId, stock);
        log.info("重置库存: {} -> {}", productId, stock);
    }

    public String deductStock(String productId) {
        RLock lock = redissonClient.getLock("lock:stock:" + productId);
        try {
            lock.lock();
            checkStockLevel(productId);

            int stock = STOCK_DB.getOrDefault(productId, 0);
            if (stock > 0) {
                STOCK_DB.put(productId, stock - 1);
                log.info("库存扣减成功，剩余: {}", stock - 1);
                return "OK, remain=" + (stock - 1);
            }
            log.warn("库存不足");
            return "NO_STOCK";
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    private void checkStockLevel(String productId) {
        RLock lock = redissonClient.getLock("lock:stock:" + productId);
        try {
            lock.lock();
            log.debug("检查库存, pid={}", productId);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}

