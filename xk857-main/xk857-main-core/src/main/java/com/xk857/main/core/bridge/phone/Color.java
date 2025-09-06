package com.xk857.main.core.bridge.phone;

/**
 * 实现层：颜色
 * 说明：桥接示例中可独立变化的维度之一，供 Phone 抽象层组合。
 */
public interface Color {
    String render();
}
