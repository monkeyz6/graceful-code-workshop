package com.xk857.main.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 告警发送请求DTO
 * 说明：承载对外接口接收的告警入参（标题/摘要/严重级别），
 * 可通过 channel/template 字段覆盖配置中心中为不同严重级别指定的默认渠道与模板。
 */
@Data
@Schema(name = "AlertRequest", description = "发送告警请求")
public class AlertRequestDTO {
    @Schema(description = "标题", example = "支付故障")
    @NotBlank(message = "标题不能为空")
    private String title;

    @Schema(description = "摘要", example = "成功率低于60%")
    @NotBlank(message = "摘要不能为空")
    private String summary;

    @Schema(description = "严重级别：INFO/WARN/CRITICAL", example = "CRITICAL")
    private String severity;

    @Schema(description = "发送渠道，可覆盖配置：sms/feishu/email", example = "feishu")
    private String channel;

    @Schema(description = "渲染模板，可覆盖配置：text/markdown", example = "markdown")
    private String template;
}
