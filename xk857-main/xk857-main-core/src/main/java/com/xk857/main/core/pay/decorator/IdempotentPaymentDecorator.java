package com.xk857.main.core.pay.decorator;

import com.xk857.main.api.annotation.KeyComponent;
import com.xk857.main.api.pay.dto.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 幂等装饰器：按 uniqueKey 做一次性处理。
 * 这里简单记录“是否处理过”，并不返回历史结果，仅示范。
 */
@Slf4j
@KeyComponent
public final class IdempotentPaymentDecorator extends PaymentServiceDecorator {
    private final Map<String, Boolean> processed = new ConcurrentHashMap<>();

    public IdempotentPaymentDecorator(com.xk857.main.api.pay.PaymentService delegate) {
        super(delegate);
    }

    @Override
    public void pay(Order order) {
        String key = order.getUniqueKey();
        if (processed.containsKey(key)) {
            log.warn("[幂等] {} 重复请求，直接返回（不下发核心）", key);
            return;
        }
        log.info("[幂等] {} 首次处理，放行", key);
        super.pay(order);
        // 仅在成功后标记已处理
        processed.putIfAbsent(key, Boolean.TRUE);
    }
}
