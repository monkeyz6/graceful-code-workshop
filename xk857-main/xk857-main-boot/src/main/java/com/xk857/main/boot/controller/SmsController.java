package com.xk857.main.boot.controller;

import com.xk857.framework.common.Result;
import com.xk857.main.boot.annotation.WorkshopDemo;
import com.xk857.main.boot.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redisson/sms")
@RequiredArgsConstructor
@Tag(name = "RRateLimiter演示", description = "全局限流器-短信网关")
@WorkshopDemo(scene = "限流总闸", focus = "演示1 QPS（生产建议≥200 QPS）")
public class SmsController {

    private final SmsService smsService;

    @Operation(summary = "发送短信(受全局限流)")
    @GetMapping("/send")
    public Result<Boolean> send(@RequestParam String phone) {
        return Result.success(smsService.sendSms(phone));
    }
}
