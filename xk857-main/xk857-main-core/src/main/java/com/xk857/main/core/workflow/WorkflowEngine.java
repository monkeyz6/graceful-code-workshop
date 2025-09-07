package com.xk857.main.core.workflow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * “总控室”：指挥工作流正向执行、失败后反向补偿
 */
@Slf4j
@Component
public final class WorkflowEngine {
    public void run(Workflow wf, FlowContext ctx) {
        var forwardIterator = wf.iterator();
        int lastSuccessStepIndex = -1;

        try {
            while (forwardIterator.hasNext()) {
                Step currentStep = forwardIterator.next();
                ctx.log("▶️ 执行步骤: " + currentStep.name());
                StepResult result = currentStep.apply(ctx);
                if (!result.success()) {
                    throw new Exception("步骤 " + currentStep.name() + " 失败: " + result.message());
                }
                lastSuccessStepIndex = forwardIterator.cursor() - 1;
            }
            ctx.log("🎉 工作流全部成功！");
        } catch (Exception e) {
            ctx.log("❌ 工作流异常: " + e.getMessage());
            ctx.log("--- 开始回滚 ---");
            var reverseIterator = wf.reverseIterator(lastSuccessStepIndex);
            while (reverseIterator.hasNext()) {
                Step stepToUndo = reverseIterator.next();
                if (stepToUndo instanceof Compensable c) {
                    ctx.log("⏪ 补偿步骤: " + stepToUndo.name());
                    try {
                        c.compensate(ctx);
                    } catch (Exception ex) {
                        log.warn("补偿步骤异常: {}", ex.getMessage());
                    }
                }
            }
            ctx.log("--- 回滚结束 ---");
        }
    }
}

