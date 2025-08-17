package com.xk857.framework.processor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 原始响应注解
 * 标记此注解的方法或类将不会被全局响应拦截器处理，直接返回原始数据
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RawResponse {
}
