package com.xk857.main.boot.controller;

import com.xk857.main.api.dto.OrderRequest;
import com.xk857.main.api.dto.OrderResultDTO;
import com.xk857.main.api.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Tag(name = "下单流程演示", description = "基于迭代器+补偿的工作流示例")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "下单", description = "演示可回滚的下单流程")
    @PostMapping("/place")
    public OrderResultDTO placeOrder(@Valid @RequestBody OrderRequest request) {
        return orderService.placeOrder(request);
    }
}

