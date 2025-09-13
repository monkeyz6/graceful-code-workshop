package com.xk857.main.boot.controller;

import com.xk857.framework.common.Result;
import com.xk857.main.core.service.redisson.FlightInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/redisson/flight")
@Tag(name = "Redisson-读写锁-航班", description = "RReadWriteLock 演示")
public class RedissonRWLockController {

    private final FlightInfoService flightInfoService;

    @Operation(summary = "查询航班状态")
    @GetMapping("/status")
    public Result<String> status(@RequestParam String flightId) {
        return Result.success(flightInfoService.getStatus(flightId));
    }

    @Operation(summary = "更新航班状态")
    @PostMapping("/update")
    public Result<String> update(@RequestParam String flightId, @RequestParam String status) {
        flightInfoService.updateStatus(flightId, status);
        return Result.success("ok");
    }
}

