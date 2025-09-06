package com.xk857.framework.aop;

import com.xk857.framework.aop.annotation.Traceable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 日志与耗时追踪代理
 */
@Slf4j
@Aspect
@Component
public class TraceAspect {

    @Around("@annotation(traceable)")
    public Object around(ProceedingJoinPoint pjp, Traceable traceable) throws Throwable {
        String traceId = UUID.randomUUID().toString().substring(0, 8);
        String sign = pjp.getSignature().toShortString();
        long start = System.currentTimeMillis();
        log.info("[Trace:{}] -> {} {}", traceId, sign, traceable.value());
        try {
            Object ret = pjp.proceed();
            long cost = System.currentTimeMillis() - start;
            log.info("[Trace:{}] <- {} ({} ms)", traceId, sign, cost);
            return ret;
        } catch (Throwable t) {
            long cost = System.currentTimeMillis() - start;
            log.error("[Trace:{}] !! {} FAILED ({} ms): {}", traceId, sign, cost, t.getMessage(), t);
            throw t;
        }
    }
}

