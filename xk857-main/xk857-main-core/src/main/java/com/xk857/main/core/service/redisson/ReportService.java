package com.xk857.main.core.service.redisson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 闭锁 - 报表生成
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final RedissonClient redissonClient;
    private final ExecutorService pool = Executors.newFixedThreadPool(3, new CustomizableThreadFactory("report-fetch-"));

    public String generateDaily() throws InterruptedException {
        String key = "report_latch:" + LocalDate.now();
        RCountDownLatch latch = redissonClient.getCountDownLatch(key);
        latch.trySetCount(3);

        pool.submit(() -> fetch("user", key));
        pool.submit(() -> fetch("order", key));
        pool.submit(() -> fetch("payment", key));

        log.info("主任务等待数据拉取完成...");
        latch.await();
        log.info("合并数据，生成报表");
        return "REPORT_OK";
    }

    private void fetch(String type, String key) {
        try {
            Thread.sleep(200);
            log.info("{} 数据拉取完成", type);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            redissonClient.getCountDownLatch(key).countDown();
        }
    }
}

