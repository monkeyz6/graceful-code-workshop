package com.xk857.framework.common;

import com.xk857.framework.constants.CommonConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结果类
 */
@Data
@NoArgsConstructor
public class Result<T> {
    
    /**
     * 是否成功
     */
    private Boolean success;
    
    /**
     * 响应数据
     */
    private T data;
    
    /**
     * 业务状态码
     */
    private Integer code;
    
    /**
     * 响应消息
     */
    private String message;

    /**
     * 失败响应
     */
    public static <T> Result<T> fail(int code, String message) {
        Result<T> response = new Result<>();
        response.setSuccess(false);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    /**
     * 成功响应（带数据）
     */
    public static <T> Result<T> success(T data) {
        return success(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_MESSAGE, data);
    }
    
    /**
     * 成功响应（无数据）
     */
    public static <T> Result<T> success() {
        return success(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_MESSAGE, null);
    }
    
    /**
     * 成功响应（自定义消息）
     */
    public static <T> Result<T> success(String message, T data) {
        return success(CommonConstants.SUCCESS_CODE, message, data);
    }

    /**
     * 成功响应（完整参数）
     */
    public static <T> Result<T> success(int code, String message, T data) {
        Result<T> response = new Result<>();
        response.setSuccess(true);
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
    
    /**
     * 失败响应（简化版本）
     */
    public static <T> Result<T> error(String message) {
        return fail(CommonConstants.ERROR_CODE, message);
    }
    
    /**
     * 失败响应（自定义错误码）
     */
    public static <T> Result<T> error(Integer code, String message) {
        return fail(code, message);
    }
}
