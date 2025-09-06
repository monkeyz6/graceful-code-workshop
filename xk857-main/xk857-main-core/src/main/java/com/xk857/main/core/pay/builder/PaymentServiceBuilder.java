package com.xk857.main.core.pay.builder;

import com.xk857.main.api.annotation.KeyComponent;
import com.xk857.main.api.pay.PaymentService;
import com.xk857.main.core.pay.decorator.DiscountPaymentDecorator;
import com.xk857.main.core.pay.decorator.IdempotentPaymentDecorator;
import com.xk857.main.core.pay.decorator.LoggingPaymentDecorator;
import com.xk857.main.core.pay.decorator.RetryPaymentDecorator;

import java.math.BigDecimal;

/**
 * 通过Builder固定装饰顺序，避免人为装配错误。
 * 顺序策略：最外层日志 -> 重试 -> 折扣 -> 幂等 -> 核心
 * 说明：幂等靠近核心，作为最后一道防线；控制类（重试）在外层；日志最外层。
 */
@KeyComponent
public class PaymentServiceBuilder {
    private PaymentService base;
    private boolean withLogging;
    private Integer retries;
    private boolean withIdempotency;
    private BigDecimal discountRate;

    public PaymentServiceBuilder base(PaymentService base) {
        this.base = base;
        return this;
    }

    public PaymentServiceBuilder withLogging() {
        this.withLogging = true;
        return this;
    }

    public PaymentServiceBuilder withRetries(int times) {
        this.retries = times;
        return this;
    }

    public PaymentServiceBuilder withIdempotency() {
        this.withIdempotency = true;
        return this;
    }

    public PaymentServiceBuilder withVipDiscount(BigDecimal rate) {
        this.discountRate = rate;
        return this;
    }

    public PaymentService build() {
        if (base == null) throw new IllegalStateException("必须提供基础 PaymentService");

        PaymentService svc = base;

        // 幂等尽量靠近核心
        if (withIdempotency) {
            svc = new IdempotentPaymentDecorator(svc);
        }

        // 业务前置调整（折扣）在幂等外层
        if (discountRate != null) {
            svc = new DiscountPaymentDecorator(svc, discountRate);
        }

        // 控制类（重试）在更外层
        if (retries != null && retries > 0) {
            svc = new RetryPaymentDecorator(svc, retries);
        }

        // 观测类（日志）最外层
        if (withLogging) {
            svc = new LoggingPaymentDecorator(svc);
        }

        return svc;
    }
}

