package com.xk857.main.api.message;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MessageReq", description = "统一的消息请求模型")
public record MessageReq(
        @Schema(description = "标题") String title,
        @Schema(description = "内容") String content,
        @Schema(description = "接收者标识（手机号、用户ID、群Webhook等）") String receiver
) {}

