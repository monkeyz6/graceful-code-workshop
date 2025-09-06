package com.xk857.main.core.patterns.composite.transparent;

import lombok.RequiredArgsConstructor;

/**
 * 透明式-叶子节点：文件
 */
@RequiredArgsConstructor
public final class FileLeaf implements Node {
    private final String name;
    private final long sizeBytes;

    @Override
    public String name() {
        return name;
    }

    @Override
    public void display(int depth) {
        System.out.println("-".repeat(Math.max(0, depth)) + name + " (" + sizeBytes + "B)");
    }

    public long getSizeBytes() {
        return sizeBytes;
    }
}

