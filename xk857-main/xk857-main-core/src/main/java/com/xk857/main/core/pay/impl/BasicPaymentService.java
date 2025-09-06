package com.xk857.main.core.pay.impl;

import com.xk857.main.api.annotation.KeyComponent;
import com.xk857.main.api.pay.PaymentService;
import com.xk857.main.api.pay.dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 核心支付实现：只做支付本身，其他增强交给装饰器。
 * 为了演示，包含一个内存中的“尝试次数计数器”和“失败策略”。
 */
@Slf4j
@Service
@KeyComponent
public class BasicPaymentService implements PaymentService {

    // 记录每个uniqueKey的核心调用次数，便于在Demo中观察。
    private final Map<String, Integer> attemptCounter = new ConcurrentHashMap<>();

    @Override
    public void pay(Order order) {
        attemptCounter.merge(order.getUniqueKey(), 1, Integer::sum);
        log.info("[核心] 执行支付: key={}, user={}, amount={}, remark={}",
                order.getUniqueKey(), order.getUserId(), order.getAmount(), order.getRemark());

        // 模拟失败策略：如果 remark=FAIL_FIRST_2，则前两次抛异常，后续成功
        if ("FAIL_FIRST_2".equalsIgnoreCase(order.getRemark())) {
            int n = attemptCounter.get(order.getUniqueKey());
            if (n <= 2) {
                log.warn("[核心] 模拟失败，第 {} 次抛出异常", n);
                throw new RuntimeException("模拟下游失败，请重试");
            }
        }

        // 模拟正常处理
        log.info("[核心] 第 {} 次尝试：支付成功", attemptCounter.get(order.getUniqueKey()));
    }

    public int getAttemptCount(String uniqueKey) {
        return attemptCounter.getOrDefault(uniqueKey, 0);
    }

    public void resetAttempt(String uniqueKey) {
        attemptCounter.remove(uniqueKey);
    }
}

