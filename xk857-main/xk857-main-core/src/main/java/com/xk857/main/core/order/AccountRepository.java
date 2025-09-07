package com.xk857.main.core.order;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模拟账户仓库（内存实现）
 */
@Slf4j
@Repository
public class AccountRepository {
    private final Map<Long, BigDecimal> balance = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        balance.put(1L, new BigDecimal("100.00"));
        balance.put(2L, new BigDecimal("20.00"));
        balance.put(3L, new BigDecimal("0.00"));
        log.info("初始化模拟余额: {}", balance);
    }

    public BigDecimal getBalance(Long userId) {
        return balance.getOrDefault(userId, BigDecimal.ZERO);
    }

    public boolean charge(Long userId, BigDecimal amount) {
        return balance.compute(userId, (k, v) -> {
            BigDecimal cur = v == null ? BigDecimal.ZERO : v;
            if (cur.compareTo(amount) < 0) {
                return cur;
            }
            return cur.subtract(amount);
        }) != null && getBalance(userId).compareTo(BigDecimal.ZERO) >= 0;
    }

    public void refund(Long userId, BigDecimal amount) {
        balance.merge(userId, amount, BigDecimal::add);
    }
}
