package com.xk857.main.core.alert.template;

import org.springframework.stereotype.Component;

/**
 * 纯文本模板
 * 说明：将告警上下文渲染为简单的文本格式，Bean 名称为 "text"。
 */
@Component("text")
public class TextTemplate implements AlertTemplate {
    @Override
    public String render(AlertContext ctx) {
        return String.format("[%s] %s | %s", ctx.getSeverity(), ctx.getTitle(), ctx.getSummary());
    }
}
