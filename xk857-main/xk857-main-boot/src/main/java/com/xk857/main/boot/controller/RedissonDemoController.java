package com.xk857.main.boot.controller;

import com.xk857.framework.common.Result;
import com.xk857.main.boot.annotation.WorkshopDemo;
import com.xk857.main.boot.service.RedissonDemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redisson")
@RequiredArgsConstructor
@Tag(name = "Redisson演示", description = "分布式锁、KV、限流示例")
@WorkshopDemo(scene = "Redisson演示接口", focus = "锁/KV/限流")
public class RedissonDemoController {

    private final RedissonDemoService service;

    @Operation(summary = "分布式锁演示")
    @GetMapping("/lock")
    public Result<String> lock(@Parameter(description = "业务键") @RequestParam String key,
                               @Parameter(description = "锁租期(秒)") @RequestParam(defaultValue = "5") long leaseSeconds,
                               @Parameter(description = "模拟业务耗时(毫秒)") @RequestParam(defaultValue = "1000") long bizMillis) throws InterruptedException {
        String res = service.doWithLock(key, leaseSeconds, bizMillis);
        return Result.success(res);
    }

    @Operation(summary = "KV缓存演示")
    @GetMapping("/kv")
    public Result<String> kv(@Parameter(description = "键") @RequestParam String key,
                             @Parameter(description = "值") @RequestParam String value,
                             @Parameter(description = "TTL(秒)") @RequestParam(defaultValue = "0") long ttlSeconds) {
        String back = service.setAndGet(key, value, ttlSeconds);
        return Result.success(back);
    }

    @Operation(summary = "简单限流演示")
    @GetMapping("/rate")
    public Result<Boolean> rate(@Parameter(description = "限流键") @RequestParam String key,
                                @Parameter(description = "窗口内许可数") @RequestParam(defaultValue = "5") long rate,
                                @Parameter(description = "窗口秒数") @RequestParam(defaultValue = "1") long intervalSeconds) {
        boolean ok = service.rateLimitTryAcquire(key, rate, intervalSeconds);
        return Result.success(ok);
    }
}

