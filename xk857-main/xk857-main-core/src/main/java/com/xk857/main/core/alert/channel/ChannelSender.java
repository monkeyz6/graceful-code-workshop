package com.xk857.main.core.alert.channel;

/**
 * 渠道发送器
 * 说明：不同发送渠道（短信/飞书/邮件等）的统一接口抽象，供桥接模式组合调用。
 */
public interface ChannelSender {
    SendResult send(String renderedContent);
}
