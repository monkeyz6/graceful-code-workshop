package com.xk857.main.boot.controller;

import com.xk857.framework.processor.annotation.APIVersion;
import com.xk857.framework.processor.enmu.APIVersionEnum;
import com.xk857.main.api.dto.PriceContext;
import com.xk857.main.api.strategy.PricingStrategy;
import com.xk857.main.core.service.pricing.SpringPricingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@APIVersion(APIVersionEnum.V1)
@RequestMapping("/api/pricing")
@Tag(name = "计价策略引擎", description = "基于策略模式的计价服务")
public class PricingController {

    private final SpringPricingService pricingService;

    @Operation(summary = "计算价格")
    @PostMapping("/calc")
    public Map<String, Object> calculate(@Valid @RequestBody PriceContext ctx) {
        BigDecimal price = pricingService.calculatePrice(ctx);
        return Map.of(
                "strategy", ctx.getStrategyCode(),
                "listPrice", ctx.getListPrice(),
                "finalPrice", price,
                "vip", ctx.getVip(),
                "newUser", ctx.getNewUser()
        );
    }

    @Operation(summary = "可用策略列表")
    @GetMapping("/strategies")
    public Map<String, String> strategies() {
        return pricingService.getStrategies().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getClass().getSimpleName()));
    }
}

