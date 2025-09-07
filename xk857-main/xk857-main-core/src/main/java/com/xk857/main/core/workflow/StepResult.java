package com.xk857.main.core.workflow;

/**
 * 步骤执行结果
 */
public record StepResult(boolean success, String message) {
    public static StepResult ok() {
        return new StepResult(true, "OK");
    }

    public static StepResult fail(String msg) {
        return new StepResult(false, msg);
    }
}

