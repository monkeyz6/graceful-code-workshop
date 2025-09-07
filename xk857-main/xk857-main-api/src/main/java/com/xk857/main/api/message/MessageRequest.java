package com.xk857.main.api.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "MessageRequest", description = "消息发送请求")
public class MessageRequest {
    @Schema(description = "渠道", example = "SMS")
    @NotNull(message = "channel不能为空")
    private MessageChannel channel;

    @Schema(description = "用户ID", example = "1")
    @NotNull(message = "userId不能为空")
    private Long userId;

    @Schema(description = "模板编码，可选")
    private String templateCode;

    @Schema(description = "模板参数，可选")
    private Map<String, Object> params;

    @Schema(description = "直接发送的内容，可选，当无模板时使用")
    private String content;

    @Schema(description = "幂等请求ID，用于去重", example = "req-001")
    @NotBlank(message = "requestId不能为空")
    private String requestId;
}

