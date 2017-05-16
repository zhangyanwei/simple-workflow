package com.github.zhangyanwei.workflow.definition.builder;

import com.github.zhangyanwei.workflow.definition.RouteDefinition;
import com.github.zhangyanwei.workflow.route.RouteChecker;

public class RouteDefinitionBuilder {

    private String routeName;
    private Class<? extends RouteChecker> checkerType;

    private RouteDefinitionBuilder(String routeName) {
        this.routeName = routeName;
    }

    public RouteDefinitionBuilder checker(Class<? extends RouteChecker> checkerType) {
        this.checkerType = checkerType;
        return this;
    }

    public static RouteDefinitionBuilder route(String routeName) {
        return new RouteDefinitionBuilder(routeName);
    }

    public static RouteDefinition empty() {
        return new RouteDefinitionBuilder("empty").checker(EmptyChecker.class).build();
    }

    public RouteDefinition build() {
        RouteDefinition definition = new RouteDefinition();
        definition.setRouteName(this.routeName);
        definition.setCheckerType(this.checkerType);
        return definition;
    }

    public static class EmptyChecker implements RouteChecker {

        @Override
        public boolean check(Long dataId) {
            return true;
        }
    }
}
