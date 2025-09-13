package com.xk857.main.boot.consumer;

import com.xk857.main.boot.annotation.WorkshopDemo;
import com.xk857.main.boot.service.OrderService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * 延迟取消消费者：监听阻塞队列，触发取消
 */
@Slf4j
@Component
@RequiredArgsConstructor
@WorkshopDemo(scene = "RDelayedQueue消费者", focus = "阻塞队列take处理")
public class OrderCancelConsumer {

    private final RedissonClient redissonClient;
    private final OrderService orderService;

    @PostConstruct
    public void start() {
        RBlockingQueue<String> dest = redissonClient.getBlockingQueue("order_cancel_queue");
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    String orderId = dest.take();
                    log.info("到期订单: {}，检查并取消", orderId);
                    orderService.cancel(orderId);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (org.redisson.RedissonShutdownException e) {
                    // 在应用关闭过程中，Redisson 会被优雅退出；此时阻塞队列操作会抛出该异常。
                    // 这里直接退出消费线程，避免在关闭阶段输出误导性的错误日志。
                    log.info("Redisson 已关闭，退出订单取消消费者线程");
                    break;
                } catch (Exception e) {
                    log.error("消费异常", e);
                }
            }
        }, "order-cancel-consumer");
        t.setDaemon(true);
        t.start();
    }
}
