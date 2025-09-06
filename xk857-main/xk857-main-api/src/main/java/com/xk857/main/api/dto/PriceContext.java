package com.xk857.main.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 计价上下文
 */
@Data
@Schema(description = "计价上下文")
public class PriceContext {

    @Schema(description = "标价", example = "399.00")
    @NotNull(message = "标价不能为空")
    @DecimalMin(value = "0.00", message = "标价不能小于0")
    private BigDecimal listPrice;

    @Schema(description = "用户ID", example = "1")
    private Long userId;

    @Schema(description = "是否VIP（可选，未传则由系统模拟）", example = "true")
    private Boolean vip;

    @Schema(description = "是否新人（可选，未传则由系统模拟）", example = "false")
    private Boolean newUser;

    @Schema(description = "策略码，例如：DISCOUNT_VIP、VOUCHER_300_50、NEW_USER_SPECIAL、NORMAL", example = "DISCOUNT_VIP")
    @NotBlank(message = "策略码不能为空")
    private String strategyCode;
}

