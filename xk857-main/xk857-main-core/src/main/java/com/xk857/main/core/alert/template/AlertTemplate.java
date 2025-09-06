package com.xk857.main.core.alert.template;

/**
 * 告警模板接口
 * 说明：定义渲染策略，将上下文转为渠道可发送的字符串内容。
 */
public interface AlertTemplate {
    String render(AlertContext ctx);
}
