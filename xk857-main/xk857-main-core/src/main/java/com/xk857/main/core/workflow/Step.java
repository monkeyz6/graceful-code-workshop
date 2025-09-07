package com.xk857.main.core.workflow;

/**
 * 工作流步骤
 */
public interface Step {

    /**
     * 返回步骤名称，用于日志记录与可视化展示。
     */
    String name();

    /**
     * 执行当前步骤的业务逻辑。
     *
     * @param ctx 流转上下文（承载共享数据与流程日志）
     * @return 步骤执行结果；当 success=false 时，工作流引擎将触发补偿回滚
     * @throws Exception 允许抛出异常，由工作流引擎统一捕获处理
     */
    StepResult apply(FlowContext ctx) throws Exception;
}
