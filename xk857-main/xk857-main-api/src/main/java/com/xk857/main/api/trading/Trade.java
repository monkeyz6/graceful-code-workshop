package com.xk857.main.api.trading;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交易指令
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Trade", description = "交易指令")
public class Trade {
    @Schema(description = "交易标的", example = "BTCUSDT")
    private String symbol;

    @Schema(description = "数量", example = "10")
    private int quantity;

    @Schema(description = "方向", example = "BUY")
    private String side;

    @Schema(description = "客户端ID", example = "client-01")
    private String clientId;
}

