package com.xk857.main.api.message;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "SendPolicy", description = "发送策略：优先级与是否见好就收")
public record SendPolicy(
        @ArraySchema(schema = @Schema(description = "优先级顺序，例如: [SMS, WeCom, DingTalk]")) List<String> preferredOrder,
        @Schema(description = "是否在首个成功后停止") boolean breakOnFirstSuccess
) {
    public static SendPolicy broadcastOrder(List<String> order) {
        return new SendPolicy(order, false);
    }
}

