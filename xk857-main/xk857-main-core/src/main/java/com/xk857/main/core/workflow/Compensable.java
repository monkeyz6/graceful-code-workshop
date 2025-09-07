package com.xk857.main.core.workflow;

/**
 * 可补偿(回滚)步骤
 */
public interface Compensable {

    /**
     * 当工作流中后续步骤失败时，用于对本步骤进行补偿/回滚的逻辑。
     * 仅对已成功执行过的步骤生效，由引擎以“反向遍历”顺序调用。
     *
     * @param ctx 流转上下文（可根据之前放入的数据执行补偿）
     */
    void compensate(FlowContext ctx);
}
