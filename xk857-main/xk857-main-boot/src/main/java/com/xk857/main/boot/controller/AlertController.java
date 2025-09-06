package com.xk857.main.boot.controller;

import com.xk857.framework.processor.annotation.APIVersion;
import com.xk857.framework.processor.enmu.APIVersionEnum;
import com.xk857.main.api.dto.AlertRequestDTO;
import com.xk857.main.api.dto.AlertResponseDTO;
import com.xk857.main.api.service.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 告警演示控制器
 * 说明：对外提供告警发送接口，演示桥接模式在“严重级别×渠道×模板”的正交解耦与配置化切换。
 */
@RestController
@APIVersion(APIVersionEnum.V1)
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
@Validated
@Tag(name = "告警演示", description = "桥接模式：严重级别×渠道×模板 正交解耦")
public class AlertController {

    private final AlertService alertService;

    @Operation(summary = "发送告警（可按配置或覆盖渠道/模板）")
    @PostMapping("/send")
    public AlertResponseDTO send(@RequestBody @Valid AlertRequestDTO request) {
        return alertService.send(request);
    }
}
