package com.xk857.main.core.pipeline.message;

import com.xk857.main.api.message.MessageChannel;
import com.xk857.main.api.message.MessageRequest;
import com.xk857.main.api.message.SendResult;

/**
 * 统一的发送接口
 */
public interface MessageSender {
    MessageChannel channel();
    SendResult send(MessageRequest request);
}

