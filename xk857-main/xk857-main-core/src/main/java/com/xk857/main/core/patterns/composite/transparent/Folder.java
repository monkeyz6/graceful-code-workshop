package com.xk857.main.core.patterns.composite.transparent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 透明式-容器节点：文件夹
 */
public final class Folder implements Node {
    private final String name;
    private final List<Node> children = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void add(Node child) {
        children.add(child);
    }

    @Override
    public void remove(Node child) {
        children.remove(child);
    }

    @Override
    public List<Node> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void display(int depth) {
        System.out.println("-".repeat(Math.max(0, depth)) + name + "/");
        for (Node child : children) {
            child.display(depth + 2);
        }
    }
}

