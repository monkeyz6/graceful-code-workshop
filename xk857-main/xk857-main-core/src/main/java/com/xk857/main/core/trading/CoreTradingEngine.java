package com.xk857.main.core.trading;

import com.xk857.framework.aop.annotation.Traceable;
import com.xk857.main.api.trading.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 纯粹的核心业务实现（不掺杂横切逻辑）
 */
@Slf4j
@Service
public class CoreTradingEngine implements TradingEngine {

    @Override
    @Traceable("executeTrade")
    public String executeTrade(Trade instruction) {
        // 1. 精密计算（演示用：日志+少量sleep）
        try { Thread.sleep(30); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        // 2. 核心撮合（演示）
        log.info("撮合成功: {} {} x{}", instruction.getSide(), instruction.getSymbol(), instruction.getQuantity());
        // 3. 数据库落地（演示：不落地，仅返回）
        return "TRADE_OK:" + instruction.getSymbol() + ":" + instruction.getSide();
    }
}

