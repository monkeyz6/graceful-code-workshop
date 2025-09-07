package com.xk857.main.core.service;

import com.xk857.main.api.dto.OrderRequest;
import com.xk857.main.api.dto.OrderResultDTO;
import com.xk857.main.api.service.OrderService;
import com.xk857.main.core.order.steps.ChargeBalanceStep;
import com.xk857.main.core.order.steps.NotifyUserStep;
import com.xk857.main.core.order.steps.ReserveInventoryStep;
import com.xk857.main.core.order.steps.ValidateInputStep;
import com.xk857.main.core.workflow.FlowContext;
import com.xk857.main.core.workflow.Workflow;
import com.xk857.main.core.workflow.WorkflowEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 订单服务实现（基于通用工作流引擎）
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final WorkflowEngine engine;
    private final ValidateInputStep validateInputStep;
    private final ReserveInventoryStep reserveInventoryStep;
    private final ChargeBalanceStep chargeBalanceStep;
    private final NotifyUserStep notifyUserStep;
    // 使用独立的上下文实例，避免并发污染

    @Override
    public OrderResultDTO placeOrder(OrderRequest request) {
        // 准备上下文（每次创建新实例）
        FlowContext ctx = new FlowContext();
        ctx.data().put("userId", request.getUserId());
        ctx.data().put("productId", request.getProductId());
        ctx.data().put("quantity", request.getQuantity());
        ctx.data().put("forcePayFail", Boolean.TRUE.equals(request.getForcePayFail()));

        // 组装工作流
        Workflow wf = new Workflow()
                .add(validateInputStep)
                .add(reserveInventoryStep)
                .add(chargeBalanceStep)
                .add(notifyUserStep);

        engine.run(wf, ctx);

        boolean success = ctx.logs().stream().anyMatch(s -> s.contains("工作流全部成功"));
        String message = success ? "下单成功" : "下单失败";
        return new OrderResultDTO(success, message, ctx.logs());
    }
}
