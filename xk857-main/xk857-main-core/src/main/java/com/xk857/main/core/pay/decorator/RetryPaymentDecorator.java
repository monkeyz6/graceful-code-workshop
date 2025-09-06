package com.xk857.main.core.pay.decorator;

import com.xk857.main.api.annotation.KeyComponent;
import com.xk857.main.api.pay.dto.Order;
import lombok.extern.slf4j.Slf4j;

/**
 * 重试装饰器。
 */
@Slf4j
@KeyComponent
public final class RetryPaymentDecorator extends PaymentServiceDecorator {
    private final int maxRetries;

    public RetryPaymentDecorator(com.xk857.main.api.pay.PaymentService delegate, int maxRetries) {
        super(delegate);
        this.maxRetries = Math.max(0, maxRetries);
    }

    @Override
    public void pay(Order order) {
        int attempt = 0;
        RuntimeException last = null;
        while (attempt <= maxRetries) {
            try {
                attempt++;
                log.warn("[重试] 第 {} 次尝试 (最多 {} 次)", attempt, maxRetries + 1);
                super.pay(order);
                return;
            } catch (RuntimeException e) {
                last = e;
                if (attempt > maxRetries) {
                    log.error("[重试] 已达到最大重试次数，仍失败: {}", e.getMessage());
                    throw e;
                }
            }
        }
        if (last != null) throw last;
    }
}

