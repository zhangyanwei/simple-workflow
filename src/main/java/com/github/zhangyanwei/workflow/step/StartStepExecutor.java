package com.github.zhangyanwei.workflow.step;

import com.github.zhangyanwei.exception.WorkflowException;

import javax.annotation.Nonnull;

public interface StartStepExecutor<R> extends Executor {
    @Nonnull
    StepContext execute(Long userId, R request, DefaultStep.AdditionsAppender additionsAppender) throws WorkflowException;
}
