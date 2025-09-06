package com.xk857.main.api.enums;

import com.xk857.framework.exception.BaseErrorEnum;

/**
 * 计价相关错误码
 */
public enum PricingErrorEnum implements BaseErrorEnum {
    UNKNOWN_STRATEGY(4001, "未知的计价策略: {0}"),
    INVALID_PRICE(4002, "无效的价格: {0}"),
    ;

    private final int code;
    private final String msg;

    PricingErrorEnum(int code, String msg) {
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

