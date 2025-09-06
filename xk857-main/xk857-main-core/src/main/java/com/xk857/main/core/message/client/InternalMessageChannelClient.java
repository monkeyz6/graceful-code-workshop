package com.xk857.main.core.message.client;

import com.xk857.main.api.message.ChannelClient;
import com.xk857.main.api.message.MessageReq;
import com.xk857.main.api.message.SendResult;
import com.xk857.main.core.message.ChannelNames;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InternalMessageChannelClient implements ChannelClient {
    @Override
    public String name() {
        return ChannelNames.INTERNAL;
    }

    @Override
    public SendResult send(MessageReq req) {
        if (req.content() != null && req.content().contains("fail:Internal")) {
            return SendResult.fail("mock_fail");
        }
        return SendResult.ok("internal-" + UUID.randomUUID());
    }
}

