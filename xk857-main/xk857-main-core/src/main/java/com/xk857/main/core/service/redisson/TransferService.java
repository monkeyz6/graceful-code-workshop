package com.xk857.main.core.service.redisson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.RedissonMultiLock;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 联锁 MultiLock - 银行转账
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransferService {

    private final RedissonClient redissonClient;
    // 模拟账户余额
    private static final Map<String, BigDecimal> ACCOUNT = new ConcurrentHashMap<>();

    public void reset(String id, BigDecimal amount) { ACCOUNT.put(id, amount); }

    public String transfer(String from, String to, BigDecimal amount) {
        RLock lockFrom = redissonClient.getLock("account_lock:" + from);
        RLock lockTo = redissonClient.getLock("account_lock:" + to);
        RedissonMultiLock multiLock = new RedissonMultiLock(lockFrom, lockTo);
        try {
            multiLock.lock();
            BigDecimal f = ACCOUNT.getOrDefault(from, BigDecimal.ZERO);
            if (f.compareTo(amount) < 0) {
                return "INSUFFICIENT_FUNDS";
            }
            ACCOUNT.put(from, f.subtract(amount));
            ACCOUNT.put(to, ACCOUNT.getOrDefault(to, BigDecimal.ZERO).add(amount));
            log.info("转账完成 {} -> {} : {}", from, to, amount);
            return "OK";
        } finally {
            multiLock.unlock();
        }
    }

    public Map<String, BigDecimal> snapshot() { return ACCOUNT; }
}
