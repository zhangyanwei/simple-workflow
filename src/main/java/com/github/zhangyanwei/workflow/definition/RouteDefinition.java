package com.github.zhangyanwei.workflow.definition;

import com.github.zhangyanwei.workflow.route.RouteChecker;

public class RouteDefinition {

    private String routeName;
    private Class<? extends RouteChecker> checkerType;

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Class<? extends RouteChecker> getCheckerType() {
        return checkerType;
    }

    public void setCheckerType(Class<? extends RouteChecker> checkerType) {
        this.checkerType = checkerType;
    }
}
