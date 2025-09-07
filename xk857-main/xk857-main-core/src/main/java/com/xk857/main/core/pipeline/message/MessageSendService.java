package com.xk857.main.core.pipeline.message;

import com.xk857.main.api.message.MessageChannel;
import com.xk857.main.api.message.MessageRequest;
import com.xk857.main.api.message.SendResult;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * 统一出入口，根据渠道路由到具体Sender
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSendService {

    private final List<MessageSender> senders;
    private final Map<MessageChannel, MessageSender> registry = new EnumMap<>(MessageChannel.class);

    @PostConstruct
    public void init() {
        for (MessageSender s : senders) {
            registry.put(s.channel(), s);
        }
        log.info("MessageSendService registry loaded: {}", registry.keySet());
    }

    public SendResult send(MessageRequest req) {
        MessageSender sender = registry.get(req.getChannel());
        if (sender == null) {
            return SendResult.rejected("不支持的渠道: " + req.getChannel());
        }
        return sender.send(req);
    }
}

