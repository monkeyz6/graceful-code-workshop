package com.xk857.main.boot.controller;

import com.xk857.framework.common.Result;
import com.xk857.main.core.service.redisson.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/redisson/report")
@Tag(name = "Redisson-闭锁-报表", description = "RCountDownLatch 演示")
public class RedissonLatchController {

    private final ReportService reportService;

    @Operation(summary = "生成日报")
    @PostMapping("/generate")
    public Result<String> generate() throws InterruptedException {
        return Result.success(reportService.generateDaily());
    }
}

