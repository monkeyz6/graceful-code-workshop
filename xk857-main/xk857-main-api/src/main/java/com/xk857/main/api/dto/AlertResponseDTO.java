package com.xk857.main.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 告警发送响应DTO
 * 说明：返回发送是否成功、消息ID/错误信息，以及最终使用的渠道与严重级别，便于排查与审计。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AlertResponse", description = "发送告警响应")
public class AlertResponseDTO {
    @Schema(description = "是否成功")
    private boolean success;

    @Schema(description = "消息ID")
    private String messageId;

    @Schema(description = "错误信息")
    private String error;

    @Schema(description = "渠道")
    private String channel;

    @Schema(description = "严重级别")
    private String severity;
}
