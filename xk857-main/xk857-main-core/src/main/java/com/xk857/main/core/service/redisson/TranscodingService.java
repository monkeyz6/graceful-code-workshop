package com.xk857.main.core.service.redisson;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

/**
 * 信号量 - 转码并发控制
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TranscodingService {

    private final RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        redissonClient.getSemaphore("transcoding_permits").trySetPermits(10);
    }

    public String submit(String videoId) throws InterruptedException {
        RSemaphore sem = redissonClient.getSemaphore("transcoding_permits");
        sem.acquire();
        try {
            log.info("获取许可，开始转码 {}", videoId);
            // 模拟耗时
            Thread.sleep(200);
            return "STARTED:" + videoId;
        } finally {
            sem.release();
            log.info("释放许可，视频 {}", videoId);
        }
    }
}

