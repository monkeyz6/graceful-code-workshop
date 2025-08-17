package com.xk857.framework.exception;

/**
 * 错误枚举基础接口
 */
public interface BaseErrorEnum {
    
    /**
     * 获取错误码
     */
    int getCode();
    
    /**
     * 获取错误消息
     */
    String getMsg();
}

