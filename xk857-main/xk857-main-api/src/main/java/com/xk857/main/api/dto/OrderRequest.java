package com.xk857.main.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 下单请求
 */
@Data
@Schema(name = "OrderRequest", description = "下单请求参数")
public class OrderRequest {

    @NotNull
    @Schema(description = "用户ID", example = "1")
    private Long userId;

    @NotNull
    @Schema(description = "商品ID", example = "1001")
    private Long productId;

    @NotNull
    @Min(1)
    @Schema(description = "购买数量", example = "2")
    private Integer quantity;

    @Schema(description = "是否强制支付失败(用于演示回滚)", example = "false")
    private Boolean forcePayFail = false;
}

