package com.xk857.main.core.strategy;

import com.xk857.main.api.dto.PriceContext;
import com.xk857.main.api.strategy.PricingStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 策略：新人专享立减 20
 */
@Component("NEW_USER_SPECIAL")
public class NewUserSpecialStrategy implements PricingStrategy {

    private static final BigDecimal CUT = new BigDecimal("20");

    @Override
    public BigDecimal apply(PriceContext ctx) {
        BigDecimal base = ctx.getListPrice();
        if (Boolean.TRUE.equals(ctx.getNewUser())) {
            BigDecimal result = base.subtract(CUT);
            if (result.signum() < 0) {
                result = BigDecimal.ZERO;
            }
            return round(result);
        }
        return round(base);
    }
}

