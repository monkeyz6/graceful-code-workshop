package com.xk857.main.api.pay.dto;

import com.xk857.main.api.annotation.KeyComponent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 订单（示例）。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@KeyComponent
@Schema(name = "Order", description = "支付订单示例")
public class Order {
    @Schema(description = "幂等唯一键", example = "order-key-123")
    private String uniqueKey;

    @Schema(description = "用户ID", example = "1")
    private Long userId;

    @Schema(description = "支付金额", example = "100.00")
    private BigDecimal amount;

    @Schema(description = "备注，用于模拟失败策略，如: FAIL_FIRST_2", example = "FAIL_FIRST_2")
    private String remark;
}

