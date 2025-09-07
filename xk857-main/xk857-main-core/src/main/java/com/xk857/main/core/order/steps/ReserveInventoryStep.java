package com.xk857.main.core.order.steps;

import com.xk857.main.core.order.InventoryRepository;
import com.xk857.main.core.workflow.Compensable;
import com.xk857.main.core.workflow.FlowContext;
import com.xk857.main.core.workflow.Step;
import com.xk857.main.core.workflow.StepResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 预留库存（可回滚）
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReserveInventoryStep implements Step, Compensable {

    private final InventoryRepository inventoryRepository;

    @Override
    public String name() {
        return "预留库存";
    }

    @Override
    public StepResult apply(FlowContext ctx) {
        Long productId = (Long) ctx.data().get("productId");
        Integer qty = (Integer) ctx.data().get("quantity");
        int before = inventoryRepository.getStock(productId);
        if (before < qty) {
            return StepResult.fail("库存不足");
        }
        boolean ok = inventoryRepository.reserve(productId, qty);
        if (!ok) {
            return StepResult.fail("库存预留失败");
        }
        ctx.data().put("inventoryReserved", true);
        ctx.log("✅ 2. 库存预留成功，商品" + productId + "，数量" + qty);
        return StepResult.ok();
    }

    @Override
    public void compensate(FlowContext ctx) {
        if (Boolean.TRUE.equals(ctx.data().get("inventoryReserved"))) {
            Long productId = (Long) ctx.data().get("productId");
            Integer qty = (Integer) ctx.data().get("quantity");
            inventoryRepository.release(productId, qty);
            ctx.log("释放已预留库存 (补偿)... 商品" + productId + " 返还数量 " + qty);
        }
    }
}
