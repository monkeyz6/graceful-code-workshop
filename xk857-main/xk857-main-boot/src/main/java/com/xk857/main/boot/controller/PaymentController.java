package com.xk857.main.boot.controller;

import com.xk857.main.api.annotation.KeyComponent;
import com.xk857.main.api.pay.dto.Order;
import com.xk857.main.core.pay.builder.PaymentServiceBuilder;
import com.xk857.main.core.pay.impl.BasicPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 支付装饰器演示控制器。
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "支付装饰器演示", description = "展示正确与错误的装饰顺序影响")
@KeyComponent
@RequestMapping("/payment")
public class PaymentController {

    private final BasicPaymentService basicPaymentService;

    @Operation(summary = "正确顺序：日志->重试->折扣->幂等->核心")
    @PostMapping("/correct")
    public PayResult correct(@RequestBody Order order,
                             @RequestParam(defaultValue = "3") int retries,
                             @RequestParam(defaultValue = "0.9") BigDecimal discount) {
        basicPaymentService.resetAttempt(order.getUniqueKey());

        var svc = new PaymentServiceBuilder()
                .base(basicPaymentService)
                .withLogging()
                .withRetries(retries)
                .withVipDiscount(discount)
                .withIdempotency()
                .build();

        try {
            svc.pay(order);
            return PayResult.success(basicPaymentService.getAttemptCount(order.getUniqueKey()));
        } catch (Exception e) {
            return PayResult.fail(basicPaymentService.getAttemptCount(order.getUniqueKey()), e.getMessage());
        }
    }

    @Operation(summary = "错误顺序（示例）：幂等->重试->核心，可能导致重复写")
    @PostMapping("/wrong")
    public PayResult wrong(@RequestBody Order order,
                           @RequestParam(defaultValue = "3") int retries) {
        basicPaymentService.resetAttempt(order.getUniqueKey());

        // 错误装配：把幂等放在了重试外层
        var idempotentOuter = new com.xk857.main.core.pay.decorator.IdempotentPaymentDecorator(
                new com.xk857.main.core.pay.decorator.RetryPaymentDecorator(basicPaymentService, retries)
        );

        try {
            idempotentOuter.pay(order);
            return PayResult.success(basicPaymentService.getAttemptCount(order.getUniqueKey()));
        } catch (Exception e) {
            return PayResult.fail(basicPaymentService.getAttemptCount(order.getUniqueKey()), e.getMessage());
        }
    }

    @Data
    public static class PayResult {
        private boolean success;
        private String message;
        private int coreAttempts;

        public static PayResult success(int attempts) {
            PayResult r = new PayResult();
            r.success = true;
            r.message = "OK";
            r.coreAttempts = attempts;
            return r;
        }

        public static PayResult fail(int attempts, String msg) {
            PayResult r = new PayResult();
            r.success = false;
            r.message = msg;
            r.coreAttempts = attempts;
            return r;
        }
    }
}

