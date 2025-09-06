package com.xk857.main.core.alert.template;

import org.springframework.stereotype.Component;

/**
 * Markdown 模板
 * 说明：将告警上下文渲染为 Markdown 文本，Bean 名称为 "markdown"。
 */
@Component("markdown")
public class MarkdownTemplate implements AlertTemplate {
    @Override
    public String render(AlertContext ctx) {
        return String.format("### [%s] %s\n\n> %s", ctx.getSeverity(), ctx.getTitle(), ctx.getSummary());
    }
}
