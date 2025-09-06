package com.xk857.main.core.pay.decorator;

import com.xk857.main.api.annotation.KeyComponent;
import com.xk857.main.api.pay.PaymentService;
import com.xk857.main.api.pay.dto.Order;

/**
 * 装饰器骨架：持有一个同类型的委托对象。
 */
@KeyComponent
public abstract class PaymentServiceDecorator implements PaymentService {

    protected final PaymentService delegate;

    protected PaymentServiceDecorator(PaymentService delegate) {
        this.delegate = delegate;
    }

    @Override
    public void pay(Order order) {
        delegate.pay(order);
    }
}

