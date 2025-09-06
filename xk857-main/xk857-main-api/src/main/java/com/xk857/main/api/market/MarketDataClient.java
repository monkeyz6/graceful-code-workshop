package com.xk857.main.api.market;

import java.math.BigDecimal;

/**
 * 市场数据远程客户端接口（通过代理提供实现）
 */
public interface MarketDataClient {
    BigDecimal getPrice(String symbol);
}

