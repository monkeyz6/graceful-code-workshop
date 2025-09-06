package com.xk857.framework.aop.annotation;

import java.lang.annotation.*;

/**
 * 轻量级重试注解（教学/演示用）
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryableLite {
    int attempts() default 3;
    long delayMs() default 100;
}

