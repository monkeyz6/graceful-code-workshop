package com.xk857.main.boot.controller;

import com.xk857.framework.common.Result;
import com.xk857.main.core.service.redisson.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/redisson/transfer")
@Tag(name = "Redisson-联锁-转账", description = "MultiLock 演示")
public class RedissonMultiLockController {

    private final TransferService transferService;

    @Operation(summary = "重置账户余额")
    @PostMapping("/reset")
    public Result<String> reset(@RequestParam String id, @RequestParam BigDecimal amount) {
        transferService.reset(id, amount);
        return Result.success("reset ok");
    }

    @Operation(summary = "转账")
    @PostMapping
    public Result<String> transfer(@RequestParam String from, @RequestParam String to, @RequestParam BigDecimal amount) {
        return Result.success(transferService.transfer(from, to, amount));
    }

    @Operation(summary = "账户快照")
    @GetMapping("/snapshot")
    public Result<Object> snapshot() { return Result.success(transferService.snapshot()); }
}

