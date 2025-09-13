package com.xk857.main.core.service.redisson;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RPermitExpirableSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 可过期信号量 - 转码容错
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TranscodingServiceV2 {

    private final RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        redissonClient.getPermitExpirableSemaphore("transcoding_permits_exp").trySetPermits(10);
    }

    public String submit(String videoId) throws InterruptedException {
        RPermitExpirableSemaphore sem = redissonClient.getPermitExpirableSemaphore("transcoding_permits_exp");
        String permitId = sem.acquire(1, TimeUnit.HOURS);
        try {
            log.info("获取带时效许可 {}，开始转码 {}", permitId, videoId);
            Thread.sleep(200);
            return "STARTED_EXP:" + videoId;
        } finally {
            sem.release(permitId);
            log.info("释放许可 {}，视频 {}", permitId, videoId);
        }
    }
}

