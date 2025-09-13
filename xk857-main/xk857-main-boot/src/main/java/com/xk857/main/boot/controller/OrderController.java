package com.xk857.main.boot.controller;

import com.xk857.framework.common.Result;
import com.xk857.main.boot.annotation.WorkshopDemo;
import com.xk857.main.boot.model.Order;
import com.xk857.main.boot.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redisson/order")
@RequiredArgsConstructor
@Tag(name = "RDelayedQueue演示", description = "下单-支付-到期取消")
@WorkshopDemo(scene = "订单延迟取消", focus = "延迟入队+阻塞消费")
public class OrderController {

    private final OrderService orderService;

    /**
     * 演示：默认延迟 5 秒，便于快速观察到期取消。
     * 生产建议：15~30 分钟等业务合理超时时间，并用延迟队列/任务系统承载。
     */
    @Operation(summary = "创建订单(默认延迟5秒)")
    @PostMapping("/create")
    public Result<Order> create(@RequestParam(defaultValue = "5") long delaySeconds) {
        return Result.success(orderService.createOrder(delaySeconds));
    }

    @Operation(summary = "支付订单(移除延迟任务)")
    @PostMapping("/pay")
    public Result<Boolean> pay(@RequestParam String id) {
        return Result.success(orderService.pay(id));
    }

    @Operation(summary = "查询订单")
    @GetMapping("/get")
    public Result<Order> get(@RequestParam String id) {
        return Result.success(orderService.get(id));
    }
}
