package com.xk857.main.api.message;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(name = "AggregateResult", description = "聚合的发送结果，包含各渠道详情")
public record AggregateResult(
        @Schema(description = "是否整体成功")
        boolean success,

        @ArraySchema(schema = @Schema(description = "各渠道结果明细"))
        Map<String, SendResult> details
) {
}

