package com.xk857.main.core.alert.factory;

import com.xk857.main.core.alert.template.AlertTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 模板工厂
 * 说明：基于 Spring 的按名称注入（Map<String, AlertTemplate>），按配置或入参选择具体模板实现。
 */
@Component
public class TemplateFactory {
    private final Map<String, AlertTemplate> templateMap;

    public TemplateFactory(Map<String, AlertTemplate> templateMap) {
        this.templateMap = templateMap;
    }

    public AlertTemplate getTemplate(String name) {
        AlertTemplate template = templateMap.get(name);
        if (template == null) {
            throw new IllegalArgumentException("Unsupported template: " + name);
        }
        return template;
    }
}
