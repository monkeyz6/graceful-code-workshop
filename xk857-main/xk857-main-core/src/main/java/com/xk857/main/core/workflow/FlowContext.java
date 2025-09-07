package com.xk857.main.core.workflow;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 流转上下文：承载数据与流程日志
 */
@Slf4j
@Getter
@Component
public final class FlowContext {
    private final Map<String, Object> data = new HashMap<>();
    private final List<String> logs = new ArrayList<>();

    public Map<String, Object> data() {
        return data;
    }

    public List<String> logs() {
        return logs;
    }

    public void log(String s) {
        logs.add(s);
        log.info(s);
    }
}

