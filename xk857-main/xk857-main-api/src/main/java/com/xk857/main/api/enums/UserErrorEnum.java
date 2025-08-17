package com.xk857.main.api.enums;

import com.xk857.framework.exception.BaseErrorEnum;

/**
 * 用户相关错误枚举
 */
public enum UserErrorEnum implements BaseErrorEnum {

    USER_IS_NULL(3001, "查询用户失败"),
    USER_NOT_FOUND(3002, "用户不存在，用户ID: {0}"),
    INVALID_USER_ID(3003, "用户ID不能为空或小于等于0"),
    USER_AGE_INVALID(3004, "用户年龄范围无效，最小年龄: {0}，最大年龄: {1}"),
    USER_QUERY_PARAM_INVALID(3005, "用户查询参数无效: {0}");

    private final int code;
    private final String msg;

    UserErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
