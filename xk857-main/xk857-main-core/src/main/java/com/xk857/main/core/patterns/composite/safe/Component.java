package com.xk857.main.core.patterns.composite.safe;

/**
 * 组合模式-安全式：基础组件接口
 */
public interface Component {
    String name();
    void display(int depth);
}

