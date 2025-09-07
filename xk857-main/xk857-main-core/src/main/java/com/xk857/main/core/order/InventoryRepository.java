package com.xk857.main.core.order;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模拟库存仓库（内存实现）
 */
@Slf4j
@Repository
public class InventoryRepository {
    private final Map<Long, Integer> stock = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        // 预置一些商品库存
        stock.put(1001L, 10);
        stock.put(1002L, 3);
        stock.put(1003L, 0);
        log.info("初始化模拟库存: {}", stock);
    }

    public int getStock(Long productId) {
        return stock.getOrDefault(productId, 0);
    }

    public boolean reserve(Long productId, int qty) {
        return stock.compute(productId, (k, v) -> {
            int cur = v == null ? 0 : v;
            if (cur < qty) {
                return cur;
            }
            return cur - qty;
        }) >= 0 && (getStock(productId) >= 0);
    }

    public void release(Long productId, int qty) {
        stock.merge(productId, qty, Integer::sum);
    }
}
