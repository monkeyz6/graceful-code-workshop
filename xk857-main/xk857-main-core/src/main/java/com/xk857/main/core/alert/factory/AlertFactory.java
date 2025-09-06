package com.xk857.main.core.alert.factory;

import com.xk857.main.api.enums.AlertSeverity;
import com.xk857.main.core.alert.bridge.Alert;
import com.xk857.main.core.alert.bridge.CriticalAlert;
import com.xk857.main.core.alert.bridge.InfoAlert;
import com.xk857.main.core.alert.channel.ChannelSender;
import com.xk857.main.core.alert.config.AlertProperties;
import com.xk857.main.core.alert.template.AlertTemplate;
import org.springframework.stereotype.Component;

/**
 * 告警工厂
 * 说明：根据严重级别与默认配置（可被请求覆盖）装配桥接对象（Alert），
 * 负责选择具体的渠道与模板，并返回对应严重级别的告警实现。
 */
@Component
public class AlertFactory {
    private final SenderFactory senderFactory;
    private final TemplateFactory templateFactory;
    private final AlertProperties properties;

    public AlertFactory(SenderFactory senderFactory, TemplateFactory templateFactory, AlertProperties properties) {
        this.senderFactory = senderFactory;
        this.templateFactory = templateFactory;
        this.properties = properties;
    }

    public Alert create(AlertSeverity severity, String overrideChannel, String overrideTemplate) {
        String channelName;
        String templateName;
        switch (severity) {
            case CRITICAL -> {
                channelName = pick(overrideChannel, properties.getCritical().getChannel());
                templateName = pick(overrideTemplate, properties.getCritical().getTemplate());
            }
            case WARN -> {
                channelName = pick(overrideChannel, properties.getWarn().getChannel());
                templateName = pick(overrideTemplate, properties.getWarn().getTemplate());
            }
            default -> {
                channelName = pick(overrideChannel, properties.getInfo().getChannel());
                templateName = pick(overrideTemplate, properties.getInfo().getTemplate());
            }
        }

        ChannelSender sender = senderFactory.getSender(channelName);
        AlertTemplate template = templateFactory.getTemplate(templateName);

        return switch (severity) {
            case CRITICAL -> new CriticalAlert(sender, template);
            case WARN, INFO -> new InfoAlert(sender, template);
        };
    }

    private static String pick(String override, String fallback) {
        return (override != null && !override.isBlank()) ? override : fallback;
    }
}
