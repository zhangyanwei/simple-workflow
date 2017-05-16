package com.github.zhangyanwei.workflow.definition;

import com.github.zhangyanwei.workflow.step.Executor;

import java.util.EnumSet;

public class StepDefinition<E extends Enum<E>> {

    private String stepName;
    private EnumSet<E> viewRoles;
    private EnumSet<E> executeRoles;
    private Class requestType;
    private Class<? extends Executor> executorType;

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public EnumSet<E> getViewRoles() {
        return viewRoles;
    }

    public void setViewRoles(EnumSet<E> viewRoles) {
        this.viewRoles = viewRoles;
    }

    public EnumSet<E> getExecuteRoles() {
        return executeRoles;
    }

    public void setExecuteRoles(EnumSet<E> executeRoles) {
        this.executeRoles = executeRoles;
    }

    public Class getRequestType() {
        return requestType;
    }

    public void setRequestType(Class requestType) {
        this.requestType = requestType;
    }

    public Class<? extends Executor> getExecutorType() {
        return executorType;
    }

    public void setExecutorType(Class<? extends Executor> executorType) {
        this.executorType = executorType;
    }

}
