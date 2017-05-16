package com.github.zhangyanwei.workflow;

import com.github.zhangyanwei.exception.WorkflowException;

public interface Retriever<R> {
    R retrieve(Long id) throws WorkflowException;
}
