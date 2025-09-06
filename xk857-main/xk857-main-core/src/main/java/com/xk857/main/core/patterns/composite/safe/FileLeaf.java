package com.xk857.main.core.patterns.composite.safe;

import lombok.RequiredArgsConstructor;

/**
 * 安全式-叶子节点
 */
@RequiredArgsConstructor
public final class FileLeaf implements Component {
    private final String name;

    @Override
    public String name() {
        return name;
    }

    @Override
    public void display(int depth) {
        System.out.println("-".repeat(Math.max(0, depth)) + name);
    }
}

