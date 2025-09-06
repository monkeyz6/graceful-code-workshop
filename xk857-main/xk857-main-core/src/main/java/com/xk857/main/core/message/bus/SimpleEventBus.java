package com.xk857.main.core.message.bus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;

@Slf4j
@Component
public class SimpleEventBus implements EventBus {

    private final Map<String, List<Consumer<Object>>> consumers = new ConcurrentHashMap<>();
    private final ExecutorService executor = new ThreadPoolExecutor(
            1, 4, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024),
            r -> new Thread(r, "simple-event-bus"), new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public void publish(String topic, Object payload) {
        List<Consumer<Object>> handlers = consumers.getOrDefault(topic, List.of());
        if (handlers.isEmpty()) {
            log.warn("no consumer for topic={}, payload={}", topic, payload);
            return;
        }
        for (Consumer<Object> h : handlers) {
            executor.submit(() -> safeConsume(topic, h, payload));
        }
    }

    private void safeConsume(String topic, Consumer<Object> h, Object payload) {
        try {
            h.accept(payload);
        } catch (Exception e) {
            log.error("consume error, topic={}", topic, e);
        }
    }

    @Override
    public void register(String topic, Consumer<Object> consumer) {
        consumers.computeIfAbsent(topic, t -> new ArrayList<>()).add(consumer);
    }
}

