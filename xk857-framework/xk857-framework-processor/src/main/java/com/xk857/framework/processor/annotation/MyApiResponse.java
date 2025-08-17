package com.xk857.framework.processor.annotation;

import com.xk857.framework.constants.CommonConstants;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * API响应注解
 * 用于自定义响应的状态码和消息
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyApiResponse {
    
    /**
     * HTTP状态码
     */
    int httpCode() default CommonConstants.HTTP_SUCCESS;
    
    /**
     * 业务状态码
     */
    int code() default CommonConstants.SUCCESS_CODE;
    
    /**
     * 响应消息
     */
    String msg() default CommonConstants.SUCCESS_MESSAGE;
}
