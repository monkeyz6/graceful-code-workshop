package com.xk857.main.api.strategy;

import com.xk857.main.api.dto.PriceContext;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 计价策略接口（军规）
 */
@FunctionalInterface
public interface PricingStrategy {

    BigDecimal apply(PriceContext ctx);

    default BigDecimal round(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}

