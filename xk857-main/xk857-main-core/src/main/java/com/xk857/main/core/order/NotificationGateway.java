package com.xk857.main.core.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 模拟通知网关
 */
@Slf4j
@Component
public class NotificationGateway {
    public void send(Long userId, String content) {
        log.info("发送通知给用户{}: {}", userId, content);
    }
}

