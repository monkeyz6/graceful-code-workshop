package com.xk857.main.core.trading;

import com.xk857.main.api.trading.Trade;

/**
 * 核心撮合引擎接口
 */
public interface TradingEngine {
    String executeTrade(Trade instruction);
}

