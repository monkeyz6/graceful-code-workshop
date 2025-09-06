package com.xk857.main.core.alert.channel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送结果
 * 说明：抽象各渠道发送后的统一结果形态，包含成功标记、消息ID或错误信息。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendResult {
    private boolean success;
    private String messageId;
    private String error;

    public static SendResult ok(String id) {
        return new SendResult(true, id, null);
    }

    public static SendResult fail(String err) {
        return new SendResult(false, null, err);
    }
}
