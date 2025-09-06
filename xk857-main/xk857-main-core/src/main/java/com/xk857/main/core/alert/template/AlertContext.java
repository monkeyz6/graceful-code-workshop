package com.xk857.main.core.alert.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 告警渲染上下文
 * 说明：模板渲染所需的最小上下文对象，包含标题、摘要与严重级别字符串。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertContext {
    private String title;
    private String summary;
    private String severity;
}
