package com.xk857.main.core.patterns.composite.safe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 安全式-容器实现
 */
public final class Folder implements Composite {
    private final String name;
    private final List<Component> children = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void add(Component child) {
        children.add(child);
    }

    @Override
    public void remove(Component child) {
        children.remove(child);
    }

    @Override
    public List<Component> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void display(int depth) {
        System.out.println("-".repeat(Math.max(0, depth)) + name + "/");
        for (Component c : children) {
            c.display(depth + 2);
        }
    }
}

