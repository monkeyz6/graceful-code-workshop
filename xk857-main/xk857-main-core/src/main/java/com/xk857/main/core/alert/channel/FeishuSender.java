package com.xk857.main.core.alert.channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 飞书发送器
 * 说明：演示用渠道实现，打印日志模拟发送并返回成功结果，Bean 名称为 "feishu"。
 */
@Slf4j
@Component("feishu")
public class FeishuSender implements ChannelSender {
    @Override
    public SendResult send(String content) {
        log.info("发送[飞书]: {}", content);
        return SendResult.ok("feishu-" + System.currentTimeMillis());
    }
}
