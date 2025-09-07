package com.xk857.main.core.pipeline.message.support;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 极简模板渲染：支持 ${key} 占位符替换
 */
@Service
public class TemplateRenderer {
    public String render(String template, Map<String, Object> params) {
        if (template == null) return null;
        if (params == null || params.isEmpty()) return template;
        String result = template;
        for (Map.Entry<String, Object> e : params.entrySet()) {
            String ph = "${" + e.getKey() + "}";
            result = result.replace(ph, String.valueOf(e.getValue()));
        }
        return result;
    }
}

