package com.xk857.main.core.message.client;

import com.xk857.main.api.message.ChannelClient;
import com.xk857.main.api.message.MessageReq;
import com.xk857.main.api.message.SendResult;
import com.xk857.main.core.message.ChannelNames;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DingTalkChannelClient implements ChannelClient {
    @Override
    public String name() {
        return ChannelNames.DING_TALK;
    }

    @Override
    public SendResult send(MessageReq req) {
        // 模拟access_token校验：receiver像token就ok
        if (req.receiver() == null || req.receiver().length() < 6) {
            return SendResult.fail("invalid_token");
        }
        if (req.content() != null && req.content().contains("fail:DingTalk")) {
            return SendResult.fail("mock_fail");
        }
        return SendResult.ok("dingtalk-" + UUID.randomUUID());
    }
}

