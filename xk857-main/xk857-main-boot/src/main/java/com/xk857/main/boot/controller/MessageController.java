package com.xk857.main.boot.controller;

import com.xk857.framework.common.Result;
import com.xk857.main.api.message.*;
import com.xk857.main.core.message.ChannelNames;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "消息外观演示")
@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    // 同步外观
    @Qualifier("defaultMessageFacade")
    private final MessageFacade defaultFacade;
    // 异步外观
    @Qualifier("asyncMessageFacade")
    private final MessageFacade asyncFacade;

    @Operation(summary = "广播到所有渠道（同步）")
    @PostMapping("/broadcast")
    public Result<AggregateResult> broadcast(@RequestBody MessageReq req) {
        return Result.success(defaultFacade.broadcast(req));
    }

    @Operation(summary = "按策略发送（同步，默认优先级：企微->钉钉->短信->站内）")
    @PostMapping("/send")
    public Result<AggregateResult> send(@RequestBody MessageReq req,
                                        @RequestParam(defaultValue = "true") boolean breakOnFirstSuccess) {
        SendPolicy policy = new SendPolicy(List.of(
                ChannelNames.WE_COM, ChannelNames.DING_TALK, ChannelNames.SMS, ChannelNames.INTERNAL
        ), breakOnFirstSuccess);
        return Result.success(defaultFacade.send(req, policy));
    }

    @Operation(summary = "广播到所有渠道（异步，立即返回queued）")
    @PostMapping("/broadcast-async")
    public Result<AggregateResult> broadcastAsync(@RequestBody MessageReq req) {
        return Result.success(asyncFacade.broadcast(req));
    }

    @Operation(summary = "按策略发送（异步，立即返回queued）")
    @PostMapping("/send-async")
    public Result<AggregateResult> sendAsync(@RequestBody MessageReq req,
                                             @RequestParam(defaultValue = "true") boolean breakOnFirstSuccess) {
        SendPolicy policy = new SendPolicy(List.of(
                ChannelNames.WE_COM, ChannelNames.DING_TALK, ChannelNames.SMS, ChannelNames.INTERNAL
        ), breakOnFirstSuccess);
        return Result.success(asyncFacade.send(req, policy));
    }
}

