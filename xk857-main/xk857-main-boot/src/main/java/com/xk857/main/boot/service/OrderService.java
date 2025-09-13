package com.xk857.main.boot.service;

import com.xk857.main.boot.annotation.WorkshopDemo;
import com.xk857.main.boot.model.Order;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 订单服务：下单即进入延迟取消队列，支付后移除
 */
@Slf4j
@Service
@RequiredArgsConstructor
@WorkshopDemo(scene = "RDelayedQueue延迟取消订单", focus = "到期自动入阻塞队列")
public class OrderService {

    private final RedissonClient redissonClient;
    private RBlockingQueue<String> cancelQueue;
    private RDelayedQueue<String> delayedQueue;
    private final Map<String, Order> store = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        this.cancelQueue = redissonClient.getBlockingQueue("order_cancel_queue");
        this.delayedQueue = redissonClient.getDelayedQueue(cancelQueue);
    }

    public Order createOrder(long delaySeconds) {
        RAtomicLong idGen = redissonClient.getAtomicLong("global_order_id_counter");
        String id = String.valueOf(idGen.incrementAndGet());
        Order order = new Order();
        order.setId(id);
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(order.getCreatedAt());
        store.put(id, order);

        delayedQueue.offer(id, delaySeconds, TimeUnit.SECONDS);
        log.info("订单{}创建，{}秒后未支付将自动取消", id, delaySeconds);
        return order;
    }

    public boolean pay(String id) {
        Order order = store.get(id);
        if (order == null) return false;
        if ("CANCELED".equals(order.getStatus())) return false;
        order.setStatus("PAID");
        order.setUpdatedAt(LocalDateTime.now());
        // 取消延迟任务
        delayedQueue.remove(id);
        return true;
    }

    public Order get(String id) {return store.get(id);}    

    public void cancel(String id) {
        Order order = store.get(id);
        if (order == null) return;
        if ("CREATED".equals(order.getStatus())) {
            order.setStatus("CANCELED");
            order.setUpdatedAt(LocalDateTime.now());
            log.info("订单{}已自动取消并释放库存", id);
        }
    }
}

