package com.xk857.main.core.pay.decorator;

import com.xk857.main.api.annotation.KeyComponent;
import com.xk857.main.api.pay.dto.Order;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志装饰器。
 */
@Slf4j
@KeyComponent
public final class LoggingPaymentDecorator extends PaymentServiceDecorator {
    public LoggingPaymentDecorator(com.xk857.main.api.pay.PaymentService delegate) {
        super(delegate);
    }

    @Override
    public void pay(Order order) {
        log.info("[日志] 支付请求开始: {}", order);
        try {
            super.pay(order);
            log.info("[日志] 支付请求结束: 成功");
        } catch (RuntimeException e) {
            log.error("[日志] 支付请求结束: 失败: {}", e.getMessage());
            throw e;
        }
    }
}

