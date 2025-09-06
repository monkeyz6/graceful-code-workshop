package com.xk857.main.core.trading;

import com.xk857.framework.aop.annotation.AdminOnly;
import com.xk857.framework.aop.annotation.RetryableLite;
import com.xk857.framework.aop.annotation.Traceable;
import com.xk857.main.api.market.MarketDataClient;
import com.xk857.main.api.trading.Trade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 交易应用服务：编排核心引擎与远程行情
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TradingService {
    private final TradingEngine tradingEngine;
    private final MarketDataClient marketDataClient;

    @AdminOnly
    @Traceable("trade.execute")
    @RetryableLite(attempts = 2, delayMs = 80)
    public Map<String, Object> execute(Trade trade) {
        // 调用远程行情（由动态代理模拟）
        BigDecimal price = marketDataClient.getPrice(trade.getSymbol());

        // 偶发失败，触发重试（教学用）
        if (ThreadLocalRandom.current().nextInt(10) < 2) {
            throw new RuntimeException("临时性撮合失败，请重试");
        }

        String result = tradingEngine.executeTrade(trade);
        return Map.of(
                "result", result,
                "price", price,
                "filled", trade.getQuantity(),
                "side", trade.getSide()
        );
    }
}

