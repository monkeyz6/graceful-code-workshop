package com.xk857.framework.exception;

import lombok.Getter;

import java.io.Serial;
import java.text.MessageFormat;

/**
 * 业务异常类 - 使用建造者模式
 */
@Getter
public class BusinessException extends RuntimeException {
    
    @Serial
    private static final long serialVersionUID = 8724087800144511883L;

    private final int code;

    private BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public static Builder of(BaseErrorEnum errorEnum, Object... args) {
        return new Builder(errorEnum, args);
    }

    public static class Builder {
        private final int code;
        private final String msg;

        private Builder(BaseErrorEnum errorEnum, Object... args) {
            this.code = errorEnum.getCode();
            // 使用 MessageFormat 支持 {0} 风格的占位符
            this.msg = MessageFormat.format(errorEnum.getMsg(), args);
        }

        public BusinessException errThrow() {
            return new BusinessException(this.code, this.msg);
        }
    }
}
