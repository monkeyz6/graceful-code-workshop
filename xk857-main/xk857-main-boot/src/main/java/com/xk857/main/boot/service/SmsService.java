package com.xk857.main.boot.service;

import com.xk857.main.boot.annotation.WorkshopDemo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 全局短信限流器：RRateLimiter OVERALL 模式
 */
@Slf4j
@Service
@RequiredArgsConstructor
@WorkshopDemo(scene = "RRateLimiter全局限流", focus = "演示1 QPS（生产建议≥200 QPS）")
public class SmsService {

    private final RedissonClient redissonClient;
    private RRateLimiter smsLimiter;

    @PostConstruct
    public void init() {
        this.smsLimiter = redissonClient.getRateLimiter("sms_global_limiter");
        // 演示：每秒 1 次，便于人工快速触发限流。
        // 生产建议：≥200 QPS 或按业务峰值评估，并配置化到应用配置中心。
        this.smsLimiter.trySetRate(RateType.OVERALL, 1, 1, RateIntervalUnit.SECONDS);
    }

    public boolean sendSms(String phone) {
        // 快速失败：最多等待500ms
        boolean ok = smsLimiter.tryAcquire(1, 500, TimeUnit.MILLISECONDS);
        if (!ok) {
            log.warn("短信网关繁忙，触发全局限流！phone={}", phone);
            return false;
        }
        log.info("获取到令牌，发送短信至: {}", phone);
        // 模拟调用第三方...
        return true;
    }
}
