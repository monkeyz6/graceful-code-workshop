package com.xk857.main.core.pay.decorator;

import com.xk857.main.api.annotation.KeyComponent;
import com.xk857.main.api.pay.dto.Order;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * VIP折扣装饰器：示例中用内存VIP表做判断。
 */
@Slf4j
@KeyComponent
public final class DiscountPaymentDecorator extends PaymentServiceDecorator {
    private final BigDecimal discountRate; // 如 0.9 表示九折

    // 模拟VIP用户集合（仅示例）。
    private static final Set<Long> VIP_USERS = ConcurrentHashMap.newKeySet();
    static {
        VIP_USERS.add(1L);
        VIP_USERS.add(2L);
    }

    public DiscountPaymentDecorator(com.xk857.main.api.pay.PaymentService delegate, BigDecimal discountRate) {
        super(delegate);
        this.discountRate = discountRate;
    }

    @Override
    public void pay(Order order) {
        if (order.getUserId() != null && VIP_USERS.contains(order.getUserId())) {
            BigDecimal old = order.getAmount();
            if (old != null) {
                BigDecimal neo = old.multiply(discountRate).setScale(2, RoundingMode.HALF_UP);
                order.setAmount(neo);
                log.info("[折扣] 用户 {} 为VIP，金额 {} -> {}", order.getUserId(), old, neo);
            }
        }
        super.pay(order);
    }
}

