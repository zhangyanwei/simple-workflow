package com.github.zhangyanwei.workflow.definition.builder;

import com.github.zhangyanwei.workflow.Maintainer;
import com.github.zhangyanwei.workflow.definition.RouteDefinition;
import com.github.zhangyanwei.workflow.definition.StepDefinition;
import com.github.zhangyanwei.workflow.definition.WorkflowDefinition;
import com.github.zhangyanwei.workflow.definition.builder.dsl.*;
import com.github.zhangyanwei.workflow.definition.builder.dsl.MaintainedBy.ViewBy;

import java.util.*;

import static com.github.zhangyanwei.workflow.definition.WorkflowDefinition.WorkflowLink;

public class WorkflowDefinitionBuilder<T extends Enum<T>, E extends Enum<E>> implements ForEntityType<T>, MaintainedBy, ViewBy<E>, Link, LinkTo, ThenLink {

    private String workFlowName;
    private T entityType;
    private Class<? extends Maintainer> maintainerType;
    private EnumSet<E> viewRoles;
    private EnumSet<E> editRoles;
    private EnumSet<E> deleteRoles;
    private Map<String, WorkflowLink> links;
    private Map<String, StepDefinition> stepDefinitions;
    private Map<String, RouteDefinition> routeDefinitions;
    private WorkflowLink current;

    private WorkflowDefinitionBuilder(String flowName) {
        workFlowName = flowName;
        links = new LinkedHashMap<>();
        stepDefinitions = new HashMap<>();
        routeDefinitions = new HashMap<>();
    }

    @Override
    public MaintainedBy entityType(T type) {
        this.entityType = type;
        return this;
    }

    @Override
    public ViewBy maintainer(Class<? extends Maintainer> maintainerType) {
        this.maintainerType = maintainerType;
        return this;
    }

    @Override
    public ViewBy viewBy(E ... roles) {
        this.viewRoles = EnumSet.copyOf(Arrays.asList(roles));
        return this;
    }

    @Override
    public ViewBy editBy(E ... roles) {
        this.editRoles = EnumSet.copyOf(Arrays.asList(roles));
        return this;
    }

    @Override
    public ViewBy deleteBy(E ... roles) {
        this.deleteRoles = EnumSet.copyOf(Arrays.asList(roles));
        return this;
    }

    @Override
    public LinkTo link(StepDefinition stepDefinition) {
        String stepName = stepDefinition.getStepName();
        WorkflowLink flowStep = new WorkflowLink(stepName);
        this.links.put(stepName, flowStep);
        this.stepDefinitions.put(stepName, stepDefinition);
        this.current = flowStep;
        return this;
    }

    @Override
    public ThenLink to(RouteDefinition routeDefinition, StepDefinition stepDefinition) {
        return to(routeDefinition.getRouteName(), routeDefinition, stepDefinition);
    }

    @Override
    public ThenLink to(String routeName, RouteDefinition routeDefinition, StepDefinition stepDefinition) {
        this.current.linkStep(routeName, stepDefinition.getStepName());
        this.stepDefinitions.putIfAbsent(stepDefinition.getStepName(), stepDefinition);
        this.routeDefinitions.putIfAbsent(routeName, routeDefinition);
        return this;
    }

    public static ForEntityType flow(String flowName) {
        return new WorkflowDefinitionBuilder(flowName);
    }

    @Override
    public WorkflowDefinition build() {
        WorkflowDefinition definition = new WorkflowDefinition();
        definition.setWorkflowName(this.workFlowName);
        definition.setEntityType(this.entityType);
        definition.setMaintainerType(this.maintainerType);
        definition.setViewRoles(this.viewRoles);
        definition.setEditRoles(this.editRoles);
        definition.setDeleteRoles(this.deleteRoles);
        definition.setLinks(this.links);
        definition.setStepDefinitions(this.stepDefinitions);
        definition.setRouteDefinitions(this.routeDefinitions);
        return definition;
    }
}
