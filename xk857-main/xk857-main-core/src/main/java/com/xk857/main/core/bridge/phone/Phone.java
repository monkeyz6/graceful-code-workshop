package com.xk857.main.core.bridge.phone;

/**
 * 抽象层：手机
 * 说明：桥接示例中的抽象角色，组合 Color 实现层并定义品牌手机的行为。
 */
public abstract class Phone {
    protected final Color color;

    protected Phone(Color color) {
        this.color = color;
    }

    public abstract void run();
}
