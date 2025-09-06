package com.xk857.main.core.virtual;

import lombok.extern.slf4j.Slf4j;

/**
 * 重型对象，创建代价高
 */
@Slf4j
public class HeavyChart implements Chart {
    private final String dataset;

    public HeavyChart(String dataset) {
        this.dataset = dataset;
        // 模拟昂贵初始化
        try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        log.info("HeavyChart initialized with dataset={}", dataset);
    }

    @Override
    public String render() {
        return "[HeavyChart] Rendered:" + dataset;
    }
}

