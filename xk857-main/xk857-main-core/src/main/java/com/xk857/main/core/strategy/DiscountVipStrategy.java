package com.xk857.main.core.strategy;

import com.xk857.main.api.dto.PriceContext;
import com.xk857.main.api.strategy.PricingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 策略：VIP 8 折，非 VIP 9 折
 */
@Slf4j
@Component("DISCOUNT_VIP")
public class DiscountVipStrategy implements PricingStrategy {

    private static final BigDecimal VIP_RATE = new BigDecimal("0.8");
    private static final BigDecimal NORMAL_RATE = new BigDecimal("0.9");

    @Override
    public BigDecimal apply(PriceContext ctx) {
        BigDecimal rate = Boolean.TRUE.equals(ctx.getVip()) ? VIP_RATE : NORMAL_RATE;
        BigDecimal result = ctx.getListPrice().multiply(rate);
        return round(result);
    }
}

