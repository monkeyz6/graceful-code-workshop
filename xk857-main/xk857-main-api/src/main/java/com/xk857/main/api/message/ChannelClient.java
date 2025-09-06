package com.xk857.main.api.message;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ChannelClient", description = "消息渠道客户端契约")
public interface ChannelClient {
    String name();

    SendResult send(MessageReq req);
}

