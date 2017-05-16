package com.github.zhangyanwei.workflow.definition.builder.dsl;

import com.github.zhangyanwei.workflow.definition.WorkflowDefinition;

public interface ThenLink extends Link, LinkTo {
    WorkflowDefinition build();
}
