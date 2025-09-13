package com.xk857.main.boot.controller;

import com.xk857.framework.common.Result;
import com.xk857.main.core.service.redisson.WorldBossService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/redisson/worldboss")
@Tag(name = "Redisson-红锁-世界BOSS", description = "RedLock 演示")
public class RedissonRedLockController {

    private final WorldBossService worldBossService;

    @Operation(summary = "刷新世界BOSS")
    @PostMapping("/spawn")
    public Result<String> spawn() {
        return Result.success(worldBossService.spawn());
    }
}

