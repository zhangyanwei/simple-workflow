package com.github.zhangyanwei.workflow.definition.builder;

import com.github.zhangyanwei.workflow.definition.StepDefinition;
import com.github.zhangyanwei.workflow.step.Executor;

import java.util.Arrays;
import java.util.EnumSet;

public class StepDefinitionBuilder<E extends Enum<E>> {

    private String stepName;
    private EnumSet<E> viewRoles;
    private EnumSet<E> executeRoles;
    private Class requestType;
    private Class<? extends Executor> executorType;

    private StepDefinitionBuilder(String stepName) {
        this.stepName = stepName;
    }

    public StepDefinitionBuilder viewBy(E ... roles) {
        viewRoles = EnumSet.copyOf(Arrays.asList(roles));
        return this;
    }

    public StepDefinitionBuilder executeBy(E ... roles) {
        this.executeRoles = EnumSet.copyOf(Arrays.asList(roles));
        return this;
    }

    public StepDefinitionBuilder executor(Class requestType,
                                          Class<? extends Executor> executorType) {
        this.requestType = requestType;
        this.executorType = executorType;
        return this;
    }

    public StepDefinitionBuilder executor(Class<? extends Executor> executorType) {
        return this.executor(null, executorType);
    }

    public static StepDefinitionBuilder step(String stepName) {
        return new StepDefinitionBuilder(stepName);
    }

    public StepDefinition build() {
        StepDefinition definition = new StepDefinition();
        definition.setStepName(this.stepName);
        definition.setViewRoles(this.viewRoles);
        definition.setExecuteRoles(this.executeRoles);
        definition.setRequestType(this.requestType);
        definition.setExecutorType(this.executorType);
        return definition;
    }
}
