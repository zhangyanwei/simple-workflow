package com.github.zhangyanwei.workflow.definition.builder.dsl;

import com.github.zhangyanwei.workflow.definition.RouteDefinition;
import com.github.zhangyanwei.workflow.definition.StepDefinition;

public interface LinkTo {
    ThenLink to(RouteDefinition routeDefinition, StepDefinition stepDefinition);
    ThenLink to(String routeName, RouteDefinition routeDefinition, StepDefinition stepDefinition);
}
