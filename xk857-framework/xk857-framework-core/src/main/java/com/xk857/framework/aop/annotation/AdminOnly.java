package com.xk857.framework.aop.annotation;

import java.lang.annotation.*;

/**
 * 管理员专属操作注解
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminOnly {
}

