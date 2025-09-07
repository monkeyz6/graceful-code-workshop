package com.xk857.main.core.pipeline.message.sender;

import com.xk857.main.api.message.MessageChannel;
import com.xk857.main.api.message.SendResult;
import com.xk857.main.core.pipeline.message.AbstractMessageSender;
import com.xk857.main.core.pipeline.message.MessageSender;
import com.xk857.main.core.pipeline.message.model.SendContext;
import com.xk857.main.core.pipeline.message.strategy.UserLoaderStrategy;
import com.xk857.main.core.pipeline.message.support.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 企业微信渠道
 */
@Slf4j
@Component
public class WecomMessageSender extends AbstractMessageSender implements MessageSender {

    public WecomMessageSender(UserLoaderStrategy userLoader,
                              AntiSpamService antiSpamService,
                              IdempotencyService idempotencyService,
                              RateLimitService rateLimitService,
                              TemplateRenderer templateRenderer,
                              AuditLogService auditLogService) {
        super(userLoader, antiSpamService, idempotencyService, rateLimitService, templateRenderer, auditLogService);
    }

    @Override
    public MessageChannel channel() { return MessageChannel.WECOM; }

    @Override
    protected String transformForChannel(String renderedContent, SendContext ctx) {
        // 企微支持Markdown，直接透传
        return renderedContent;
    }

    @Override
    protected SendResult doSend(String payload, SendContext ctx) {
        log.info("[WeCom] to={} content={}", ctx.getUser().getWecomId(), payload);
        return SendResult.ok("wecom-" + ctx.getRequest().getRequestId());
    }
}

