package com.github.zhangyanwei.workflow.definition.builder.dsl;

import com.github.zhangyanwei.workflow.definition.StepDefinition;

public interface Link {
    LinkTo link(StepDefinition stepDefinition);
}
