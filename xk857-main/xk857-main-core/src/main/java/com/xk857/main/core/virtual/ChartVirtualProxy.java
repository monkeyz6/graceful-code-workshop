package com.xk857.main.core.virtual;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * 虚拟代理：按需加载HeavyChart
 */
@Slf4j
public class ChartVirtualProxy implements Chart {
    private final Supplier<HeavyChart> supplier;
    private volatile HeavyChart target;

    public ChartVirtualProxy(Supplier<HeavyChart> supplier) {
        this.supplier = supplier;
    }

    @Override
    public String render() {
        if (target == null) {
            synchronized (this) {
                if (target == null) {
                    log.info("[VirtualProxy] Lazy init HeavyChart");
                    target = supplier.get();
                }
            }
        }
        return target.render();
    }
}

