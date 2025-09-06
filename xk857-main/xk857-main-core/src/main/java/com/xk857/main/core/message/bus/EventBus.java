package com.xk857.main.core.message.bus;

import java.util.function.Consumer;

/**
 * 极简事件总线接口，用于演示异步外观。
 */
public interface EventBus {
    void publish(String topic, Object payload);

    void register(String topic, Consumer<Object> consumer);
}

