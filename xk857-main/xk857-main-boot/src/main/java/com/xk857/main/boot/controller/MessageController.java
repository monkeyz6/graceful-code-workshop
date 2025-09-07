package com.xk857.main.boot.controller;

import com.xk857.framework.processor.annotation.APIVersion;
import com.xk857.framework.processor.enmu.APIVersionEnum;
import com.xk857.main.api.message.MessageRequest;
import com.xk857.main.api.message.SendResult;
import com.xk857.main.core.pipeline.message.MessageSendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息发送调试入口
 */
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Validated
@APIVersion(APIVersionEnum.V1)
@Tag(name = "消息发送", description = "模板方法-消息发送流水线")
public class MessageController {

    private final MessageSendService messageSendService;

    @Operation(summary = "发送消息（遵循固定黄金流程）")
    @PostMapping("/send")
    public SendResult send(@Valid @RequestBody MessageRequest req) {
        return messageSendService.send(req);
    }
}
