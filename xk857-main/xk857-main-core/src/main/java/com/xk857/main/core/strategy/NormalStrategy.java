package com.xk857.main.core.strategy;

import com.xk857.main.api.dto.PriceContext;
import com.xk857.main.api.strategy.PricingStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 策略：原价
 */
@Component("NORMAL")
public class NormalStrategy implements PricingStrategy {
    @Override
    public BigDecimal apply(PriceContext ctx) {
        return round(ctx.getListPrice());
    }
}

