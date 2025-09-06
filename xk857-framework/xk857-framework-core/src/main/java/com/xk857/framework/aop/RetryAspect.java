package com.xk857.framework.aop;

import com.xk857.framework.aop.annotation.RetryableLite;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 轻量级重试代理
 */
@Slf4j
@Aspect
@Component
public class RetryAspect {

    @Around("@annotation(retryableLite)")
    public Object withRetry(ProceedingJoinPoint pjp, RetryableLite retryableLite) throws Throwable {
        int attempts = Math.max(1, retryableLite.attempts());
        long delay = Math.max(0, retryableLite.delayMs());

        int i = 0;
        Throwable last = null;
        while (i < attempts) {
            try {
                return pjp.proceed();
            } catch (Throwable t) {
                last = t;
                i++;
                if (i >= attempts) {
                    log.warn("[RetryAspect] 尝试 {} 次后仍失败: {}", attempts, t.toString());
                    break;
                }
                log.warn("[RetryAspect] 失败第 {} 次，准备重试... {}", i, t.toString());
                if (delay > 0) {
                    try { Thread.sleep(delay); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
                }
            }
        }
        throw last;
    }
}

