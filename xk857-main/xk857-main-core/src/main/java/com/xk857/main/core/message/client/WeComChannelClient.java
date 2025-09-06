package com.xk857.main.core.message.client;

import com.xk857.main.api.message.ChannelClient;
import com.xk857.main.api.message.MessageReq;
import com.xk857.main.api.message.SendResult;
import com.xk857.main.core.message.ChannelNames;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WeComChannelClient implements ChannelClient {
    @Override
    public String name() {
        return ChannelNames.WE_COM;
    }

    @Override
    public SendResult send(MessageReq req) {
        // 模拟拼装Markdown：只要有content就ok
        if (req.content() == null || req.content().isBlank()) {
            return SendResult.fail("empty_content");
        }
        if (req.content().contains("fail:WeCom")) {
            return SendResult.fail("mock_fail");
        }
        return SendResult.ok("wecom-" + UUID.randomUUID());
    }
}

