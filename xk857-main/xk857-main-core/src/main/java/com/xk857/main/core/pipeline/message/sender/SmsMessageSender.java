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
 * 短信渠道
 */
@Slf4j
@Component
public class SmsMessageSender extends AbstractMessageSender implements MessageSender {

    public SmsMessageSender(UserLoaderStrategy userLoader,
                            AntiSpamService antiSpamService,
                            IdempotencyService idempotencyService,
                            RateLimitService rateLimitService,
                            TemplateRenderer templateRenderer,
                            AuditLogService auditLogService) {
        super(userLoader, antiSpamService, idempotencyService, rateLimitService, templateRenderer, auditLogService);
    }

    @Override
    public MessageChannel channel() { return MessageChannel.SMS; }

    @Override
    protected boolean needSign() { return true; }

    @Override
    protected String sign(String content, SendContext ctx) {
        return "【" + ctx.getUser().getTenant() + "】" + content;
    }

    @Override
    protected String transformForChannel(String renderedContent, SendContext ctx) {
        // 极简：移除加粗Markdown
        return renderedContent.replace("**", "");
    }

    @Override
    protected SendResult doSend(String payload, SendContext ctx) {
        log.info("[SMS] to={} content={}", ctx.getUser().getPhone(), payload);
        // 模拟下游traceId
        return SendResult.ok("sms-" + ctx.getRequest().getRequestId());
    }
}

