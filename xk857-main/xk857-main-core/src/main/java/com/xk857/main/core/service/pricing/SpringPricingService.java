package com.xk857.main.core.service.pricing;

import com.xk857.framework.exception.BusinessException;
import com.xk857.main.api.dto.PriceContext;
import com.xk857.main.api.enums.PricingErrorEnum;
import com.xk857.main.api.strategy.PricingStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 计价服务：基于 Spring 的策略注入实现“零 if-else”
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SpringPricingService {

    private final Map<String, PricingStrategy> strategies;
    private final UserProfileService userProfileService;

    public BigDecimal calculatePrice(PriceContext ctx) {
        if (ctx.getListPrice() == null || ctx.getListPrice().signum() < 0) {
            throw BusinessException.of(PricingErrorEnum.INVALID_PRICE, String.valueOf(ctx.getListPrice())).errThrow();
        }

        // 补全用户画像（若未显式传入）
        if (ctx.getVip() == null) {
            ctx.setVip(userProfileService.isVip(ctx.getUserId()));
        }
        if (ctx.getNewUser() == null) {
            ctx.setNewUser(userProfileService.isNewUser(ctx.getUserId()));
        }

        PricingStrategy strategy = strategies.get(ctx.getStrategyCode());
        if (strategy == null) {
            throw BusinessException.of(PricingErrorEnum.UNKNOWN_STRATEGY, ctx.getStrategyCode()).errThrow();
        }
        BigDecimal price = strategy.apply(ctx);
        log.info("Pricing -> strategy={}, listPrice={}, finalPrice={}, vip={}, newUser={}",
                ctx.getStrategyCode(), ctx.getListPrice(), price, ctx.getVip(), ctx.getNewUser());
        return price;
    }

    public Map<String, PricingStrategy> getStrategies() {
        return strategies;
    }
}

