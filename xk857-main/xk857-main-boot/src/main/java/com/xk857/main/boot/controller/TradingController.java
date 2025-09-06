package com.xk857.main.boot.controller;

import com.xk857.framework.processor.annotation.APIVersion;
import com.xk857.framework.processor.enmu.APIVersionEnum;
import com.xk857.main.api.trading.Trade;
import com.xk857.main.core.trading.TradingQueryService;
import com.xk857.main.core.trading.TradingService;
import com.xk857.main.core.virtual.Chart;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@APIVersion(APIVersionEnum.V1)
@RequestMapping("/api/trade")
@Tag(name = "代理模式演示", description = "安全/重试/缓存/远程/虚拟代理示例")
public class TradingController {

    private final TradingService tradingService;
    private final TradingQueryService queryService;
    private final Chart chart;

    @Operation(summary = "执行交易（需要管理员）", description = "Header: X-ROLE=admin")
    @PostMapping("/execute")
    public Map<String, Object> execute(@RequestBody @Valid Trade trade) {
        return tradingService.execute(trade);
    }

    @Operation(summary = "查询交易摘要（缓存代理）")
    @GetMapping("/summary/{id}")
    public Map<String, Object> summary(@PathVariable Long id) {
        return queryService.getTradeSummary(id);
    }

    @Operation(summary = "渲染图表（虚拟代理：首次慢，之后快）")
    @GetMapping("/chart")
    public String renderChart() {
        return chart.render();
    }
}

