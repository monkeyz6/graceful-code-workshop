package com.xk857.main.core.alert.bridge;

import com.xk857.main.core.alert.channel.ChannelSender;
import com.xk857.main.core.alert.channel.SendResult;
import com.xk857.main.core.alert.template.AlertTemplate;

/**
 * INFO 告警
 * 说明：轻量策略，直接通过渠道发送，无额外兜底或升级逻辑。
 */
public class InfoAlert extends Alert {
    public InfoAlert(ChannelSender s, AlertTemplate t) { super(s, t); }

    @Override
    protected SendResult doSend(String content) {
        return sender.send(content);
    }
}
