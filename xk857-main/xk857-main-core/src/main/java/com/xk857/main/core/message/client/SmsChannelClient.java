package com.xk857.main.core.message.client;

import com.xk857.main.api.message.ChannelClient;
import com.xk857.main.api.message.MessageReq;
import com.xk857.main.api.message.SendResult;
import com.xk857.main.core.message.ChannelNames;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SmsChannelClient implements ChannelClient {
    @Override
    public String name() {
        return ChannelNames.SMS;
    }

    @Override
    public SendResult send(MessageReq req) {
        // 模拟第三方短信网关：只要receiver像手机号就认为成功
        if (req.receiver() == null || req.receiver().length() < 6) {
            return SendResult.fail("invalid_phone");
        }
        // 人为触发失败：内容包含 fail:SMS
        if (req.content() != null && req.content().contains("fail:SMS")) {
            return SendResult.fail("mock_fail");
        }
        return SendResult.ok("sms-" + UUID.randomUUID());
    }
}

