package com.github.zhangyanwei.workflow.step;

import com.github.zhangyanwei.exception.WorkflowException;

public interface Step {

    String getName();

    void process(Long userId, String request) throws WorkflowException;

    void process(Long userId, Long dataId, String request) throws WorkflowException;
}
