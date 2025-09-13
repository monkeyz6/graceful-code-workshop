package com.xk857.main.core.service.redisson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 读写锁 - 航班大屏
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FlightInfoService {

    private final RedissonClient redissonClient;
    private static final Map<String, String> FLIGHT_STATUS = new ConcurrentHashMap<>();

    public String getStatus(String flightId) {
        RReadWriteLock rw = redissonClient.getReadWriteLock("flight_info_lock:" + flightId);
        RLock r = rw.readLock();
        r.lock();
        try {
            String status = FLIGHT_STATUS.getOrDefault(flightId, "UNKNOWN");
            log.info("查询航班状态 {} -> {}", flightId, status);
            return status;
        } finally {
            r.unlock();
        }
    }

    public void updateStatus(String flightId, String newStatus) {
        RReadWriteLock rw = redissonClient.getReadWriteLock("flight_info_lock:" + flightId);
        RLock w = rw.writeLock();
        w.lock();
        try {
            FLIGHT_STATUS.put(flightId, newStatus);
            log.info("更新航班状态 {} -> {}", flightId, newStatus);
        } finally {
            w.unlock();
        }
    }
}

