package com.xk857.main.core.trading;

import com.xk857.framework.aop.annotation.Traceable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 交易查询服务（缓存代理示例）
 */
@Slf4j
@Service
public class TradingQueryService {

    @Traceable("getTradeSummary")
    @Cacheable(cacheNames = "trade-sum", key = "#tradeId")
    public Map<String, Object> getTradeSummary(Long tradeId) {
        // 模拟昂贵的计算/IO
        try { Thread.sleep(150); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        log.info("计算交易摘要（未命中缓存） tradeId={}", tradeId);
        return Map.of(
                "tradeId", tradeId,
                "status", "DONE",
                "pnl", 123.45,
                "calcAt", System.currentTimeMillis()
        );
    }
}

