package com.xk857.main.core.patterns.composite.safe;

import java.util.List;

/**
 * 组合模式-安全式：容器接口
 */
public interface Composite extends Component {
    void add(Component child);
    void remove(Component child);
    List<Component> getChildren();
}

