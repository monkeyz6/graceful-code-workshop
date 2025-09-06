package com.xk857.main.api.pay;

import com.xk857.main.api.annotation.KeyComponent;
import com.xk857.main.api.pay.dto.Order;

/**
 * 支付服务契约。
 */
@KeyComponent
public interface PaymentService {
    void pay(Order order);
}

