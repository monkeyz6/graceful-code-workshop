package com.xk857.framework.enums;

import com.xk857.framework.exception.BaseErrorEnum;

/**
 * 系统级错误枚举
 */
public enum SystemErrorEnum implements BaseErrorEnum {

    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权访问"),
    FORBIDDEN(403, "拒绝访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂不可用"),
    
    VALIDATION_ERROR(1001, "参数校验失败: {0}"),
    DATA_ACCESS_ERROR(1002, "数据访问异常"),
    BUSINESS_ERROR(1003, "业务处理异常: {0}");

    private final int code;
    private final String msg;

    SystemErrorEnum(int code, String msg) {
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
