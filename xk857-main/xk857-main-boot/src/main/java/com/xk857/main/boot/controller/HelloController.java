package com.xk857.main.boot.controller;

import com.xk857.framework.constants.ApplicationConstants;
import com.xk857.main.boot.dto.WelcomeResponse;
import com.xk857.main.boot.manager.EndpointManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "Hello接口", description = "基础问候和系统信息接口")
public class HelloController {

    @Autowired
    private EndpointManager endpointManager;

    @Operation(summary = "欢迎页面", description = "获取项目介绍和可用接口列表")
    @GetMapping("/")
    public WelcomeResponse welcome() {
        log.info("访问欢迎页面");
        return endpointManager.getWelcomeResponse();
    }

    @Operation(summary = "基础问候", description = "返回基础的问候语")
    @GetMapping("/hello")
    public String hello() {
        return ApplicationConstants.HELLO_MESSAGE;
    }

    @Operation(summary = "个性化问候", description = "根据传入的姓名返回个性化问候语")
    @GetMapping("/hello/name")
    public String helloWithName(@Parameter(description = "用户姓名") @RequestParam String name) {
        return String.format(ApplicationConstants.HELLO_NAME_MESSAGE_TEMPLATE, name);
    }
}