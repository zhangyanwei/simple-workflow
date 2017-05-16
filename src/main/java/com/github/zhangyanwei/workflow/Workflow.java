package com.github.zhangyanwei.workflow;

import com.github.zhangyanwei.exception.IllegalAccessException;
import com.github.zhangyanwei.workflow.definition.WorkflowDefinition;
import com.github.zhangyanwei.workflow.impl.entity.WorkflowInstance;
import com.github.zhangyanwei.workflow.step.Step;

import javax.annotation.Nullable;
import java.util.List;

public interface Workflow<T extends Enum<T>, E extends Enum<E>> {

    Workflow from(WorkflowDefinition<T, E> definition, Long userId);

    Workflow from(WorkflowDefinition<T, E> definition, WorkflowInstance instance, Long userId);

    @Nullable
    Step nextStep();

    @Nullable
    Step nextStep(String stepName);

    @Nullable
    Step currentStep();

    List<String> nextSteps(Step step) throws IllegalAccessException;

}
