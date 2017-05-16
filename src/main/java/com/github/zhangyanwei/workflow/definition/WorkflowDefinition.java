package com.github.zhangyanwei.workflow.definition;

import com.github.zhangyanwei.workflow.Maintainer;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WorkflowDefinition<T extends Enum<T>, E extends Enum<E>> {

    private String workflowName;
    private T entityType;
    private Class<? extends Maintainer> maintainerType;
    private EnumSet<E> viewRoles;
    private EnumSet<E> editRoles;
    private EnumSet<E> deleteRoles;
    private Map<String, WorkflowLink> links;
    private Map<String, StepDefinition> stepDefinitions;
    private Map<String, RouteDefinition> routeDefinitions;

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public void setEntityType(T entityType) {
        this.entityType = entityType;
    }

    public T getEntityType() {
        return entityType;
    }

    public void setMaintainerType(Class<? extends Maintainer> maintainerType) {
        this.maintainerType = maintainerType;
    }

    public EnumSet<E> getViewRoles() {
        return viewRoles;
    }

    public void setViewRoles(EnumSet<E> viewRoles) {
        this.viewRoles = EnumSet.copyOf(viewRoles);
    }

    public EnumSet<E> getEditRoles() {
        return editRoles;
    }

    public void setEditRoles(EnumSet<E> editRoles) {
        this.editRoles = EnumSet.copyOf(editRoles);
    }

    public EnumSet<E> getDeleteRoles() {
        return deleteRoles;
    }

    public void setDeleteRoles(EnumSet<E> deleteRoles) {
        this.deleteRoles = EnumSet.copyOf(deleteRoles);
    }

    public Class<? extends Maintainer> getMaintainerType() {
        return maintainerType;
    }

    public Map<String, WorkflowLink> getLinks() {
        return links;
    }

    public void setLinks(Map<String, WorkflowLink> links) {
        this.links = links;
    }

    public Map<String, StepDefinition> getStepDefinitions() {
        return stepDefinitions;
    }

    public void setStepDefinitions(Map<String, StepDefinition> stepDefinitions) {
        this.stepDefinitions = stepDefinitions;
    }

    public Map<String, RouteDefinition> getRouteDefinitions() {
        return routeDefinitions;
    }

    public void setRouteDefinitions(Map<String, RouteDefinition> routeDefinitions) {
        this.routeDefinitions = routeDefinitions;
    }

    public static class WorkflowLink {

        private String stepName;
        private Map<String, String> linkedSteps;

        public WorkflowLink(String stepName) {
            this.stepName = stepName;
            this.linkedSteps = new HashMap<>();
        }

        public String getStepName() {
            return stepName;
        }

        public String getLinkedStep(String route) {
            return this.linkedSteps.get(route);
        }

        public void linkStep(String route, String stepName) {
            this.linkedSteps.put(route, stepName);
        }

        public Set<String> routes() {
            return this.linkedSteps.keySet();
        }
    }
}
