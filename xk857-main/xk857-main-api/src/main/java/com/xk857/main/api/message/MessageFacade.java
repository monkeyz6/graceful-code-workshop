package com.xk857.main.api.message;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MessageFacade", description = "消息发送外观接口")
public interface MessageFacade {
    AggregateResult send(MessageReq req, SendPolicy policy);

    AggregateResult broadcast(MessageReq req);
}

