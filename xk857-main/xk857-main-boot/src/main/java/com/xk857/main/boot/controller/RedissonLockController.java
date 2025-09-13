package com.xk857.main.boot.controller;

import com.xk857.framework.common.Result;
import com.xk857.main.core.service.redisson.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/redisson/stock")
@Tag(name = "Redisson-锁-秒杀", description = "RLock 可重入锁演示")
public class RedissonLockController {

    private final StockService stockService;

    @Operation(summary = "重置库存")
    @PostMapping("/reset")
    public Result<String> reset(@RequestParam String productId, @RequestParam int stock) {
        stockService.resetStock(productId, stock);
        return Result.success("reset ok", "pid=" + productId + ", stock=" + stock);
    }

    @Operation(summary = "扣减库存")
    @PostMapping("/buy")
    public Result<String> buy(@RequestParam String productId) {
        return Result.success(stockService.deductStock(productId));
    }
}

