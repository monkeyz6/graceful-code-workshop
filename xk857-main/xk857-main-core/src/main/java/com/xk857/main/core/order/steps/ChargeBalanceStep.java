package com.xk857.main.core.order.steps;

import com.xk857.main.core.order.AccountRepository;
import com.xk857.main.core.workflow.Compensable;
import com.xk857.main.core.workflow.FlowContext;
import com.xk857.main.core.workflow.Step;
import com.xk857.main.core.workflow.StepResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 扣减余额（可回滚）
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ChargeBalanceStep implements Step, Compensable {

    private final AccountRepository accountRepository;

    @Override
    public String name() {
        return "扣减余额";
    }

    @Override
    public StepResult apply(FlowContext ctx) {
        Long userId = (Long) ctx.data().get("userId");
        Integer qty = (Integer) ctx.data().get("quantity");
        boolean forcePayFail = Boolean.TRUE.equals(ctx.data().get("forcePayFail"));

        // 简化：假设单价固定 9.90
        BigDecimal amount = new BigDecimal("9.90").multiply(BigDecimal.valueOf(qty));

        if (forcePayFail) {
            return StepResult.fail("支付网关超时");
        }

        boolean ok = accountRepository.charge(userId, amount);
        if (!ok) {
            return StepResult.fail("余额不足");
        }
        ctx.data().put("balanceCharged", true);
        ctx.data().put("chargedAmount", amount);
        ctx.log("✅ 3. 扣款成功，金额 " + amount);
        return StepResult.ok();
    }

    @Override
    public void compensate(FlowContext ctx) {
        if (Boolean.TRUE.equals(ctx.data().get("balanceCharged"))) {
            Long userId = (Long) ctx.data().get("userId");
            BigDecimal amount = (BigDecimal) ctx.data().get("chargedAmount");
            accountRepository.refund(userId, amount);
            ctx.log("退款完成 (补偿)... 金额 " + amount);
        }
    }
}

