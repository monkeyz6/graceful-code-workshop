package com.xk857.main.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 下单结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "OrderResultDTO", description = "下单结果返回体")
public class OrderResultDTO {
    @Schema(description = "是否成功")
    private boolean success;

    @Schema(description = "提示信息")
    private String message;

    @Schema(description = "流程日志")
    private List<String> logs;
}

