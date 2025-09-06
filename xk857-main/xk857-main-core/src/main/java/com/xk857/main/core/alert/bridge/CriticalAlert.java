package com.xk857.main.core.alert.bridge;

import com.xk857.main.core.alert.channel.ChannelSender;
import com.xk857.main.core.alert.channel.SendResult;
import com.xk857.main.core.alert.channel.SmsSender;
import com.xk857.main.core.alert.template.AlertTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * CRITICAL 告警
 * 说明：高优先级策略示例，发送失败时触发短信兜底（演示用），可扩展为并发多渠道、重试与升级。
 */
@Slf4j
public class CriticalAlert extends Alert {
    public CriticalAlert(ChannelSender s, AlertTemplate t) { super(s, t); }

    @Override
    protected SendResult doSend(String content) {
        log.info("CRITICAL: 加急处理");
        // CRITICAL级别：并发多渠道、失败重试、紧急升级
        SendResult result = sender.send(content);
        if (!result.isSuccess()) {
            log.warn("CRITICAL: 主渠道失败，短信兜底");
            return new SmsSender().send(content);
        }
        return result;
    }
}
