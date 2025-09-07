package com.xk857.main.core.order.steps;

import com.xk857.main.core.order.NotificationGateway;
import com.xk857.main.core.workflow.FlowContext;
import com.xk857.main.core.workflow.Step;
import com.xk857.main.core.workflow.StepResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 发送通知（不可回滚）
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NotifyUserStep implements Step {

    private final NotificationGateway notificationGateway;

    @Override
    public String name() {
        return "发送通知";
    }

    @Override
    public StepResult apply(FlowContext ctx) {
        Long userId = (Long) ctx.data().get("userId");
        notificationGateway.send(userId, "下单成功通知");
        ctx.log("✅ 4. 通知发送成功");
        return StepResult.ok();
    }
}

