package com.xk857.main.boot.controller;

import com.xk857.framework.common.Result;
import com.xk857.main.core.service.redisson.TranscodingService;
import com.xk857.main.core.service.redisson.TranscodingServiceV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/redisson/transcoding")
@Tag(name = "Redisson-信号量-转码", description = "RSemaphore 与 PermitExpirableSemaphore 演示")
public class RedissonSemaphoreController {

    private final TranscodingService transcodingService;
    private final TranscodingServiceV2 transcodingServiceV2;

    @Operation(summary = "提交转码任务（普通信号量）")
    @PostMapping("/submit")
    public Result<String> submit(@RequestParam String videoId) throws InterruptedException {
        return Result.success(transcodingService.submit(videoId));
    }

    @Operation(summary = "提交转码任务（可过期许可）")
    @PostMapping("/submit-exp")
    public Result<String> submitExp(@RequestParam String videoId) throws InterruptedException {
        return Result.success(transcodingServiceV2.submit(videoId));
    }
}

