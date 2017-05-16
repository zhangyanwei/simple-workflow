package com.github.zhangyanwei.workflow.step;

import com.github.zhangyanwei.exception.WorkflowException;
import com.github.zhangyanwei.exception.ForbiddenException;

public interface StepExecutor<R> extends Executor {
    void execute(Long userId, Long dataId, R request, DefaultStep.AdditionsAppender additionsAppender) throws WorkflowException;
    default void checkDataAccess(Long userId, Long dataId) throws ForbiddenException {}
}
