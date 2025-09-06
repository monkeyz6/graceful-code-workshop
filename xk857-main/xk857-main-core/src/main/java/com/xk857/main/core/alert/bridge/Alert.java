package com.xk857.main.core.alert.bridge;

import com.xk857.main.core.alert.channel.ChannelSender;
import com.xk857.main.core.alert.channel.SendResult;
import com.xk857.main.core.alert.template.AlertContext;
import com.xk857.main.core.alert.template.AlertTemplate;

/**
 * 告警（抽象层）
 * 说明：桥接模式中的抽象角色，组合了渠道与模板，实现公共的渲染与发送模板方法，
 * 具体严重级别通过子类在 doSend 中体现差异化策略。
 */
public abstract class Alert {
    protected final ChannelSender sender;
    protected final AlertTemplate template;

    protected Alert(ChannelSender sender, AlertTemplate template) {
        this.sender = sender;
        this.template = template;
    }

    public final SendResult notify(AlertContext ctx) {
        String content = template.render(ctx);
        return doSend(content);
    }

    protected abstract SendResult doSend(String content);
}
