package com.xk857.main.core.message;

import com.xk857.main.api.message.*;
import com.xk857.main.core.message.bus.EventBus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service("asyncMessageFacade")
@RequiredArgsConstructor
public class AsyncMessageFacade implements MessageFacade {

    public static final String TOPIC_BROADCAST = "message.broadcast";
    public static final String TOPIC_SEND_WITH_POLICY = "message.send";

    private final EventBus eventBus;

    @Override
    public AggregateResult send(MessageReq req, SendPolicy policy) {
        // 只发布事件，立即返回已入队状态
        eventBus.publish(TOPIC_SEND_WITH_POLICY, new SendPayload(req, policy));
        Map<String, SendResult> details = new LinkedHashMap<>();
        details.put("status", SendResult.ok("queued"));
        return new AggregateResult(true, details);
    }

    @Override
    public AggregateResult broadcast(MessageReq req) {
        eventBus.publish(TOPIC_BROADCAST, req);
        Map<String, SendResult> details = new LinkedHashMap<>();
        details.put("status", SendResult.ok("queued"));
        return new AggregateResult(true, details);
    }

    /**
     * 事件携带体。
     */
    public record SendPayload(MessageReq req, SendPolicy policy) {}
}

