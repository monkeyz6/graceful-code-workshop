package com.xk857.main.core.pipeline.message;

import com.xk857.main.api.message.MessageRequest;
import com.xk857.main.api.message.SendResult;
import com.xk857.main.core.pipeline.message.model.SendContext;
import com.xk857.main.core.pipeline.message.model.user.UserProfile;
import com.xk857.main.core.pipeline.message.strategy.UserLoaderStrategy;
import com.xk857.main.core.pipeline.message.support.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 模板方法：定义黄金流程
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMessageSender implements MessageSender {

    private final UserLoaderStrategy userLoader;
    private final AntiSpamService antiSpamService;
    private final IdempotencyService idempotencyService;
    private final RateLimitService rateLimitService;
    private final TemplateRenderer templateRenderer;
    private final AuditLogService auditLogService;

    @Override
    public final SendResult send(MessageRequest request) {
        long start = System.nanoTime();
        try {
            // 1. 基础校验
            validate(request);

            // 2. 加载用户
            UserProfile user = loadUser(request.getUserId());
            if (user == null) {
                return auditLogService.auditAndReturn(request, null, SendResult.rejected("用户不存在"), start);
            }
            if (user.isBlacklisted()) {
                return auditLogService.auditAndReturn(request, user, SendResult.rejected("用户在黑名单"), start);
            }

            // 3. 构建上下文
            SendContext ctx = buildContext(request, user);

            // 4. 前置检查：反垃圾、幂等、限流
            if (!passAntiSpam(ctx)) {
                return auditLogService.auditAndReturn(request, user, SendResult.rejected("触发反垃圾"), start);
            }
            if (!passIdempotency(ctx)) {
                return auditLogService.auditAndReturn(request, user, SendResult.rejected("重复请求"), start);
            }
            if (!passRateLimit(ctx)) {
                return auditLogService.auditAndReturn(request, user, SendResult.rejected("限流拒绝"), start);
            }

            // 5. 渲染模板
            String rendered = renderTemplate(ctx);

            // 6. 渠道转换
            String transformed = transformForChannel(rendered, ctx);

            // 7. 可选签名/加密
            if (needSign()) transformed = sign(transformed, ctx);
            if (needEncrypt()) transformed = encrypt(transformed, ctx);

            // 8. 实际发送
            SendResult result = doSend(transformed, ctx);

            // 9. 发送后钩子
            try {
                postHooks(ctx, result);
            } catch (Exception ex) {
                log.warn("postHooks error", ex);
            }

            return auditLogService.auditAndReturn(request, user, result, start);
        } catch (Exception e) {
            log.error("send error", e);
            return auditLogService.auditAndReturn(request, null, SendResult.fail("系统异常: " + e.getMessage()), start);
        }
    }

    // 固定步骤实现
    protected void validate(MessageRequest req) {
        if (req.getUserId() == null) throw new IllegalArgumentException("userId不能为空");
        if (req.getRequestId() == null || req.getRequestId().isBlank())
            throw new IllegalArgumentException("requestId不能为空");
        if (req.getContent() == null && (req.getTemplateCode() == null || req.getTemplateCode().isBlank())) {
            throw new IllegalArgumentException("content和templateCode至少一个不能为空");
        }
    }

    protected UserProfile loadUser(long userId) {
        return userLoader.load(userId);
    }

    protected SendContext buildContext(MessageRequest req, UserProfile user) {
        String base = req.getContent();
        if ((base == null || base.isBlank()) && req.getTemplateCode() != null) {
            // 示例：使用模板编码本身作为模板内容（真实场景应查询模板），用于演示
            base = req.getTemplateCode();
        }
        String rendered = templateRenderer.render(base, req.getParams());
        return SendContext.builder().request(req).user(user).renderedContent(rendered).build();
    }

    protected boolean passAntiSpam(SendContext ctx) {
        return antiSpamService.pass(ctx.getRenderedContent());
    }

    protected boolean passIdempotency(SendContext ctx) {
        return idempotencyService.firstSeen(ctx.getRequest().getRequestId());
    }

    protected boolean passRateLimit(SendContext ctx) {
        String key = ctx.getUser().getUserId() + ":" + ctx.getRequest().getChannel();
        return rateLimitService.allow(key);
    }

    protected String renderTemplate(SendContext ctx) {
        return ctx.getRenderedContent();
    }

    // 钩子
    protected boolean needSign() {
        return false;
    }

    protected String sign(String content, SendContext ctx) {
        return content;
    }

    protected boolean needEncrypt() {
        return false;
    }

    protected String encrypt(String content, SendContext ctx) {
        return content;
    }

    protected void postHooks(SendContext ctx, SendResult result) { /* 可选 */ }

    // 子类必须实现
    protected abstract String transformForChannel(String renderedContent, SendContext ctx);

    protected abstract SendResult doSend(String payload, SendContext ctx);
}

