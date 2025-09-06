package com.xk857.main.api.message;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SendResult", description = "单个渠道的发送结果")
public record SendResult(
        @Schema(description = "是否成功") boolean success,
        @Schema(description = "消息ID或追踪ID") String messageId,
        @Schema(description = "错误信息（失败时）") String error
) {
    public static SendResult ok(String messageId) {
        return new SendResult(true, messageId, null);
    }

    public static SendResult fail(String error) {
        return new SendResult(false, null, error);
    }
}

