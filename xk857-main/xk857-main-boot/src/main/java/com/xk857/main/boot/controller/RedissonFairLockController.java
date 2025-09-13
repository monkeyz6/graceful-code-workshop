package com.xk857.main.boot.controller;

import com.xk857.framework.common.Result;
import com.xk857.main.core.service.redisson.LotteryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/redisson/lottery")
@Tag(name = "Redisson-公平锁-摇号", description = "RFairLock 演示")
public class RedissonFairLockController {

    private final LotteryService lotteryService;

    @Operation(summary = "参与摇号")
    @PostMapping("/apply")
    public Result<String> apply(@RequestParam String userId) {
        return Result.success(lotteryService.applyForLottery(userId));
    }

    @Operation(summary = "查看队列")
    @GetMapping("/queue")
    public Result<Object> queue() {
        return Result.success(lotteryService.listQueue());
    }
}

