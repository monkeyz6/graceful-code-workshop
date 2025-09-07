package com.xk857.main.api.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送结果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SendResult", description = "消息发送结果")
public class SendResult {
    @Schema(description = "是否成功")
    private boolean success;

    @Schema(description = "结果码", example = "OK/SYS_ERR/REJECTED")
    private String code;

    @Schema(description = "结果描述")
    private String message;

    @Schema(description = "下游回执或追踪ID")
    private String traceId;

    public static SendResult ok(String traceId) {
        return SendResult.builder().success(true).code("OK").message("success").traceId(traceId).build();
    }

    public static SendResult fail(String msg) {
        return SendResult.builder().success(false).code("SYS_ERR").message(msg).build();
    }

    public static SendResult rejected(String msg) {
        return SendResult.builder().success(false).code("REJECTED").message(msg).build();
    }
}

