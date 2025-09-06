package com.xk857.main.core.alert.factory;

import com.xk857.main.core.alert.channel.ChannelSender;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 渠道工厂
 * 说明：基于 Spring 的按名称注入（Map<String, ChannelSender>），按配置或入参选择具体渠道实现。
 */
@Component
public class SenderFactory {

    // Spring会自动将所有ChannelSender类型的Bean注入这个Map
    // key是Bean的名字（"sms", "feishu"），value是Bean实例
    private final Map<String, ChannelSender> senderMap;

    public SenderFactory(Map<String, ChannelSender> senderMap) {
        this.senderMap = senderMap;
    }

    public ChannelSender getSender(String channel) {
        ChannelSender sender = senderMap.get(channel);
        if (sender == null) {
            throw new IllegalArgumentException("Unsupported channel: " + channel);
        }
        return sender;
    }
}
