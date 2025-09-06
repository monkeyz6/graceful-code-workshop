package com.xk857.framework.aop.annotation;

import java.lang.annotation.*;

/**
 * 方法调用埋点与耗时追踪
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Traceable {
    String value() default "";
}

