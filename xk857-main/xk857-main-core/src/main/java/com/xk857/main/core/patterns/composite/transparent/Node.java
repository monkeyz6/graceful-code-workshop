package com.xk857.main.core.patterns.composite.transparent;

import java.util.Collections;
import java.util.List;

/**
 * 组合模式-透明式：统一接口，叶子默认不支持管理子节点
 */
public interface Node {
    String name();
    void display(int depth);

    default void add(Node child) {
        throw new UnsupportedOperationException("叶子节点不支持添加子节点");
    }
    default void remove(Node child) {
        throw new UnsupportedOperationException("叶子节点不支持移除子节点");
    }
    default List<Node> getChildren() {
        return Collections.emptyList();
    }
}

