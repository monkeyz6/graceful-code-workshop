package com.xk857.main.core.strategy;

import com.xk857.main.api.dto.PriceContext;
import com.xk857.main.api.strategy.PricingStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 策略：满 300 减 50
 */
@Component("VOUCHER_300_50")
public class Voucher300_50Strategy implements PricingStrategy {

    private static final BigDecimal THRESHOLD = new BigDecimal("300");
    private static final BigDecimal VOUCHER = new BigDecimal("50");

    @Override
    public BigDecimal apply(PriceContext ctx) {
        BigDecimal base = ctx.getListPrice();
        if (base.compareTo(THRESHOLD) >= 0) {
            return round(base.subtract(VOUCHER));
        }
        return round(base);
    }
}

