package com.xk857.main.core.alert.channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 邮件发送器
 * 说明：演示用渠道实现，打印日志模拟发送并返回成功结果，Bean 名称为 "email"。
 */
@Slf4j
@Component("email")
public class EmailSender implements ChannelSender {
    @Override
    public SendResult send(String content) {
        log.info("发送[邮件]: {}", content);
        return SendResult.ok("email-" + System.currentTimeMillis());
    }
}
