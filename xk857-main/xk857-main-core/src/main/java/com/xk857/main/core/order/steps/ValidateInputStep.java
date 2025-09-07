package com.xk857.main.core.order.steps;

import com.xk857.main.core.workflow.FlowContext;
import com.xk857.main.core.workflow.Step;
import com.xk857.main.core.workflow.StepResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 校验参数步骤
 */
@Slf4j
@Component
public class ValidateInputStep implements Step {
    @Override
    public String name() {
        return "校验参数";
    }

    @Override
    public StepResult apply(FlowContext ctx) {
        Long userId = (Long) ctx.data().get("userId");
        Long productId = (Long) ctx.data().get("productId");
        Integer qty = (Integer) ctx.data().get("quantity");

        if (userId == null || userId <= 0) {
            return StepResult.fail("用户ID非法");
        }
        if (productId == null || productId <= 0) {
            return StepResult.fail("商品ID非法");
        }
        if (qty == null || qty <= 0) {
            return StepResult.fail("数量必须大于0");
        }

        ctx.log("✅ 1. 参数校验通过");
        return StepResult.ok();
    }
}

